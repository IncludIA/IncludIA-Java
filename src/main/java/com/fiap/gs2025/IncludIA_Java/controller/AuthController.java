package com.fiap.gs2025.IncludIA_Java.controller;

import com.fiap.gs2025.IncludIA_Java.dto.auth.LoginRequest;
import com.fiap.gs2025.IncludIA_Java.dto.auth.LoginResponse;
import com.fiap.gs2025.IncludIA_Java.dto.auth.SocialLoginRequest;
import com.fiap.gs2025.IncludIA_Java.dto.request.CandidateRegistrationRequest;
import com.fiap.gs2025.IncludIA_Java.dto.request.RecruiterRegistrationRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.CandidateProfileResponse;
import com.fiap.gs2025.IncludIA_Java.dto.response.RecruiterProfileResponse;
import com.fiap.gs2025.IncludIA_Java.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register-candidate")
    public ResponseEntity<CandidateProfileResponse> registerCandidate(@Valid @RequestBody CandidateRegistrationRequest request) {
        return ResponseEntity.status(201).body(authService.registerCandidate(request));
    }

    @PostMapping("/register-recruiter")
    public ResponseEntity<RecruiterProfileResponse> registerRecruiter(@Valid @RequestBody RecruiterRegistrationRequest request) {
        return ResponseEntity.status(201).body(authService.registerRecruiter(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/social-login")
    public ResponseEntity<LoginResponse> socialLogin(@RequestBody SocialLoginRequest request) {
        return ResponseEntity.ok(authService.socialLogin(request));
    }
}