package com.fiap.gs2025.IncludIA_Java.controller;

import com.fiap.gs2025.IncludIA_Java.dto.request.CandidateIdiomaRequest;
import com.fiap.gs2025.IncludIA_Java.dto.request.EducationRequest;
import com.fiap.gs2025.IncludIA_Java.dto.request.ExperienceRequest;
import com.fiap.gs2025.IncludIA_Java.dto.request.VoluntariadoRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.*;
import com.fiap.gs2025.IncludIA_Java.service.CandidateProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/profile")
public class CandidateProfileController {

    @Autowired
    private CandidateProfileService profileService;

    @GetMapping
    public ResponseEntity<CandidateProfileResponse> getMyProfile() {
        return ResponseEntity.ok(profileService.getFullProfile());
    }

    @PostMapping("/generate-ai-summary")
    public ResponseEntity<CandidateProfileResponse> generateAiSummary() {
        return ResponseEntity.ok(profileService.generateMyAIProfile());
    }

    @PostMapping("/experience")
    public ResponseEntity<ExperienceResponse> addExperience(@Valid @RequestBody ExperienceRequest request) {
        return ResponseEntity.status(201).body(profileService.addExperience(request));
    }

    @PostMapping("/education")
    public ResponseEntity<EducationResponse> addEducation(@Valid @RequestBody EducationRequest request) {
        return ResponseEntity.status(201).body(profileService.addEducation(request));
    }

    @PostMapping("/volunteering")
    public ResponseEntity<VoluntariadoResponse> addVoluntariado(@Valid @RequestBody VoluntariadoRequest request) {
        return ResponseEntity.status(201).body(profileService.addVoluntariado(request));
    }

    @PostMapping("/language")
    public ResponseEntity<CandidateIdiomaResponse> addIdioma(@Valid @RequestBody CandidateIdiomaRequest request) {
        return ResponseEntity.status(201).body(profileService.addIdioma(request));
    }

    @PostMapping("/skill/{skillId}")
    public ResponseEntity<CandidateProfileResponse> addSkill(@PathVariable UUID skillId) {
        return ResponseEntity.ok(profileService.addSkillToProfile(skillId));
    }

    @PutMapping("/summary")
    public ResponseEntity<CandidateProfileResponse> updateSummary(@RequestBody String summary) {
        return ResponseEntity.ok(profileService.updateProfileSummary(summary));
    }
}