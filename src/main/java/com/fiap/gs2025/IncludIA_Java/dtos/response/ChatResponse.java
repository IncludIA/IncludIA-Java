package com.fiap.gs2025.IncludIA_Java.dtos.response;

import com.fiap.gs2025.IncludIA_Java.models.Chat;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record ChatResponse(
        UUID id,
        UUID matchId,
        Instant createdAt,
        Set<ChatMessageResponse> messages
) {
    public ChatResponse(Chat chat) {
        this(
                chat.getId(),
                chat.getMatch().getId(),
                chat.getCreatedAt(),
                chat.getMessages().stream()
                        .map(ChatMessageResponse::new)
                        .collect(Collectors.toSet())
        );
    }
}