package com.fiap.gs2025.IncludIA_Java.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "empresas")
public class Empresa {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String nomeOficial;

    private String nomeFantasia;

    @Column(nullable = false, unique = true)
    private String cnpj;

    private String localizacao;

    @Column(length = 4000)
    private String descricao;

    @Column(length = 4000)
    private String cultura;

    private String fotoCapaUrl;

    @JsonManagedReference
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private Set<Recruiter> recruiters = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private Set<JobVaga> vagas = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomeOficial() {
        return nomeOficial;
    }

    public void setNomeOficial(String nomeOficial) {
        this.nomeOficial = nomeOficial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCultura() {
        return cultura;
    }

    public void setCultura(String cultura) {
        this.cultura = cultura;
    }

    public String getFotoCapaUrl() {
        return fotoCapaUrl;
    }

    public void setFotoCapaUrl(String fotoCapaUrl) {
        this.fotoCapaUrl = fotoCapaUrl;
    }

    public Set<Recruiter> getRecruiters() {
        return recruiters;
    }

    public void setRecruiters(Set<Recruiter> recruiters) {
        this.recruiters = recruiters;
    }

    public Set<JobVaga> getVagas() {
        return vagas;
    }

    public void setVagas(Set<JobVaga> vagas) {
        this.vagas = vagas;
    }
}