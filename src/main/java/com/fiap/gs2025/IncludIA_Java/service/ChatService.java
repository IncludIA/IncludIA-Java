package com.fiap.gs2025.IncludIA_Java.service;

import com.fiap.gs2025.IncludIA_Java.dto.request.ChatMessageRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.ChatMessageResponse;
import com.fiap.gs2025.IncludIA_Java.dto.response.ChatPreviewResponse;
import com.fiap.gs2025.IncludIA_Java.exceptions.ResourceNotFoundException;
import com.fiap.gs2025.IncludIA_Java.exceptions.UnauthorizedAccessException;
import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.Chat;
import com.fiap.gs2025.IncludIA_Java.models.ChatMessage;
import com.fiap.gs2025.IncludIA_Java.models.Recruiter;
import com.fiap.gs2025.IncludIA_Java.repository.ChatMessageRepository;
import com.fiap.gs2025.IncludIA_Java.repository.ChatRepository;
import com.fiap.gs2025.IncludIA_Java.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private void checkUserChatAccess(Chat chat, UUID userId) {
        boolean isCandidate = chat.getMatch().getCandidate().getId().equals(userId);
        boolean isRecruiter = chat.getMatch().getVaga().getRecruiter().getId().equals(userId);
        if (!isCandidate && !isRecruiter) {
            throw new UnauthorizedAccessException("Usuário não pertence a este chat");
        }
    }

    @Transactional(readOnly = true)
    public List<ChatPreviewResponse> getMyChats() {
        UUID userId = getAuthenticatedUserId();

        List<Chat> chats = chatRepository.findChatsByUserId(userId);

        return chats.stream()
                .map(chat -> {
                    boolean amICandidate = chat.getMatch().getCandidate().getId().equals(userId);

                    UUID otherId;
                    String otherName;
                    String otherPhoto;
                    boolean isOnline;

                    if (amICandidate) {
                        Recruiter recruiter = chat.getMatch().getVaga().getRecruiter();
                        otherId = recruiter.getId();
                        otherName = recruiter.getNome();
                        otherPhoto = recruiter.getFotoPerfilUrl();
                        isOnline = recruiter.isOnline();
                    } else {
                        Candidate candidate = chat.getMatch().getCandidate();
                        otherId = candidate.getId();
                        otherName = candidate.getNome();
                        otherPhoto = candidate.getFotoPerfilUrl();
                        isOnline = candidate.isOnline();
                    }

                    ChatMessage lastMsgObj = chat.getMessages().stream()
                            .max(Comparator.comparing(ChatMessage::getTimestamp))
                            .orElse(null);

                    String lastMessageContent = (lastMsgObj != null) ? lastMsgObj.getConteudo() : "Inicie a conversa...";
                    Instant lastMessageTime = (lastMsgObj != null) ? lastMsgObj.getTimestamp() : chat.getCreatedAt();

                    long unreadCount = chat.getMessages().stream()
                            .filter(msg -> !msg.isRead() && msg.getReceiverId().equals(userId))
                            .count();

                    return new ChatPreviewResponse(
                            chat.getId(),
                            otherId,
                            otherName,
                            otherPhoto,
                            lastMessageContent,
                            lastMessageTime,
                            unreadCount,
                            isOnline
                    );
                })
                .sorted(Comparator.comparing(ChatPreviewResponse::lastMessageTime).reversed())
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteChat(UUID chatId) {
        UUID userId = getAuthenticatedUserId();
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat não encontrado"));

        checkUserChatAccess(chat, userId);

        chat.setAtive(false);
        chatRepository.save(chat);
    }
    private UUID getAuthenticatedUserId() {
        return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    @Transactional(readOnly = true)
    public Page<ChatMessageResponse> getMessagesForChat(UUID chatId, Pageable pageable) {
        UUID userId = getAuthenticatedUserId();
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat não encontrado"));

        checkUserChatAccess(chat, userId);

        return chatMessageRepository.findByChatOrderByTimestampDesc(chat, pageable)
                .map(ChatMessageResponse::new);
    }

    @Transactional
    public ChatMessageResponse sendMessage(UUID chatId, ChatMessageRequest request) {
        UUID senderId = getAuthenticatedUserId();
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat não encontrado"));

        checkUserChatAccess(chat, senderId);

        ChatMessage message = new ChatMessage();
        message.setId(UUID.randomUUID());
        message.setChat(chat);
        message.setConteudo(request.conteudo());
        message.setSenderId(senderId);
        message.setReceiverId(request.receiverId());
        message.setTimestamp(Instant.now());
        message.setRead(false);

        ChatMessage savedMessage = chatMessageRepository.save(message);
        ChatMessageResponse response = new ChatMessageResponse(savedMessage);

        messagingTemplate.convertAndSend("/topic/chat/" + chatId, response);

        return response;
    }
}