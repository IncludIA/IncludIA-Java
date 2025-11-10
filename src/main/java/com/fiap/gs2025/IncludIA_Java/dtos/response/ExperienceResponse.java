package com.fiap.gs2025.IncludIA_Java.dtos.response;

import com.fiap.gs2025.IncludIA_Java.models.Experience;

import java.time.LocalDate;
import java.util.UUID;

public record ExperienceResponse(
        UUID id,
        String tituloCargo,
        String tipoEmprego,
        LocalDate dataInicio,
        LocalDate dataFim,
        String descricao,
        EmpresaResponse empresa
) {
    public ExperienceResponse(Experience exp) {
        this(
                exp.getId(),
                exp.getTituloCargo(),
                exp.getTipoEmprego(),
                exp.getDataInicio(),
                exp.getDataFim(),
                exp.getDescricao(),
                exp.getEmpresa() != null ? new EmpresaResponse(exp.getEmpresa()) : null
        );
    }
}