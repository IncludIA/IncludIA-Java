package com.fiap.gs2025.IncludIA_Java.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ChatMessageRequest(
        @NotBlank
        @Size(max = 2000)
        String conteudo,

        @NotNull
        UUID receiverId
) {}