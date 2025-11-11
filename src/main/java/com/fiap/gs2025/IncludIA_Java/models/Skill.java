package com.fiap.gs2025.IncludIA_Java.models;

import com.fiap.gs2025.IncludIA_Java.enums.SkillType;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "skills")
public class Skill {
    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SkillType tipoSkill;

    @ManyToMany(mappedBy = "skills")
    private Set<Candidate> candidates = new HashSet<>();

    @ManyToMany(mappedBy = "skillsDesejadas")
    private Set<JobVaga> vagas = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public SkillType getTipoSkill() {
        return tipoSkill;
    }

    public void setTipoSkill(SkillType tipoSkill) {
        this.tipoSkill = tipoSkill;
    }

    public Set<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(Set<Candidate> candidates) {
        this.candidates = candidates;
    }

    public Set<JobVaga> getVagas() {
        return vagas;
    }

    public void setVagas(Set<JobVaga> vagas) {
        this.vagas = vagas;
    }
}