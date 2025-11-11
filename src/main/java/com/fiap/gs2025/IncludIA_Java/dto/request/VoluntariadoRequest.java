package com.fiap.gs2025.IncludIA_Java.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record VoluntariadoRequest(
        @NotBlank(message = "{organizacao.notblank}")
        String organizacao,

        @NotBlank(message = "{funcao.notblank}")
        String funcao,

        String descricao,

        @NotNull(message = "{data.inicio.notnull}")
        LocalDate dataInicio,

        LocalDate dataFim
) {}