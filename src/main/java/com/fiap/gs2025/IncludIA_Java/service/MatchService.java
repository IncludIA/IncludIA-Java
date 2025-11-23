package com.fiap.gs2025.IncludIA_Java.service;

import com.fiap.gs2025.IncludIA_Java.dto.response.CandidateMatchResponse;
import com.fiap.gs2025.IncludIA_Java.dto.response.CandidateProfileResponse;
import com.fiap.gs2025.IncludIA_Java.dto.response.MatchResponse;
import com.fiap.gs2025.IncludIA_Java.enums.MatchStatus;
import com.fiap.gs2025.IncludIA_Java.exceptions.ResourceNotFoundException;
import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.JobVaga;
import com.fiap.gs2025.IncludIA_Java.models.Match;
import com.fiap.gs2025.IncludIA_Java.models.Recruiter;
import com.fiap.gs2025.IncludIA_Java.repository.CandidateRepository;
import com.fiap.gs2025.IncludIA_Java.repository.JobVagaRepository;
import com.fiap.gs2025.IncludIA_Java.repository.MatchRepository;
import com.fiap.gs2025.IncludIA_Java.repository.RecruiterRepository;
import com.fiap.gs2025.IncludIA_Java.security.CustomUserDetails;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${mq.queue.match}")
    private String matchQueue;

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

    @Transactional
    public void processarSwipe(UUID vagaId, UUID candidateId, boolean isLiked) {
        if (isLiked) {
            JobVaga vaga = jobVagaRepository.findById(vagaId)
                    .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada"));
            Candidate candidate = candidateRepository.findById(candidateId)
                    .orElseThrow(() -> new ResourceNotFoundException("Candidato não encontrado"));

            Match match = new Match();
            match.setVaga(vaga);
            match.setCandidate(candidate);
            match.setStatus(MatchStatus.MATCHED);

            matchRepository.save(match);

            String mensagem = "Novo Match! Vaga: " + vaga.getTitulo() + " - Candidato: " + candidate.getNome();
            rabbitTemplate.convertAndSend(matchQueue, mensagem);
        }
    }

    public List<CandidateMatchResponse> getCandidatesFeedForJob(UUID vagaId) {
        JobVaga vaga = jobVagaRepository.findById(vagaId)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada"));

        List<Candidate> allCandidates = candidateRepository.findAll().stream()
                .filter(Candidate::isAtive)
                .collect(Collectors.toList());

        List<UUID> swipedCandidateIds = matchRepository.findAll().stream()
                .filter(m -> m.getVaga().getId().equals(vagaId))
                .map(m -> m.getCandidate().getId())
                .collect(Collectors.toList());

        return allCandidates.stream()
                .filter(c -> !swipedCandidateIds.contains(c.getId()))
                .map(candidate -> calculateCompatibility(candidate, vaga))
                .sorted((c1, c2) -> Integer.compare(c2.porcentagemCompatibilidade(), c1.porcentagemCompatibilidade()))
                .collect(Collectors.toList());
    }

    public CandidateProfileResponse getCandidateById(UUID candidateId) {
        Candidate c = candidateRepository.findById(candidateId)
                .filter(Candidate::isAtive)
                .orElseThrow(() -> new ResourceNotFoundException("Candidato não encontrado ou inativo"));

        return new CandidateProfileResponse(c);
    }

    public List<CandidateMatchResponse> findBestCandidatesForJob(UUID vagaId) {
        JobVaga vaga = jobVagaRepository.findById(vagaId)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada"));

        List<Candidate> allCandidates = candidateRepository.findAll();

        return allCandidates.stream()
                .map(candidate -> calculateCompatibility(candidate, vaga))
                .sorted((c1, c2) -> Integer.compare(c2.porcentagemCompatibilidade(), c1.porcentagemCompatibilidade()))
                .collect(Collectors.toList());
    }

    public CandidateMatchResponse calculateCompatibility(Candidate candidate, JobVaga vaga) {
        int score = 0;
        StringBuilder motivos = new StringBuilder();

        long matchingSkills = candidate.getSkills().stream()
                .filter(skill -> vaga.getSkillsDesejadas().contains(skill))
                .count();

        int totalSkillsVaga = vaga.getSkillsDesejadas().size();
        if (totalSkillsVaga > 0) {
            int skillScore = (int) ((double) matchingSkills / totalSkillsVaga * 60);
            score += skillScore;
            if(matchingSkills > 0) motivos.append(matchingSkills).append(" skills em comum. ");
        }

        if (candidate.getEndereco() != null && vaga.getLocalizacao() != null) {
            if (vaga.getLocalizacao().toLowerCase().contains(candidate.getEndereco().getCidade().toLowerCase())) {
                score += 20;
                motivos.append("Localização compatível. ");
            }
        }

        if (vaga.getModeloTrabalho().toString().equals("REMOTO")) {
            score += 20;
            motivos.append("Vaga remota. ");
        }

        return new CandidateMatchResponse(new CandidateProfileResponse(candidate), score, motivos.toString());
    }
}