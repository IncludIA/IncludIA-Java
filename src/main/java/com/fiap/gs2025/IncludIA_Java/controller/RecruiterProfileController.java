package com.fiap.gs2025.IncludIA_Java.controller;

import com.fiap.gs2025.IncludIA_Java.dto.request.RecruiterProfileRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.RecruiterProfileResponse;
import com.fiap.gs2025.IncludIA_Java.service.RecruiterProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    @Autowired
    private RecruiterProfileService recruiterProfileService;

    @GetMapping("/me")
    public ResponseEntity<RecruiterProfileResponse> getMyProfile() {
        return ResponseEntity.ok(recruiterProfileService.getMyProfile());
    }

    @PutMapping("/me")
    public ResponseEntity<RecruiterProfileResponse> updateMyProfile(@Valid @RequestBody RecruiterProfileRequest request) {
        return ResponseEntity.ok(recruiterProfileService.updateMyProfile(request));
    }
}