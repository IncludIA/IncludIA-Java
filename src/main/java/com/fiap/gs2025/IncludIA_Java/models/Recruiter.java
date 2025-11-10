package com.fiap.gs2025.IncludIA_Java.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @OneToMany(mappedBy = "recruiter")
    private Set<JobVaga> vagasPostadas = new HashSet<>();

    @OneToMany(mappedBy = "recruiter")
    private Set<SavedCandidate> candidatosSalvos = new HashSet<>();

    @OneToMany(mappedBy = "recruiter")
    private Set<ProfileView> viewsEmCandidatos = new HashSet<>();

    @OneToMany(mappedBy = "recruiter")
    private Set<Notification> notificacoes = new HashSet<>();

}