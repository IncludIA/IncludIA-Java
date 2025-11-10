package com.fiap.gs2025.IncludIA_Java.models;

import com.fiap.gs2025.IncludIA_Java.enums.JobType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "job_vagas")
public class JobVaga {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 5000)
    private String descricaoOriginal;

    @Column(length = 5000)
    private String descricaoInclusiva;

    private String localizacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType tipoVaga;

    private BigDecimal salarioMin;

    private BigDecimal salarioMax;

    private String beneficios;

    private String experienciaRequerida;

    private boolean isAtiva;

    @Column(nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id", nullable = false)
    private Recruiter recruiter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "vaga_skills",
            joinColumns = @JoinColumn(name = "job_vaga_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skillsDesejadas = new HashSet<>();

    @OneToMany(mappedBy = "vaga")
    private Set<Match> matches = new HashSet<>();


}