package com.fiap.gs2025.IncludIA_Java.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record RecruiterProfileRequest(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @URL(message = "URL da foto de perfil inválida")
        String fotoPerfilUrl
) {}