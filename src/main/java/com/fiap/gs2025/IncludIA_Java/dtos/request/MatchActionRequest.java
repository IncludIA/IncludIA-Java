package com.fiap.gs2025.IncludIA_Java.dtos.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MatchActionRequest(
        @NotNull(message = "ID do alvo (vaga ou candidato) é obrigatório")
        UUID targetId,

        @NotNull(message = "Ação 'isLiked' é obrigatória")
        Boolean isLiked
) {}