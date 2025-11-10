package com.fiap.gs2025.IncludIA_Java.service;

import com.fiap.gs2025.IncludIA_Java.dto.request.IdiomaRequest;
import com.fiap.gs2025.IncludIA_Java.dto.request.SkillRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.IdiomaResponse;
import com.fiap.gs2025.IncludIA_Java.dto.response.SkillResponse;
import com.fiap.gs2025.IncludIA_Java.exceptions.DuplicateResourceException;
import com.fiap.gs2025.IncludIA_Java.models.Idioma;
import com.fiap.gs2025.IncludIA_Java.models.Skill;
import com.fiap.gs2025.IncludIA_Java.repository.IdiomaRepository;
import com.fiap.gs2025.IncludIA_Java.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private IdiomaRepository idiomaRepository;

    @Transactional
    @CacheEvict(value = "skills", allEntries = true)
    public SkillResponse createSkill(SkillRequest request) {
        if (skillRepository.findByNome(request.nome()).isPresent()) {
            throw new DuplicateResourceException("Skill já cadastrada");
        }
        Skill skill = new Skill();
        skill.setId(UUID.randomUUID());
        skill.setNome(request.nome());
        skill.setTipoSkill(request.tipoSkill());

        Skill savedSkill = skillRepository.save(skill);
        return new SkillResponse(savedSkill);
    }

    @Cacheable("skills")
    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAll().stream()
                .map(SkillResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value = "idiomas", allEntries = true)
    public IdiomaResponse createIdioma(IdiomaRequest request) {
        if (idiomaRepository.findByNome(request.nome()).isPresent()) {
            throw new DuplicateResourceException("Idioma já cadastrado");
        }
        Idioma idioma = new Idioma();
        idioma.setId(UUID.randomUUID());
        idioma.setNome(request.nome());

        Idioma savedIdioma = idiomaRepository.save(idioma);
        return new IdiomaResponse(savedIdioma);
    }

    @Cacheable("idiomas")
    public List<IdiomaResponse> getAllIdiomas() {
        return idiomaRepository.findAll().stream()
                .map(IdiomaResponse::new)
                .collect(Collectors.toList());
    }
}