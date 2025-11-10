package com.fiap.gs2025.IncludIA_Java.dtos.response;

import com.fiap.gs2025.IncludIA_Java.models.Empresa;

import java.util.UUID;

public record EmpresaResponse(
        UUID id,
        String nomeOficial,
        String nomeFantasia,
        String cnpj,
        String localizacao,
        String descricao,
        String cultura,
        String fotoCapaUrl
) {
    public EmpresaResponse(Empresa empresa) {
        this(
                empresa.getId(),
                empresa.getNomeOficial(),
                empresa.getNomeFantasia(),
                empresa.getCnpj(),
                empresa.getLocalizacao(),
                empresa.getDescricao(),
                empresa.getCultura(),
                empresa.getFotoCapaUrl()
        );
    }
}