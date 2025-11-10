package com.fiap.gs2025.IncludIA_Java.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record SavedJobRequest(
        @NotNull(message = "ID da Vaga é obrigatório")
        UUID vagaId
) {}