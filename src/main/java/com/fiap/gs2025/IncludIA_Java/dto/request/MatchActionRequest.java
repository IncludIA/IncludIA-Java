package com.fiap.gs2025.IncludIA_Java.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MatchActionRequest(
        @NotNull(message = "{target.id.notnull}")
        UUID targetId,

        @NotNull(message = "{action.liked.notnull}")
        Boolean isLiked
) {}