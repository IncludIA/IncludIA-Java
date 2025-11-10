package com.fiap.gs2025.IncludIA_Java.dtos.response;

import com.fiap.gs2025.IncludIA_Java.enums.GrauEducacao;
import com.fiap.gs2025.IncludIA_Java.models.Education;

import java.time.LocalDate;
import java.util.UUID;

public record EducationResponse(
        UUID id,
        String nomeInstituicao,
        GrauEducacao grau,
        String areaEstudo,
        LocalDate dataInicio,
        LocalDate dataFim,
        String descricao
) {
    public EducationResponse(Education edu) {
        this(
                edu.getId(),
                edu.getNomeInstituicao(),
                edu.getGrau(),
                edu.getAreaEstudo(),
                edu.getDataInicio(),
                edu.getDataFim(),
                edu.getDescricao()
        );
    }
}