package com.fiap.gs2025.IncludIA_Java.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmpresaRequest(
        @NotBlank(message = "Nome Oficial é obrigatório")
        String nomeOficial,

        String nomeFantasia,

        @NotBlank(message = "CNPJ é obrigatório")
        @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos numéricos")
        String cnpj,

        String localizacao,

        @Size(max = 4000)
        String descricao,

        @Size(max = 4000)
        String cultura,

        String fotoCapaUrl
) {}