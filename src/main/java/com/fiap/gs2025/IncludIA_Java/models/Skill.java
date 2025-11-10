package com.fiap.gs2025.IncludIA_Java.models;

import com.fiap.gs2025.IncludIA_Java.enums.SkillType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
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
}