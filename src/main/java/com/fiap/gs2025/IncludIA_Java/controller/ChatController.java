package com.fiap.gs2025.IncludIA_Java.controller;

import com.fiap.gs2025.IncludIA_Java.dto.request.ChatMessageRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.ChatMessageResponse;
import com.fiap.gs2025.IncludIA_Java.dto.response.ChatPreviewResponse;
import com.fiap.gs2025.IncludIA_Java.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<Page<ChatMessageResponse>> getMessages(
            @PathVariable UUID chatId,
            @PageableDefault(size = 30) Pageable pageable) {
        return ResponseEntity.ok(chatService.getMessagesForChat(chatId, pageable));
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable UUID chatId) {
        chatService.deleteChat(chatId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{chatId}/messages")
    public ResponseEntity<ChatMessageResponse> sendMessage(
            @PathVariable UUID chatId,
            @Valid @RequestBody ChatMessageRequest request) {
        return ResponseEntity.status(201).body(chatService.sendMessage(chatId, request));
    }

    @GetMapping
    public ResponseEntity<List<ChatPreviewResponse>> getMyChats() {
        return ResponseEntity.ok(chatService.getMyChats());
    }
}