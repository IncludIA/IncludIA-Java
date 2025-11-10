package com.fiap.gs2025.IncludIA_Java.service;

import com.fiap.gs2025.IncludIA_Java.dto.request.MatchActionRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.MatchResponse;
import com.fiap.gs2025.IncludIA_Java.enums.MatchStatus;
import com.fiap.gs2025.IncludIA_Java.exceptions.ResourceNotFoundException;
import com.fiap.gs2025.IncludIA_Java.exceptions.UnauthorizedAccessException;
import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.JobVaga;
import com.fiap.gs2025.IncludIA_Java.models.Match;
import com.fiap.gs2025.IncludIA_Java.models.Recruiter;
import com.fiap.gs2025.IncludIA_Java.repository.CandidateRepository;
import com.fiap.gs2025.IncludIA_Java.repository.JobVagaRepository;
import com.fiap.gs2025.IncludIA_Java.repository.MatchRepository;
import com.fiap.gs2025.IncludIA_Java.repository.RecruiterRepository;
import com.fiap.gs2025.IncludIA_Java.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class InteractionService {

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private JobVagaRepository jobVagaRepository;
    @Autowired
    private RecruiterRepository recruiterRepository;
    @Autowired
    private NotificationService notificationService;

    @Transactional
    public MatchResponse candidateSwipe(MatchActionRequest request) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Candidate candidate = candidateRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidato não encontrado"));

        JobVaga vaga = jobVagaRepository.findById(request.targetId())
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada"));

        Match match = matchRepository.findByCandidateAndVaga(candidate, vaga)
                .orElse(new Match(UUID.randomUUID(), candidate, vaga));

        match.setLikedByCandidate(request.isLiked());
        match.updateStatus();

        Match savedMatch = matchRepository.save(match);
        if (savedMatch.getStatus() == MatchStatus.MATCHED) {
            notificationService.sendMatchNotification(savedMatch);
        }

        return new MatchResponse(savedMatch);
    }

    @Transactional
    public MatchResponse recruiterSwipe(UUID vagaId, MatchActionRequest request) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Recruiter recruiter = recruiterRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Recrutador não encontrado"));

        Candidate candidate = candidateRepository.findById(request.targetId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidato não encontrado"));

        JobVaga vaga = jobVagaRepository.findById(vagaId)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada"));

        if (!vaga.getRecruiter().getId().equals(recruiter.getId())) {
            throw new UnauthorizedAccessException("Você não é o dono desta vaga");
        }

        Match match = matchRepository.findByCandidateAndVaga(candidate, vaga)
                .orElse(new Match(UUID.randomUUID(), candidate, vaga));

        match.setLikedByRecruiter(request.isLiked());
        match.updateStatus();

        Match savedMatch = matchRepository.save(match);
        if (savedMatch.getStatus() == MatchStatus.MATCHED) {
            notificationService.sendMatchNotification(savedMatch);
        }

        return new MatchResponse(savedMatch);
    }
}