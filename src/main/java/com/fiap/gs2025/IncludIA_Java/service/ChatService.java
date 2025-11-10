package com.fiap.gs2025.IncludIA_Java.service;

import com.fiap.gs2025.IncludIA_Java.dto.request.ChatMessageRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.ChatMessageResponse;
import com.fiap.gs2025.IncludIA_Java.exceptions.ResourceNotFoundException;
import com.fiap.gs2025.IncludIA_Java.exceptions.UnauthorizedAccessException;
import com.fiap.gs2025.IncludIA_Java.models.Chat;
import com.fiap.gs2025.IncludIA_Java.models.ChatMessage;
import com.fiap.gs2025.IncludIA_Java.repository.ChatMessageRepository;
import com.fiap.gs2025.IncludIA_Java.repository.ChatRepository;
import com.fiap.gs2025.IncludIA_Java.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    private void checkUserChatAccess(Chat chat, UUID userId) {
        boolean isCandidate = chat.getMatch().getCandidate().getId().equals(userId);
        boolean isRecruiter = chat.getMatch().getVaga().getRecruiter().getId().equals(userId);
        if (!isCandidate && !isRecruiter) {
            throw new UnauthorizedAccessException("Usuário não pertence a este chat");
        }
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getMessagesForChat(UUID chatId) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat não encontrado"));

        checkUserChatAccess(chat, userDetails.getId());

        return chatMessageRepository.findByChat(chat).stream()
                .map(ChatMessageResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ChatMessageResponse sendMessage(UUID chatId, ChatMessageRequest request) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat não encontrado"));

        checkUserChatAccess(chat, userDetails.getId());

        ChatMessage message = new ChatMessage();
        message.setId(UUID.randomUUID());
        message.setChat(chat);
        message.setConteudo(request.conteudo());
        message.setSenderId(userDetails.getId());
        message.setReceiverId(request.receiverId());
        message.setTimestamp(Instant.now());
        message.setRead(false);

        ChatMessage savedMessage = chatMessageRepository.save(message);
        return new ChatMessageResponse(savedMessage);
    }
}