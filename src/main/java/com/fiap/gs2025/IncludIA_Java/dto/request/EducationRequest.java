package com.fiap.gs2025.IncludIA_Java.dto.request;

import com.fiap.gs2025.IncludIA_Java.enums.GrauEducacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EducationRequest(
        @NotBlank(message = "{nome.instituicao.notblank}")
        String nomeInstituicao,

        @NotNull(message = "{grau.educacao.notnull}")
        GrauEducacao grau,

        String areaEstudo,

        @NotNull(message = "{data.inicio.notnull}")
        LocalDate dataInicio,

        LocalDate dataFim,
        String descricao
) {}