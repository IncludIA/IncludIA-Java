package com.fiap.gs2025.IncludIA_Java.controller;

import com.fiap.gs2025.IncludIA_Java.dto.request.JobVagaRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.CandidateMatchResponse;
import com.fiap.gs2025.IncludIA_Java.dto.response.CandidateProfileResponse;
import com.fiap.gs2025.IncludIA_Java.dto.response.JobVagaResponse;
import com.fiap.gs2025.IncludIA_Java.enums.ModeloTrabalho;
import com.fiap.gs2025.IncludIA_Java.enums.TipoContrato;
import com.fiap.gs2025.IncludIA_Java.service.JobVagaService;
import com.fiap.gs2025.IncludIA_Java.service.MatchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vagas")
public class JobVagaController {

    @Autowired
    private JobVagaService vagaService;

    @Autowired
    private MatchService matchService;

    @GetMapping("/{vagaId}/candidates-feed")
    public ResponseEntity<List<CandidateMatchResponse>> getCandidatesFeed(@PathVariable UUID vagaId) {
        return ResponseEntity.ok(matchService.getCandidatesFeedForJob(vagaId));
    }

    @GetMapping
    public ResponseEntity<Page<JobVagaResponse>> getAllVagas(
            @RequestParam(required = false) ModeloTrabalho modelo,
            @RequestParam(required = false) TipoContrato tipo,
            @RequestParam(required = false) BigDecimal minSalario,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(vagaService.getAllActiveVagas(pageable));
    }

    @GetMapping("/candidate-detail/{candidateId}")
    public ResponseEntity<CandidateProfileResponse> getCandidateDetail(@PathVariable UUID candidateId) {
        return ResponseEntity.ok(matchService.getCandidateById(candidateId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVaga(@PathVariable UUID id) {
        vagaService.deleteVaga(id);
        return ResponseEntity.noContent().build();
    }

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