package com.fiap.gs2025.IncludIA_Java.dto.response;

import java.time.Instant;
import java.util.UUID;

public record ChatPreviewResponse(
        UUID id,
        UUID recipientId,
        String recipientName,
        String recipientPhoto,
        String lastMessage,
        Instant lastMessageTime,
        boolean isOnline
) {}