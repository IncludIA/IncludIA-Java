package com.fiap.gs2025.IncludIA_Java.dto.request;

import com.fiap.gs2025.IncludIA_Java.enums.ModeloTrabalho;
import com.fiap.gs2025.IncludIA_Java.enums.TipoContrato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record JobVagaRequest(
        @NotBlank(message = "{titulo.notblank}")
        String titulo,

        @NotBlank(message = "{descricao.notblank}")
        @Size(max = 5000)
        String descricaoOriginal,

        String localizacao,

        @NotNull(message = "{tipo.vaga.notnull}")
        TipoContrato tipoVaga,

        @NotNull(message = "{modelo.trabalho.notnull}")
        ModeloTrabalho modeloTrabalho,

        BigDecimal salarioMin,
        BigDecimal salarioMax,
        String beneficios,
        String experienciaRequerida,

        Set<UUID> skillIds
) {}