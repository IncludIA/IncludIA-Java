package com.fiap.gs2025.IncludIA_Java.service;

import com.fiap.gs2025.IncludIA_Java.dto.request.MatchActionRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.CandidateProfileResponse;
import com.fiap.gs2025.IncludIA_Java.dto.response.MatchResponse;
import com.fiap.gs2025.IncludIA_Java.enums.MatchStatus;
import com.fiap.gs2025.IncludIA_Java.exceptions.ResourceNotFoundException;
import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.JobVaga;
import com.fiap.gs2025.IncludIA_Java.models.Recruiter;
import com.fiap.gs2025.IncludIA_Java.repository.CandidateRepository;
import com.fiap.gs2025.IncludIA_Java.repository.JobVagaRepository;
import com.fiap.gs2025.IncludIA_Java.repository.MatchRepository;
import com.fiap.gs2025.IncludIA_Java.repository.RecruiterRepository;
import com.fiap.gs2025.IncludIA_Java.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private JobVagaRepository jobVagaRepository;

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

    public List<MatchActionRequest> findBestCandidatesForJob(UUID vagaId) {
        JobVaga vaga = jobVagaRepository.findById(vagaId)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada"));

        List<Candidate> allCandidates = candidateRepository.findAll(); // Em prod, filtre isso no banco!

        return allCandidates.stream()
                .map(candidate -> calculateCompatibility(candidate, vaga))
                .sorted((c1, c2) -> Integer.compare(c2.porcentagemCompatibilidade(), c1.porcentagemCompatibilidade())) // Ordem decrescente
                .collect(Collectors.toList());
    }

    private MatchActionRequest calculateCompatibility(Candidate candidate, JobVaga vaga) {
        int score = 0;
        StringBuilder motivos = new StringBuilder();

        // 1. Skills (Peso alto: 60%)
        long matchingSkills = candidate.getSkills().stream()
                .filter(skill -> vaga.getSkillsDesejadas().contains(skill))
                .count();

        int totalSkillsVaga = vaga.getSkillsDesejadas().size();
        if (totalSkillsVaga > 0) {
            int skillScore = (int) ((double) matchingSkills / totalSkillsVaga * 60);
            score += skillScore;
            if(matchingSkills > 0) motivos.append(matchingSkills).append(" skills em comum. ");
        }

        // 2. Localização (Peso: 20%)
        if (candidate.getEndereco() != null && vaga.getLocalizacao() != null) {
            // Lógica simples: Se a cidade do candidato estiver na string de localização da vaga
            if (vaga.getLocalizacao().toLowerCase().contains(candidate.getEndereco().getCidade().toLowerCase())) {
                score += 20;
                motivos.append("Localização compatível. ");
            }
        }

        // 3. Modelo de Trabalho (Peso: 20%)
        // (Assumindo que adicionamos ModeloTrabalho ao Candidato depois, ou inferindo)
        // Se a vaga for REMOTO, ganha pontos automaticamente
        if (vaga.getModeloTrabalho().toString().equals("REMOTO")) {
            score += 20;
            motivos.append("Vaga remota. ");
        }

        return new MatchActionRequest(new CandidateProfileResponse(candidate), score, motivos.toString());
    }
}