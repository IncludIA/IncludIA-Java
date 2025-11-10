package com.fiap.gs2025.IncludIA_Java.service;

import com.fiap.gs2025.IncludIA_Java.dto.request.SavedCandidateRequest;
import com.fiap.gs2025.IncludIA_Java.dto.request.SavedJobRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.SavedCandidateResponse;
import com.fiap.gs2025.IncludIA_Java.dto.response.SavedJobResponse;
import com.fiap.gs2025.IncludIA_Java.exceptions.DuplicateResourceException;
import com.fiap.gs2025.IncludIA_Java.exceptions.ResourceNotFoundException;
import com.fiap.gs2025.IncludIA_Java.models.*;
import com.fiap.gs2025.IncludIA_Java.repository.*;
import com.fiap.gs2025.IncludIA_Java.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class SavedContentService {

    @Autowired
    private SavedJobRepository savedJobRepository;
    @Autowired
    private SavedCandidateRepository savedCandidateRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private RecruiterRepository recruiterRepository;
    @Autowired
    private JobVagaRepository jobVagaRepository;

    @Transactional
    public SavedJobResponse saveJob(SavedJobRequest request) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Candidate candidate = candidateRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidato não encontrado"));
        JobVaga vaga = jobVagaRepository.findById(request.vagaId())
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada"));

        if (savedJobRepository.findByCandidateAndVaga(candidate, vaga).isPresent()) {
            throw new DuplicateResourceException("Vaga já salva");
        }

        SavedJob savedJob = new SavedJob();
        savedJob.setId(UUID.randomUUID());
        savedJob.setCandidate(candidate);
        savedJob.setVaga(vaga);
        savedJob.setSavedAt(Instant.now());

        SavedJob saved = savedJobRepository.save(savedJob);
        return new SavedJobResponse(saved);
    }

    @Transactional
    public SavedCandidateResponse saveCandidate(SavedCandidateRequest request) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Recruiter recruiter = recruiterRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Recrutador não encontrado"));
        Candidate candidate = candidateRepository.findById(request.candidateId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidato não encontrado"));

        if (savedCandidateRepository.findByRecruiterAndCandidate(recruiter, candidate).isPresent()) {
            throw new DuplicateResourceException("Candidato já salvo");
        }

        SavedCandidate savedCandidate = new SavedCandidate();
        savedCandidate.setId(UUID.randomUUID());
        savedCandidate.setRecruiter(recruiter);
        savedCandidate.setCandidate(candidate);
        savedCandidate.setSavedAt(Instant.now());

        SavedCandidate saved = savedCandidateRepository.save(savedCandidate);
        return new SavedCandidateResponse(saved);
    }
}