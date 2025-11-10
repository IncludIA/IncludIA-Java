package com.fiap.gs2025.IncludIA_Java.dto.response;

import com.fiap.gs2025.IncludIA_Java.enums.SkillType;
import com.fiap.gs2025.IncludIA_Java.models.Skill;

import java.util.UUID;

public record SkillResponse(
        UUID id,
        String nome,
        SkillType tipoSkill
) {
    public SkillResponse(Skill skill) {
        this(skill.getId(), skill.getNome(), skill.getTipoSkill());
    }
}