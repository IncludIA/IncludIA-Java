package com.fiap.gs2025.IncludIA_Java.dto.auth;

import java.util.UUID;

public record LoginResponse(
        String token,
        UUID userId,
        String nome,
        String email,
        String role
) {}