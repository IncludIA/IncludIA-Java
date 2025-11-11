package com.fiap.gs2025.IncludIA_Java.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ChatMessageRequest(
        @NotBlank(message = "{chat.content.notblank}")
        @Size(max = 2000)
        String conteudo,

        @NotNull(message = "{chat.receiver.notnull}")
        UUID receiverId
) {}