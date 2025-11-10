package com.fiap.gs2025.IncludIA_Java.dtos.response;

import com.fiap.gs2025.IncludIA_Java.enums.NomeIdioma;
import com.fiap.gs2025.IncludIA_Java.models.Idioma;

import java.util.UUID;

public record IdiomaResponse(
        UUID id,
        NomeIdioma nome
) {
    public IdiomaResponse(Idioma idioma) {
        this(idioma.getId(), idioma.getNome());
    }
}