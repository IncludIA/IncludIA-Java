package com.fiap.gs2025.IncludIA_Java.dto.response;

import com.fiap.gs2025.IncludIA_Java.models.Candidate;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record CandidateProfileResponse(
        UUID id,
        String nome,
        String email,
        String resumoPerfil,
        String resumoInclusivoIA,
        String fotoPerfilUrl,

        boolean isOnline,

        String cidade,
        String estado,
        Integer raioBuscaKm,

        Set<SkillResponse> skills,
        Set<ExperienceResponse> experiencias,
        Set<EducationResponse> formacoes,
        Set<VoluntariadoResponse> voluntariados,
        Set<CandidateIdiomaResponse> idiomas
) {
    public CandidateProfileResponse(Candidate candidate) {
        this(
                candidate.getId(),
                candidate.getNome(),
                candidate.getEmail(),
                candidate.getResumoPerfil(),
                candidate.getResumoInclusivoIA(),
                candidate.getFotoPerfilUrl(),

                candidate.isOnline(),

                (candidate.getEndereco() != null) ? candidate.getEndereco().getCidade() : null,
                (candidate.getEndereco() != null) ? candidate.getEndereco().getEstado() : null,
                candidate.getRaioBuscaKm(),

                candidate.getSkills().stream().map(SkillResponse::new).collect(Collectors.toSet()),
                candidate.getExperiencias().stream().map(ExperienceResponse::new).collect(Collectors.toSet()),
                candidate.getFormacoes().stream().map(EducationResponse::new).collect(Collectors.toSet()),
                candidate.getVoluntariados().stream().map(VoluntariadoResponse::new).collect(Collectors.toSet()),
                candidate.getIdiomas().stream().map(CandidateIdiomaResponse::new).collect(Collectors.toSet())
        );
    }
}