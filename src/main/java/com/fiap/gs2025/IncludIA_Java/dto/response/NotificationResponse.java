package com.fiap.gs2025.IncludIA_Java.dto.response;

import com.fiap.gs2025.IncludIA_Java.enums.NotificationType;
import com.fiap.gs2025.IncludIA_Java.models.Notification;

import java.time.Instant;
import java.util.UUID;

public record NotificationResponse(
        UUID id,
        NotificationType tipo,
        String mensagem,
        boolean isRead,
        Instant createdAt
) {
    public NotificationResponse(Notification notification) {
        this(
                notification.getId(),
                notification.getTipo(),
                notification.getMensagem(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }
}