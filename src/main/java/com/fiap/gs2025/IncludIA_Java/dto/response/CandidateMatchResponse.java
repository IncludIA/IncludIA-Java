package com.fiap.gs2025.IncludIA_Java.dto.response;

public record CandidateMatchResponse(
        CandidateProfileResponse perfil,
        int porcentagemCompatibilidade,
        String motivoCompatibilidade
) {}