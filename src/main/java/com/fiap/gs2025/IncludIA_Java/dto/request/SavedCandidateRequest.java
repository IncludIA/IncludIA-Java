package com.fiap.gs2025.IncludIA_Java.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record SavedCandidateRequest(
        @NotNull(message = "{candidate.id.notnull}")
        UUID candidateId
) {}