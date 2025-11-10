package com.fiap.gs2025.IncludIA_Java.service;

import com.fiap.gs2025.IncludIA_Java.dto.request.JobVagaRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.JobVagaResponse;
import com.fiap.gs2025.IncludIA_Java.exceptions.ResourceNotFoundException;
import com.fiap.gs2025.IncludIA_Java.exceptions.UnauthorizedAccessException;
import com.fiap.gs2025.IncludIA_Java.models.JobVaga;
import com.fiap.gs2025.IncludIA_Java.models.Recruiter;
import com.fiap.gs2025.IncludIA_Java.models.Skill;
import com.fiap.gs2025.IncludIA_Java.repository.JobVagaRepository;
import com.fiap.gs2025.IncludIA_Java.repository.RecruiterRepository;
import com.fiap.gs2025.IncludIA_Java.repository.SkillRepository;
import com.fiap.gs2025.IncludIA_Java.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobVagaService {

    @Autowired
    private JobVagaRepository jobVagaRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private SkillRepository skillRepository;


    private Recruiter getCurrentAuthenticatedRecruiter() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return recruiterRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Recrutador não encontrado"));
    }

    @Transactional
    public JobVagaResponse createVaga(JobVagaRequest request) {
        Recruiter recruiter = getCurrentAuthenticatedRecruiter();

        Set<Skill> skills = request.skillIds().stream()
                .map(skillId -> skillRepository.findById(skillId)
                        .orElseThrow(() -> new ResourceNotFoundException("Skill não encontrada: " + skillId)))
                .collect(Collectors.toSet());


        JobVaga vaga = new JobVaga();
        vaga.setId(UUID.randomUUID());
        vaga.setTitulo(request.titulo());
        vaga.setDescricaoOriginal(request.descricaoOriginal());
        vaga.setLocalizacao(request.localizacao());
        vaga.setTipoVaga(request.tipoVaga());
        vaga.setModeloTrabalho(request.modeloTrabalho());
        vaga.setSalarioMin(request.salarioMin());
        vaga.setSalarioMax(request.salarioMax());
        vaga.setBeneficios(request.beneficios());
        vaga.setExperienciaRequerida(request.experienciaRequerida());
        vaga.setSkillsDesejadas(skills);
        vaga.setRecruiter(recruiter);
        vaga.setEmpresa(recruiter.getEmpresa());
        vaga.setAtiva(true);
        vaga.setCreatedAt(Instant.now());

        JobVaga savedVaga = jobVagaRepository.save(vaga);
        return new JobVagaResponse(savedVaga);
    }

    @Transactional(readOnly = true)
    public JobVagaResponse getVagaById(UUID vagaId) {
        JobVaga vaga = jobVagaRepository.findById(vagaId)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada"));
        return new JobVagaResponse(vaga);
    }

    @Transactional(readOnly = true)
    public List<JobVagaResponse> getVagasByAuthenticatedRecruiter() {
        Recruiter recruiter = getCurrentAuthenticatedRecruiter();
        return jobVagaRepository.findByRecruiter(recruiter).stream()
                .map(JobVagaResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<JobVagaResponse> getAllActiveVagas() {
        return jobVagaRepository.findAll().stream()
                .filter(JobVaga::isAtiva)
                .map(JobVagaResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public JobVagaResponse updateVaga(UUID vagaId, JobVagaRequest request) {
        Recruiter recruiter = getCurrentAuthenticatedRecruiter();
        JobVaga vaga = jobVagaRepository.findById(vagaId)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada"));

        if (!vaga.getRecruiter().getId().equals(recruiter.getId())) {
            throw new UnauthorizedAccessException("Você não tem permissão para editar esta vaga");
        }

        JobVaga savedVaga = jobVagaRepository.save(vaga);
        return new JobVagaResponse(savedVaga);
    }
}