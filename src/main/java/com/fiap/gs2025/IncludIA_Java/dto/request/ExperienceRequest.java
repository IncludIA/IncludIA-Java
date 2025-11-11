package com.fiap.gs2025.IncludIA_Java.dto.request;

import com.fiap.gs2025.IncludIA_Java.enums.TipoContrato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record ExperienceRequest(
        @NotBlank(message = "{titulo.cargo.notblank}")
        String tituloCargo,

        @NotBlank(message = "{tipo.emprego.notblank}")
        TipoContrato tipoEmprego,

        @NotNull(message = "{data.inicio.notnull}")
        LocalDate dataInicio,

        LocalDate dataFim,

        String descricao,
        UUID empresaId
) {}