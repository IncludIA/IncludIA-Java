package com.fiap.gs2025.IncludIA_Java.dto.response;

import com.fiap.gs2025.IncludIA_Java.enums.NomeIdioma;
import com.fiap.gs2025.IncludIA_Java.enums.ProficiencyLevel;
import com.fiap.gs2025.IncludIA_Java.models.CandidateIdioma;

import java.util.UUID;

public record CandidateIdiomaResponse(
        UUID id,
        NomeIdioma nomeIdioma,
        ProficiencyLevel nivelProficiencia
) {
    public CandidateIdiomaResponse(CandidateIdioma ci) {
        this(
                ci.getId(),
                ci.getIdioma().getNome(),
                ci.getNivelProficiencia()
        );
    }
}