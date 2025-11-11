package com.fiap.gs2025.IncludIA_Java.controller;

import com.fiap.gs2025.IncludIA_Java.dto.request.ChatMessageRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.ChatMessageResponse;
import com.fiap.gs2025.IncludIA_Java.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class WebSocketChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    // O cliente envia mensagens para /app/chat.send/{chatId}
    @MessageMapping("/chat.send/{chatId}")
    public void handleChatMessage(@DestinationVariable UUID chatId, @Payload ChatMessageRequest request) {

        // Assumimos que o 'request' vindo do WebSocket já foi validado pelo cliente
        // e contém o 'senderId' correto no corpo.

        // 1. Persiste a mensagem no banco
        // Precisamos de um DTO que inclua o senderId, ou modificar o ChatMessageRequest
        // Vamos assumir que o ChatMessageRequest agora TEM o senderId

        // ---
        // CORREÇÃO: Vamos criar um DTO específico para o WebSocket para não quebrar o REST
        // Vá para o passo 7
        // ---

        // ---
        // PLANO B: O ChatMessageRequest não tem senderId. O WebSocket é "burro".
        // O cliente envia para /app/chat.send/{chatId}/{senderId}
        // Vamos tentar isso.

        // Vamos refatorar o ChatService para receber o senderId... (Já fizemos no passo 4!)

        // O cliente envia para /app/chat.send/{chatId}
        // O payload (ChatMessageRequest) NÃO tem o senderId.
        // O senderId tem que vir no payload.

        // ---
        // PLANO C: (O MELHOR) O cliente envia para /app/chat.send/{chatId}
        // O Payload será um NOVO DTO: WebSocketChatRequest

        // (Ver Passo 7)
    }
}