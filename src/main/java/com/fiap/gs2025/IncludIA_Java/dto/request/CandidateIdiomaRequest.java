package com.fiap.gs2025.IncludIA_Java.dto.request;

import com.fiap.gs2025.IncludIA_Java.enums.NomeIdioma;
import com.fiap.gs2025.IncludIA_Java.enums.ProficiencyLevel;
import jakarta.validation.constraints.NotNull;

public record CandidateIdiomaRequest(
        @NotNull(message = "{idioma.notnull}")
        NomeIdioma nomeIdioma,

        @NotNull(message = "{proficiencia.notnull}")
        ProficiencyLevel nivelProficiencia
) {}