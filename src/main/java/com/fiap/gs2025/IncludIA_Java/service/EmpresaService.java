package com.fiap.gs2025.IncludIA_Java.service;

import com.fiap.gs2025.IncludIA_Java.dto.request.EmpresaRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.EmpresaResponse;
import com.fiap.gs2025.IncludIA_Java.exceptions.DuplicateResourceException;
import com.fiap.gs2025.IncludIA_Java.exceptions.ResourceNotFoundException;
import com.fiap.gs2025.IncludIA_Java.models.Empresa;
import com.fiap.gs2025.IncludIA_Java.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Transactional
    public EmpresaResponse createEmpresa(EmpresaRequest request) {
        if (empresaRepository.findByCnpj(request.cnpj()).isPresent()) {
            throw new DuplicateResourceException("CNPJ já cadastrado");
        }

        Empresa empresa = new Empresa();
        empresa.setId(UUID.randomUUID());
        empresa.setNomeOficial(request.nomeOficial());
        empresa.setNomeFantasia(request.nomeFantasia());
        empresa.setCnpj(request.cnpj());
        empresa.setLocalizacao(request.localizacao());
        empresa.setDescricao(request.descricao());
        empresa.setCultura(request.cultura());
        empresa.setFotoCapaUrl(request.fotoCapaUrl());

        Empresa savedEmpresa = empresaRepository.save(empresa);
        return new EmpresaResponse(savedEmpresa);
    }

    public EmpresaResponse getEmpresaById(UUID id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));
        return new EmpresaResponse(empresa);
    }
}