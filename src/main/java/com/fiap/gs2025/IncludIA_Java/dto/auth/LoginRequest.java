package com.fiap.gs2025.IncludIA_Java.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "{email.notblank}")
        @Email(message = "{field.email}")
        String email,

        @NotBlank(message = "{senha.notblank}")
        String senha
) {}