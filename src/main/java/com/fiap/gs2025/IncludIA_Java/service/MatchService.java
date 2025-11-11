package com.fiap.gs2025.IncludIA_Java.service;

import com.fiap.gs2025.IncludIA_Java.dto.response.MatchResponse;
import com.fiap.gs2025.IncludIA_Java.enums.MatchStatus;
import com.fiap.gs2025.IncludIA_Java.exceptions.ResourceNotFoundException;
import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.Recruiter;
import com.fiap.gs2025.IncludIA_Java.repository.CandidateRepository;
import com.fiap.gs2025.IncludIA_Java.repository.MatchRepository;
import com.fiap.gs2025.IncludIA_Java.repository.RecruiterRepository;
import com.fiap.gs2025.IncludIA_Java.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Transactional(readOnly = true)
    public Page<MatchResponse> getMyMatches(Pageable pageable) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        if ("ROLE_CANDIDATE".equals(role)) {
            Candidate candidate = candidateRepository.findById(userDetails.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Candidato não encontrado"));
            return matchRepository.findByCandidateAndStatus(candidate, MatchStatus.MATCHED, pageable)
                    .map(MatchResponse::new);
        } else {
            Recruiter recruiter = recruiterRepository.findById(userDetails.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Recrutador não encontrado"));
            return matchRepository.findByVaga_RecruiterAndStatus(recruiter, MatchStatus.MATCHED, pageable)
                    .map(MatchResponse::new);
        }
    }
}