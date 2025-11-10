package com.fiap.gs2025.IncludIA_Java.dtos.response;

import com.fiap.gs2025.IncludIA_Java.models.Recruiter;

import java.util.UUID;

public record RecruiterProfileResponse(
        UUID id,
        String nome,
        String email,
        String fotoPerfilUrl,
        EmpresaResponse empresa
) {
    public RecruiterProfileResponse(Recruiter recruiter) {
        this(
                recruiter.getId(),
                recruiter.getNome(),
                recruiter.getEmail(),
                recruiter.getFotoPerfilUrl(),
                new EmpresaResponse(recruiter.getEmpresa())
        );
    }
}