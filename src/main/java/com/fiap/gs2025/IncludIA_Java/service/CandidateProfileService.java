package com.fiap.gs2025.IncludIA_Java.service;

import com.fiap.gs2025.IncludIA_Java.dto.request.*;
import com.fiap.gs2025.IncludIA_Java.dto.response.*;
import com.fiap.gs2025.IncludIA_Java.exceptions.ResourceNotFoundException;
import com.fiap.gs2025.IncludIA_Java.models.*;
import com.fiap.gs2025.IncludIA_Java.repository.*;
import com.fiap.gs2025.IncludIA_Java.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CandidateProfileService {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private ExperienceRepository experienceRepository;
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private VoluntariadoRepository voluntariadoRepository;
    @Autowired
    private CandidateIdiomaRepository candidateIdiomaRepository;
    @Autowired
    private IdiomaRepository idiomaRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private EmpresaRepository empresaRepository;

    private Candidate getCurrentAuthenticatedCandidate() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return candidateRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidato não encontrado"));
    }

    @Transactional(readOnly = true)
    public CandidateProfileResponse getFullProfile() {
        Candidate candidate = getCurrentAuthenticatedCandidate();
        return new CandidateProfileResponse(candidate);
    }

    @Transactional
    public CandidateProfileResponse updateProfileSummary(String newSummary) {
        Candidate candidate = getCurrentAuthenticatedCandidate();
        candidate.setResumoPerfil(newSummary);

        // Fallback temporário sem IA
        candidate.setResumoInclusivoIA(newSummary);

        Candidate savedCandidate = candidateRepository.save(candidate);
        return new CandidateProfileResponse(savedCandidate);
    }

    @Transactional
    public ExperienceResponse addExperience(ExperienceRequest request) {
        Candidate candidate = getCurrentAuthenticatedCandidate();
        Empresa empresa = null;
        if (request.empresaId() != null) {
            empresa = empresaRepository.findById(request.empresaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));
        }

        Experience experience = new Experience();
        experience.setId(UUID.randomUUID());
        experience.setTituloCargo(request.tituloCargo());
        experience.setTipoEmprego(request.tipoEmprego());
        experience.setDataInicio(request.dataInicio());
        experience.setDataFim(request.dataFim());
        experience.setDescricao(request.descricao());
        experience.setCandidate(candidate);
        experience.setEmpresa(empresa);

        Experience savedExperience = experienceRepository.save(experience);
        return new ExperienceResponse(savedExperience);
    }

    @Transactional
    public EducationResponse addEducation(EducationRequest request) {
        Candidate candidate = getCurrentAuthenticatedCandidate();

        Education education = new Education();
        education.setId(UUID.randomUUID());
        education.setNomeInstituicao(request.nomeInstituicao());
        education.setGrau(request.grau());
        education.setAreaEstudo(request.areaEstudo());
        education.setDataInicio(request.dataInicio());
        education.setDataFim(request.dataFim());
        education.setDescricao(request.descricao());
        education.setCandidate(candidate);

        Education savedEducation = educationRepository.save(education);
        return new EducationResponse(savedEducation);
    }

    @Transactional
    public VoluntariadoResponse addVoluntariado(VoluntariadoRequest request) {
        Candidate candidate = getCurrentAuthenticatedCandidate();

        Voluntariado voluntariado = new Voluntariado();
        voluntariado.setId(UUID.randomUUID());
        voluntariado.setOrganizacao(request.organizacao());
        voluntariado.setFuncao(request.funcao());
        voluntariado.setDescricao(request.descricao());
        voluntariado.setDataInicio(request.dataInicio());
        voluntariado.setDataFim(request.dataFim());
        voluntariado.setCandidate(candidate);

        Voluntariado savedVoluntariado = voluntariadoRepository.save(voluntariado);
        return new VoluntariadoResponse(savedVoluntariado);
    }

    @Transactional
    public CandidateIdiomaResponse addIdioma(CandidateIdiomaRequest request) {
        Candidate candidate = getCurrentAuthenticatedCandidate();
        Idioma idioma = idiomaRepository.findByNome(request.nomeIdioma())
                .orElseThrow(() -> new ResourceNotFoundException("Idioma não encontrado"));

        CandidateIdioma candidateIdioma = new CandidateIdioma();
        candidateIdioma.setId(UUID.randomUUID());
        candidateIdioma.setCandidate(candidate);
        candidateIdioma.setIdioma(idioma);
        candidateIdioma.setNivelProficiencia(request.nivelProficiencia());

        CandidateIdioma savedCI = candidateIdiomaRepository.save(candidateIdioma);
        return new CandidateIdiomaResponse(savedCI);
    }

    @Transactional
    public CandidateProfileResponse addSkillToProfile(UUID skillId) {
        Candidate candidate = getCurrentAuthenticatedCandidate();
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill não encontrada"));

        candidate.getSkills().add(skill);
        Candidate savedCandidate = candidateRepository.save(candidate);
        return new CandidateProfileResponse(savedCandidate);
    }
}