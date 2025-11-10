package com.fiap.gs2025.IncludIA_Java.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record VoluntariadoRequest(
        @NotBlank(message = "Organização é obrigatória")
        String organizacao,

        @NotBlank(message = "Função é obrigatória")
        String funcao,

        String descricao,

        @NotNull(message = "Data de início é obrigatória")
        LocalDate dataInicio,

        LocalDate dataFim
) {}