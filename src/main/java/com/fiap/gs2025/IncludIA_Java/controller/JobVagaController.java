package com.fiap.gs2025.IncludIA_Java.controller;

import com.fiap.gs2025.IncludIA_Java.dto.request.JobVagaRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.JobVagaResponse;
import com.fiap.gs2025.IncludIA_Java.service.JobVagaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/vagas")
public class JobVagaController {

    @Autowired
    private JobVagaService vagaService;

    @PostMapping
    public ResponseEntity<JobVagaResponse> createVaga(@Valid @RequestBody JobVagaRequest request) {
        JobVagaResponse response = vagaService.createVaga(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<JobVagaResponse>> getAllVagas(@PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(vagaService.getAllActiveVagas(pageable));
    }

    @GetMapping("/my-vagas")
    public ResponseEntity<Page<JobVagaResponse>> getMyVagas(@PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(vagaService.getVagasByAuthenticatedRecruiter(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobVagaResponse> getVagaById(@PathVariable UUID id) {
        return ResponseEntity.ok(vagaService.getVagaById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobVagaResponse> updateVaga(@PathVariable UUID id, @Valid @RequestBody JobVagaRequest request) {
        return ResponseEntity.ok(vagaService.updateVaga(id, request));
    }
}