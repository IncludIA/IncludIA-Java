package com.fiap.gs2025.IncludIA_Java.controller;

import com.fiap.gs2025.IncludIA_Java.dto.response.MatchResponse;
import com.fiap.gs2025.IncludIA_Java.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping("/my-matches")
    public ResponseEntity<Page<MatchResponse>> getMyMatches(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(matchService.getMyMatches(pageable));
    }
}