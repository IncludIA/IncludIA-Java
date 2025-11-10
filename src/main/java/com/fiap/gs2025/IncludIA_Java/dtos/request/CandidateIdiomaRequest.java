package com.fiap.gs2025.IncludIA_Java.dtos.request;

import com.fiap.gs2025.IncludIA_Java.enums.NomeIdioma;
import com.fiap.gs2025.IncludIA_Java.enums.ProficiencyLevel;
import jakarta.validation.constraints.NotNull;

public record CandidateIdiomaRequest(
        @NotNull(message = "Nome do idioma é obrigatório")
        NomeIdioma nomeIdioma,

        @NotNull(message = "Nível de proficiência é obrigatório")
        ProficiencyLevel nivelProficiencia
) {}