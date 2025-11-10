package com.fiap.gs2025.IncludIA_Java.models;

import com.fiap.gs2025.IncludIA_Java.enums.ModeloTrabalho;
import com.fiap.gs2025.IncludIA_Java.enums.TipoContrato;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
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
    private TipoContrato tipoVaga;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModeloTrabalho modeloTrabalho;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricaoOriginal() {
        return descricaoOriginal;
    }

    public void setDescricaoOriginal(String descricaoOriginal) {
        this.descricaoOriginal = descricaoOriginal;
    }

    public String getDescricaoInclusiva() {
        return descricaoInclusiva;
    }

    public void setDescricaoInclusiva(String descricaoInclusiva) {
        this.descricaoInclusiva = descricaoInclusiva;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public TipoContrato getTipoVaga() {
        return tipoVaga;
    }

    public void setTipoVaga(TipoContrato tipoVaga) {
        this.tipoVaga = tipoVaga;
    }

    public ModeloTrabalho getModeloTrabalho() {
        return modeloTrabalho;
    }

    public void setModeloTrabalho(ModeloTrabalho modeloTrabalho) {
        this.modeloTrabalho = modeloTrabalho;
    }

    public BigDecimal getSalarioMin() {
        return salarioMin;
    }

    public void setSalarioMin(BigDecimal salarioMin) {
        this.salarioMin = salarioMin;
    }

    public BigDecimal getSalarioMax() {
        return salarioMax;
    }

    public void setSalarioMax(BigDecimal salarioMax) {
        this.salarioMax = salarioMax;
    }

    public String getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(String beneficios) {
        this.beneficios = beneficios;
    }

    public String getExperienciaRequerida() {
        return experienciaRequerida;
    }

    public void setExperienciaRequerida(String experienciaRequerida) {
        this.experienciaRequerida = experienciaRequerida;
    }

    public boolean isAtiva() {
        return isAtiva;
    }

    public void setAtiva(boolean ativa) {
        isAtiva = ativa;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Recruiter getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Set<Skill> getSkillsDesejadas() {
        return skillsDesejadas;
    }

    public void setSkillsDesejadas(Set<Skill> skillsDesejadas) {
        this.skillsDesejadas = skillsDesejadas;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }
}