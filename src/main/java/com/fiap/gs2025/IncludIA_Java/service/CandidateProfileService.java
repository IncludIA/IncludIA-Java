package com.fiap.gs2025.IncludIA_Java.service;

import com.fiap.gs2025.IncludIA_Java.dto.request.*;
import com.fiap.gs2025.IncludIA_Java.dto.response.*;
import com.fiap.gs2025.IncludIA_Java.enums.SkillType;
import com.fiap.gs2025.IncludIA_Java.enums.TipoContrato;
import com.fiap.gs2025.IncludIA_Java.exceptions.ResourceNotFoundException;
import com.fiap.gs2025.IncludIA_Java.exceptions.UnauthorizedAccessException;
import com.fiap.gs2025.IncludIA_Java.models.*;
import com.fiap.gs2025.IncludIA_Java.repository.*;
import com.fiap.gs2025.IncludIA_Java.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
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

    @Autowired
    private AiService aiService;

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
        Candidate savedCandidate = candidateRepository.save(candidate);
        return new CandidateProfileResponse(savedCandidate);
    }

    @Transactional
    public CandidateProfileResponse generateMyAIProfile() {
        Candidate candidate = getCurrentAuthenticatedCandidate();
        String resumoInclusivo = aiService.gerarResumoInclusivo(candidate);
        candidate.setResumoInclusivoIA(resumoInclusivo);
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

    @Transactional
    public void deleteExperience(UUID experienceId) {
        Candidate candidate = getCurrentAuthenticatedCandidate();
        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Experiência não encontrada"));

        if (!experience.getCandidate().getId().equals(candidate.getId())) {
            throw new UnauthorizedAccessException("Esta experiência não pertence a você");
        }

        experienceRepository.delete(experience);
    }

    @Transactional
    public CandidateProfileResponse updateFullProfile(CandidateFullUpdateRequest request) {
        Candidate candidate = getCurrentAuthenticatedCandidate();

        if (request.resumoPerfil() != null) candidate.setResumoPerfil(request.resumoPerfil());
        if (request.resumoInclusivoIA() != null) candidate.setResumoInclusivoIA(request.resumoInclusivoIA());

        if (candidate.getEndereco() != null) {
            if (request.cidade() != null) candidate.getEndereco().setCidade(request.cidade());
            if (request.estado() != null) candidate.getEndereco().setEstado(request.estado());
        } else {
            Endereco end = new Endereco();
            end.setCidade(request.cidade());
            end.setEstado(request.estado());
            candidate.setEndereco(end);
        }

        if (request.skills() != null) {
            candidate.getSkills().clear();
            for (CandidateFullUpdateRequest.SkillFullRequest sReq : request.skills()) {
                Skill skill = skillRepository.findByNome(sReq.nome())
                        .orElseGet(() -> {
                            Skill newSkill = new Skill();
                            newSkill.setId(UUID.randomUUID());
                            newSkill.setNome(sReq.nome());
                            newSkill.setTipoSkill(sReq.tipoSkill() != null ? sReq.tipoSkill() : SkillType.HARD_SKILL);
                            return skillRepository.save(newSkill);
                        });
                candidate.getSkills().add(skill);
            }
        }

        if (request.experiencias() != null) {
            candidate.getExperiencias().clear();

            for (CandidateFullUpdateRequest.ExperienceFullRequest expReq : request.experiencias()) {
                Experience exp = new Experience();
                exp.setId(UUID.randomUUID());
                exp.setTituloCargo(expReq.tituloCargo());
                exp.setTipoEmprego(expReq.tipoEmprego() != null ? expReq.tipoEmprego() : TipoContrato.TEMPO_INTEGRAL);
                exp.setDataInicio(expReq.dataInicio());
                exp.setDataFim(expReq.dataFim());
                exp.setDescricao(expReq.descricao());
                exp.setCandidate(candidate);

                if (expReq.empresa() != null && expReq.empresa().nomeFantasia() != null) {
                }

                candidate.getExperiencias().add(exp);
            }
        }

        if (request.formacoes() != null) {
            candidate.getFormacoes().clear();
            for (CandidateFullUpdateRequest.EducationFullRequest eduReq : request.formacoes()) {
                Education edu = new Education();
                edu.setId(UUID.randomUUID());
                edu.setNomeInstituicao(eduReq.nomeInstituicao());
                edu.setGrau(eduReq.grau());
                edu.setAreaEstudo(eduReq.areaEstudo());
                edu.setDataInicio(eduReq.dataInicio());
                edu.setDataFim(eduReq.dataFim());
                edu.setDescricao(eduReq.descricao());
                edu.setCandidate(candidate);
                candidate.getFormacoes().add(edu);
            }
        }

        if (request.voluntariados() != null) {
            candidate.getVoluntariados().clear();
            for (CandidateFullUpdateRequest.VoluntariadoFullRequest volReq : request.voluntariados()) {
                Voluntariado vol = new Voluntariado();
                vol.setId(UUID.randomUUID());
                vol.setOrganizacao(volReq.organizacao());
                vol.setFuncao(volReq.funcao());
                vol.setDescricao(volReq.descricao());
                vol.setDataInicio(volReq.dataInicio());
                vol.setDataFim(volReq.dataFim());
                vol.setCandidate(candidate);
                candidate.getVoluntariados().add(vol);
            }
        }

        if (request.idiomas() != null) {
            candidate.getIdiomas().clear();
            for (CandidateFullUpdateRequest.IdiomaFullRequest idioReq : request.idiomas()) {
                Optional<Idioma> idiomaOpt = idiomaRepository.findByNome(idioReq.nomeIdioma());
                if (idiomaOpt.isPresent()) {
                    CandidateIdioma ci = new CandidateIdioma();
                    ci.setId(UUID.randomUUID());
                    ci.setCandidate(candidate);
                    ci.setIdioma(idiomaOpt.get());
                    ci.setNivelProficiencia(idioReq.nivelProficiencia());
                    candidate.getIdiomas().add(ci);
                }
            }
        }

        return new CandidateProfileResponse(candidateRepository.save(candidate));
    }

    @Transactional
    public void deleteMyAccount() {
        Candidate candidate = getCurrentAuthenticatedCandidate();
        candidate.setAtive(false); // Soft Delete
        candidateRepository.save(candidate);
    }
}