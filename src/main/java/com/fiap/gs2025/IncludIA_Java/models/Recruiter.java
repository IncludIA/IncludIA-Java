package com.fiap.gs2025.IncludIA_Java.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "recruiters")
public class Recruiter {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senhaHash;

    private boolean isAtive;

    private String fotoPerfilUrl;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @JsonManagedReference
    @OneToMany(mappedBy = "recruiter")
    private Set<JobVaga> vagasPostadas = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "recruiter")
    private Set<SavedCandidate> candidatosSalvos = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "recruiter")
    private Set<ProfileView> viewsEmCandidatos = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "recruiter")
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

    public boolean isAtive() {
        return isAtive;
    }

    public void setAtive(boolean ative) {
        isAtive = ative;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Set<JobVaga> getVagasPostadas() {
        return vagasPostadas;
    }

    public void setVagasPostadas(Set<JobVaga> vagasPostadas) {
        this.vagasPostadas = vagasPostadas;
    }

    public Set<SavedCandidate> getCandidatosSalvos() {
        return candidatosSalvos;
    }

    public void setCandidatosSalvos(Set<SavedCandidate> candidatosSalvos) {
        this.candidatosSalvos = candidatosSalvos;
    }

    public Set<ProfileView> getViewsEmCandidatos() {
        return viewsEmCandidatos;
    }

    public void setViewsEmCandidatos(Set<ProfileView> viewsEmCandidatos) {
        this.viewsEmCandidatos = viewsEmCandidatos;
    }

    public Set<Notification> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(Set<Notification> notificacoes) {
        this.notificacoes = notificacoes;
    }
}