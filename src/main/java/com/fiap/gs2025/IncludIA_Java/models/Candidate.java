package com.fiap.gs2025.IncludIA_Java.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senhaHash;

    @Embedded
    private Endereco endereco;

    private Integer raioBuscaKm;

    @Column(length = 2000)
    private String resumoPerfil;

    @Column(length = 2000)
    private String resumoInclusivoIA;

    private String fotoPerfilUrl;

    private boolean isAtive;

    private boolean isOnline;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "candidate_skills")
    private Set<Skill> skills = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Experience> experiencias = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Education> formacoes = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Voluntariado> voluntariados = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CandidateIdioma> idiomas = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "candidate")
    private Set<Match> matches = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "candidate")
    private Set<SavedJob> vagasSalvas = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "candidate")
    private Set<ProfileView> viewsNoPerfil = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "candidate")
    private Set<Notification> notificacoes = new HashSet<>();

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Integer getRaioBuscaKm() {
        return raioBuscaKm;
    }

    public void setRaioBuscaKm(Integer raioBuscaKm) {
        this.raioBuscaKm = raioBuscaKm;
    }

    public String getResumoPerfil() {
        return resumoPerfil;
    }

    public void setResumoPerfil(String resumoPerfil) {
        this.resumoPerfil = resumoPerfil;
    }

    public String getResumoInclusivoIA() {
        return resumoInclusivoIA;
    }

    public void setResumoInclusivoIA(String resumoInclusivoIA) {
        this.resumoInclusivoIA = resumoInclusivoIA;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public boolean isAtive() {
        return isAtive;
    }

    public void setAtive(boolean ative) {
        isAtive = ative;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<Experience> getExperiencias() {
        return experiencias;
    }

    public void setExperiencias(Set<Experience> experiencias) {
        this.experiencias = experiencias;
    }

    public Set<Education> getFormacoes() {
        return formacoes;
    }

    public void setFormacoes(Set<Education> formacoes) {
        this.formacoes = formacoes;
    }

    public Set<Voluntariado> getVoluntariados() {
        return voluntariados;
    }

    public void setVoluntariados(Set<Voluntariado> voluntariados) {
        this.voluntariados = voluntariados;
    }

    public Set<CandidateIdioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Set<CandidateIdioma> idiomas) {
        this.idiomas = idiomas;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }

    public Set<SavedJob> getVagasSalvas() {
        return vagasSalvas;
    }

    public void setVagasSalvas(Set<SavedJob> vagasSalvas) {
        this.vagasSalvas = vagasSalvas;
    }

    public Set<ProfileView> getViewsNoPerfil() {
        return viewsNoPerfil;
    }

    public void setViewsNoPerfil(Set<ProfileView> viewsNoPerfil) {
        this.viewsNoPerfil = viewsNoPerfil;
    }

    public Set<Notification> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(Set<Notification> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}