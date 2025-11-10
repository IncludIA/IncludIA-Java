package com.fiap.gs2025.IncludIA_Java.dtos.response;

import com.fiap.gs2025.IncludIA_Java.models.ChatMessage;

import java.time.Instant;
import java.util.UUID;

public record ChatMessageResponse(
        UUID id,
        UUID chatId,
        String conteudo,
        Instant timestamp,
        UUID senderId,
        UUID receiverId,
        boolean isRead
) {
    public ChatMessageResponse(ChatMessage message) {
        this(
                message.getId(),
                message.getChat().getId(),
                message.getConteudo(),
                message.getTimestamp(),
                message.getSenderId(),
                message.getReceiverId(),
                message.isRead()
        );
    }
}