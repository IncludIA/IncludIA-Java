package com.fiap.gs2025.IncludIA_Java.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmpresaRequest(
        @NotBlank(message = "{nome.notblank}")
        String nomeOficial,

        String nomeFantasia,

        @NotBlank(message = "{cnpj.notblank}")
        @Pattern(regexp = "\\d{14}", message = "{cnpj.pattern}")
        String cnpj,

        String localizacao,

        @Size(max = 4000)
        String descricao,

        String fotoLogo, // NOVO
        String fotoCapaUrl
) {}