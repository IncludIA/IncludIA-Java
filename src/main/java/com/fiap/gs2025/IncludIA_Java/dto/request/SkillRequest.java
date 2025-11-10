package com.fiap.gs2025.IncludIA_Java.dto.request;

import com.fiap.gs2025.IncludIA_Java.enums.SkillType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SkillRequest(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "Tipo de skill é obrigatório")
        SkillType tipoSkill
) {}