package com.fiap.gs2025.IncludIA_Java.dtos.response;

import com.fiap.gs2025.IncludIA_Java.models.Voluntariado;

import java.time.LocalDate;
import java.util.UUID;

public record VoluntariadoResponse(
        UUID id,
        String organizacao,
        String funcao,
        String descricao,
        LocalDate dataInicio,
        LocalDate dataFim
) {
    public VoluntariadoResponse(Voluntariado vol) {
        this(
                vol.getId(),
                vol.getOrganizacao(),
                vol.getFuncao(),
                vol.getDescricao(),
                vol.getDataInicio(),
                vol.getDataFim()
        );
    }
}