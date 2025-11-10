package com.fiap.gs2025.IncludIA_Java.controller;

import com.fiap.gs2025.IncludIA_Java.dto.request.MatchActionRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.MatchResponse;
import com.fiap.gs2025.IncludIA_Java.service.InteractionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/swipe")
public class InteractionController {

    @Autowired
    private InteractionService interactionService;

    @PostMapping("/candidate")
    public ResponseEntity<MatchResponse> candidateSwipe(@Valid @RequestBody MatchActionRequest request) {
        return ResponseEntity.ok(interactionService.candidateSwipe(request));
    }

    @PostMapping("/recruiter/{vagaId}")
    public ResponseEntity<MatchResponse> recruiterSwipe(@PathVariable UUID vagaId, @Valid @RequestBody MatchActionRequest request) {
        return ResponseEntity.ok(interactionService.recruiterSwipe(vagaId, request));
    }
}