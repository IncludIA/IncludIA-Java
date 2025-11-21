package com.fiap.gs2025.IncludIA_Java.service;

import com.fiap.gs2025.IncludIA_Java.dto.request.RecruiterProfileRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.RecruiterProfileResponse;
import com.fiap.gs2025.IncludIA_Java.exceptions.ResourceNotFoundException;
import com.fiap.gs2025.IncludIA_Java.models.Recruiter;
import com.fiap.gs2025.IncludIA_Java.repository.RecruiterRepository;
import com.fiap.gs2025.IncludIA_Java.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecruiterProfileService {

    @Autowired
    private RecruiterRepository recruiterRepository;

    private Recruiter getCurrentAuthenticatedRecruiter() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return recruiterRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Recrutador não encontrado"));
    }

    @Transactional(readOnly = true)
    public RecruiterProfileResponse getMyProfile() {
        Recruiter recruiter = getCurrentAuthenticatedRecruiter();
        return new RecruiterProfileResponse(recruiter);
    }

    @Transactional
    public void deleteMyAccount() {
        Recruiter recruiter = getCurrentAuthenticatedRecruiter();
        recruiter.setAtive(false); // Soft Delete
        recruiterRepository.save(recruiter);
    }

    @Transactional
    public RecruiterProfileResponse updateMyProfile(RecruiterProfileRequest request) {
        Recruiter recruiter = getCurrentAuthenticatedRecruiter();

        recruiter.setNome(request.nome());
        recruiter.setFotoPerfilUrl(request.fotoPerfilUrl());

        Recruiter savedRecruiter = recruiterRepository.save(recruiter);
        return new RecruiterProfileResponse(savedRecruiter);
    }
}