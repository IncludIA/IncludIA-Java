package com.fiap.gs2025.IncludIA_Java.controller;

import com.fiap.gs2025.IncludIA_Java.dto.request.EmpresaRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.EmpresaResponse;
import com.fiap.gs2025.IncludIA_Java.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<EmpresaResponse> createEmpresa(@Valid @RequestBody EmpresaRequest request) {
        EmpresaResponse response = empresaService.createEmpresa(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponse> getEmpresaById(@PathVariable UUID id) {
        return ResponseEntity.ok(empresaService.getEmpresaById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponse> update(@PathVariable UUID id, @Valid @RequestBody EmpresaRequest request) {
        return ResponseEntity.ok(empresaService.updateEmpresa(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        empresaService.deleteEmpresa(id);
        return ResponseEntity.noContent().build();
    }
}