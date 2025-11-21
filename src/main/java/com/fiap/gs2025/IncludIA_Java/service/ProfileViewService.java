package com.fiap.gs2025.IncludIA_Java.service;

import com.fiap.gs2025.IncludIA_Java.dto.response.ProfileViewResponse;
import com.fiap.gs2025.IncludIA_Java.exceptions.ResourceNotFoundException;
import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.ProfileView;
import com.fiap.gs2025.IncludIA_Java.models.Recruiter;
import com.fiap.gs2025.IncludIA_Java.repository.CandidateRepository;
import com.fiap.gs2025.IncludIA_Java.repository.ProfileViewRepository;
import com.fiap.gs2025.IncludIA_Java.repository.RecruiterRepository;
import com.fiap.gs2025.IncludIA_Java.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProfileViewService {

    @Autowired
    private ProfileViewRepository profileViewRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public ProfileViewResponse logProfileView(UUID candidateId) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Recruiter recruiter = recruiterRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Recrutador não encontrado"));

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidato não encontrado"));

        ProfileView profileView = new ProfileView();
        profileView.setId(UUID.randomUUID());
        profileView.setRecruiter(recruiter);
        profileView.setCandidate(candidate);
        profileView.setViewedAt(Instant.now());

        ProfileView savedView = profileViewRepository.save(profileView);

        notificationService.createProfileViewNotification(recruiter, candidate);

        return new ProfileViewResponse(savedView);
    }

    @Transactional(readOnly = true)
    public List<ProfileViewResponse> getMyProfileViews() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Candidate candidate = candidateRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidato não encontrado"));

        return profileViewRepository.findByCandidate(candidate).stream()
                .map(ProfileViewResponse::new)
                .collect(Collectors.toList());
    }

    
}