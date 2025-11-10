package com.fiap.gs2025.IncludIA_Java.controller;

import com.fiap.gs2025.IncludIA_Java.dto.response.ProfileViewResponse;
import com.fiap.gs2025.IncludIA_Java.service.ProfileViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/view")
public class ProfileViewController {

    @Autowired
    private ProfileViewService profileViewService;

    @PostMapping("/profile/{candidateId}")
    public ResponseEntity<ProfileViewResponse> logView(@PathVariable UUID candidateId) {
        return ResponseEntity.status(201).body(profileViewService.logProfileView(candidateId));
    }

    @GetMapping("/my-profile-views")
    public ResponseEntity<List<ProfileViewResponse>> getMyViews() {
        return ResponseEntity.ok(profileViewService.getMyProfileViews());
    }
}