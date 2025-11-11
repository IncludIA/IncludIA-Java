package com.fiap.gs2025.IncludIA_Java.dto.request;

import com.fiap.gs2025.IncludIA_Java.enums.NomeIdioma;
import jakarta.validation.constraints.NotNull;

public record IdiomaRequest(
        @NotNull(message = "{idioma.notnull}")
        NomeIdioma nome
) {}