package com.fiap.gs2025.IncludIA_Java.controller;

import com.fiap.gs2025.IncludIA_Java.dto.request.SavedCandidateRequest;
import com.fiap.gs2025.IncludIA_Java.dto.request.SavedJobRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.SavedCandidateResponse;
import com.fiap.gs2025.IncludIA_Java.dto.response.SavedJobResponse;
import com.fiap.gs2025.IncludIA_Java.service.SavedContentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/save")
public class SavedContentController {

    @Autowired
    private SavedContentService savedContentService;

    @PostMapping("/job")
    public ResponseEntity<SavedJobResponse> saveJob(@Valid @RequestBody SavedJobRequest request) {
        return ResponseEntity.status(201).body(savedContentService.saveJob(request));
    }

    @PostMapping("/candidate")
    public ResponseEntity<SavedCandidateResponse> saveCandidate(@Valid @RequestBody SavedCandidateRequest request) {
        return ResponseEntity.status(201).body(savedContentService.saveCandidate(request));
    }
}