package com.fiap.gs2025.IncludIA_Java.dto.request;

import com.fiap.gs2025.IncludIA_Java.enums.GrauEducacao;
import com.fiap.gs2025.IncludIA_Java.enums.NomeIdioma;
import com.fiap.gs2025.IncludIA_Java.enums.ProficiencyLevel;
import com.fiap.gs2025.IncludIA_Java.enums.SkillType;
import com.fiap.gs2025.IncludIA_Java.enums.TipoContrato;

import java.time.LocalDate;
import java.util.List;

public record CandidateFullUpdateRequest(
        String resumoPerfil,
        String resumoInclusivoIA,
        String cidade,
        String estado,
        List<SkillFullRequest> skills,
        List<ExperienceFullRequest> experiencias,
        List<EducationFullRequest> formacoes,
        List<VoluntariadoFullRequest> voluntariados,
        List<IdiomaFullRequest> idiomas
) {
    // --- Sub-DTOs internos para casar com o JSON do Mobile ---

    public record SkillFullRequest(String nome, SkillType tipoSkill) {}

    public record ExperienceFullRequest(
            String tituloCargo,
            TipoContrato tipoEmprego,
            LocalDate dataInicio,
            LocalDate dataFim,
            String descricao,
            EmpresaObjRequest empresa // Mobile manda objeto, não ID
    ) {}

    public record EmpresaObjRequest(String nomeFantasia) {}

    public record EducationFullRequest(
            String nomeInstituicao,
            GrauEducacao grau,
            String areaEstudo,
            LocalDate dataInicio,
            LocalDate dataFim,
            String descricao
    ) {}

    public record VoluntariadoFullRequest(
            String organizacao,
            String funcao,
            String descricao,
            LocalDate dataInicio,
            LocalDate dataFim
    ) {}

    public record IdiomaFullRequest(
            NomeIdioma nomeIdioma, // O mobile manda como string que bate com o enum
            ProficiencyLevel nivelProficiencia
    ) {}
}