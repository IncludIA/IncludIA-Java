package com.fiap.gs2025.IncludIA_Java.dto.response;

import java.time.Instant;
import java.util.UUID;

public record ChatPreviewResponse(
        UUID id,                // ID do Chat
        UUID recipientId,       // ID do outro usuário
        String recipientName,   // Nome do outro usuário
        String recipientPhoto,  // Foto do outro usuário
        String lastMessage,     // Conteúdo da última mensagem
        Instant lastMessageTime,// Horário da última mensagem
        long unreadCount,       // Contagem de mensagens não lidas
        boolean isOnline        // Se o outro usuário está online
) {}