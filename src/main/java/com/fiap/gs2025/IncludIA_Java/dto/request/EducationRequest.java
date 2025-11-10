package com.fiap.gs2025.IncludIA_Java.dto.request;

import com.fiap.gs2025.IncludIA_Java.enums.GrauEducacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EducationRequest(
        @NotBlank(message = "Nome da instituição é obrigatório")
        String nomeInstituicao,

        @NotNull(message = "Grau de educação é obrigatório")
        GrauEducacao grau,

        String areaEstudo,

        @NotNull(message = "Data de início é obrigatória")
        LocalDate dataInicio,

        LocalDate dataFim,
        String descricao
) {}