package com.fiap.gs2025.IncludIA_Java;


import com.fiap.gs2025.IncludIA_Java.dto.response.CandidateMatchResponse;
import com.fiap.gs2025.IncludIA_Java.enums.ModeloTrabalho;
import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.Endereco;
import com.fiap.gs2025.IncludIA_Java.models.JobVaga;
import com.fiap.gs2025.IncludIA_Java.models.Skill;
import com.fiap.gs2025.IncludIA_Java.service.MatchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @InjectMocks
    private MatchService matchService;

    @Test
    @DisplayName("Deve calcular 100% de match para candidato perfeito (Skills + Remoto)")
    void testCalculateCompatibility_PerfectMatch() {
        Skill javaSkill = new Skill();
        javaSkill.setNome("Java");

        Set<Skill> skills = new HashSet<>();
        skills.add(javaSkill);

        Candidate candidate = new Candidate();
        candidate.setSkills(skills);
        candidate.setNome("Dev Senior");

        JobVaga vaga = new JobVaga();
        vaga.setSkillsDesejadas(skills);
        vaga.setModeloTrabalho(ModeloTrabalho.REMOTO);
        CandidateMatchResponse result = matchService.calculateCompatibility(candidate, vaga);

        assertEquals(80, result.porcentagemCompatibilidade());
        assertTrue(result.motivoCompatibilidade().contains("Vaga remota"));
    }

    @Test
    @DisplayName("Deve calcular match zero se não houver skills em comum")
    void testCalculateCompatibility_NoMatch() {
        Skill javaSkill = new Skill(); javaSkill.setNome("Java");
        Skill pythonSkill = new Skill(); pythonSkill.setNome("Python");

        Candidate candidate = new Candidate();
        candidate.setSkills(Set.of(javaSkill));

        JobVaga vaga = new JobVaga();
        vaga.setSkillsDesejadas(Set.of(pythonSkill));
        vaga.setModeloTrabalho(ModeloTrabalho.PRESENCIAL);

        CandidateMatchResponse result = matchService.calculateCompatibility(candidate, vaga);

        assertEquals(0, result.porcentagemCompatibilidade());
    }
}