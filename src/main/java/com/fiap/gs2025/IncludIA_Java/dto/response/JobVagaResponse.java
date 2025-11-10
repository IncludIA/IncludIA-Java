package com.fiap.gs2025.IncludIA_Java.dto.response;

import com.fiap.gs2025.IncludIA_Java.enums.ModeloTrabalho;
import com.fiap.gs2025.IncludIA_Java.enums.TipoContrato;
import com.fiap.gs2025.IncludIA_Java.models.JobVaga;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record JobVagaResponse(
        UUID id,
        String titulo,
        String descricaoOriginal,
        String descricaoInclusiva,
        String localizacao,
        TipoContrato tipoVaga,
        ModeloTrabalho modeloTrabalho,
        BigDecimal salarioMin,
        BigDecimal salarioMax,
        String beneficios,
        String experienciaRequerida,
        boolean isAtiva,
        Instant createdAt,
        EmpresaResponse empresa,
        Set<SkillResponse> skillsDesejadas
) {
    public JobVagaResponse(JobVaga vaga) {
        this(
                vaga.getId(),
                vaga.getTitulo(),
                vaga.getDescricaoOriginal(),
                vaga.getDescricaoInclusiva(),
                vaga.getLocalizacao(),
                vaga.getTipoVaga(),
                vaga.getModeloTrabalho(),
                vaga.getSalarioMin(),
                vaga.getSalarioMax(),
                vaga.getBeneficios(),
                vaga.getExperienciaRequerida(),
                vaga.isAtiva(),
                vaga.getCreatedAt(),
                new EmpresaResponse(vaga.getEmpresa()),
                vaga.getSkillsDesejadas().stream().map(SkillResponse::new).collect(Collectors.toSet())
        );
    }
}