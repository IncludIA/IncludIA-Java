package com.fiap.gs2025.IncludIA_Java.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record ExperienceRequest(
        @NotBlank(message = "Título do cargo é obrigatório")
        String tituloCargo,

        @NotBlank(message = "Tipo de emprego é obrigatório")
        String tipoEmprego,

        @NotNull(message = "Data de início é obrigatória")
        LocalDate dataInicio,

        LocalDate dataFim,

        String descricao,
        UUID empresaId
) {}