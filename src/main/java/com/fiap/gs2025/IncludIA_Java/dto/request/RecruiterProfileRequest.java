package com.fiap.gs2025.IncludIA_Java.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record RecruiterProfileRequest(
        @NotBlank(message = "{nome.notblank}")
        String nome,

        @URL(message = "{field.url}")
        String fotoPerfilUrl
) {}