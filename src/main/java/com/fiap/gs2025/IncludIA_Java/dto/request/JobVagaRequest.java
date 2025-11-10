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
        @NotBlank(message = "Título é obrigatório")
        String titulo,

        @NotBlank(message = "Descrição é obrigatória")
        @Size(max = 5000)
        String descricaoOriginal,

        String localizacao,

        @NotNull(message = "Tipo de vaga é obrigatório")
        TipoContrato tipoVaga,

        @NotNull(message = "Modelo de trabalho é obrigatório")
        ModeloTrabalho modeloTrabalho,

        BigDecimal salarioMin,
        BigDecimal salarioMax,
        String beneficios,
        String experienciaRequerida,

        Set<UUID> skillIds
) {}