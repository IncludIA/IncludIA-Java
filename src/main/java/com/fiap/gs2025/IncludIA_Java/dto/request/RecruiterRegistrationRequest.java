package com.fiap.gs2025.IncludIA_Java.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RecruiterRegistrationRequest(
        @NotBlank(message = "{nome.notblank}")
        String nome,

        @NotBlank(message = "{email.notblank}")
        @Email(message = "{field.email}")
        String email,

        @NotBlank(message = "{senha.notblank}")
        @Size(min = 8, message = "{field.size.password}")
        String senha,

        @NotNull(message = "{empresa.notnull}")
        UUID empresaId
) {}