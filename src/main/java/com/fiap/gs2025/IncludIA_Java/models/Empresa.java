package com.fiap.gs2025.IncludIA_Java.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "t_inc_empresa") // NOME CORRETO DO SQL
public class Empresa {
    @Id
    @Column(name = "id_empresa") // NOME CORRETO DO SQL
    private UUID id;

    @Column(name = "nome_oficial", nullable = false)
    private String nomeOficial;

    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    @Column(nullable = false, unique = true)
    private String cnpj;

    private String localizacao;

    @Column(length = 4000)
    private String descricao;

    // REMOVIDO: private String cultura;

    @Column(name = "foto_logo")
    private String fotoLogo; // NOVO CAMPO

    @Column(name = "foto_capa_url")
    private String fotoCapaUrl;

    @Column(name = "is_verificado")
    private boolean isVerificado; // NOVO CAMPO

    @Column(name = "is_ative")
    private boolean isAtive = true;

    @JsonManagedReference
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private Set<Recruiter> recruiters = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private Set<JobVaga> vagas = new HashSet<>();

    // --- GETTERS E SETTERS ---

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNomeOficial() { return nomeOficial; }
    public void setNomeOficial(String nomeOficial) { this.nomeOficial = nomeOficial; }

    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getFotoLogo() { return fotoLogo; }
    public void setFotoLogo(String fotoLogo) { this.fotoLogo = fotoLogo; }

    public String getFotoCapaUrl() { return fotoCapaUrl; }
    public void setFotoCapaUrl(String fotoCapaUrl) { this.fotoCapaUrl = fotoCapaUrl; }

    public boolean isVerificado() { return isVerificado; }
    public void setVerificado(boolean verificado) { isVerificado = verificado; }

    public boolean isAtive() { return isAtive; }
    public void setAtive(boolean ative) { isAtive = ative; }

    public Set<Recruiter> getRecruiters() { return recruiters; }
    public void setRecruiters(Set<Recruiter> recruiters) { this.recruiters = recruiters; }

    public Set<JobVaga> getVagas() { return vagas; }
    public void setVagas(Set<JobVaga> vagas) { this.vagas = vagas; }
}
