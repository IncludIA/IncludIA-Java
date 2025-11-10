package com.fiap.gs2025.IncludIA_Java.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor; // (Para o JPA)
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senhaHash;

    @Column(length = 2000) // Definindo um tamanho maior para resumos
    private String resumoPerfil;

    @Column(length = 2000)
    private String resumoInclusivoIA;

    private String fotoPerfilUrl;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "candidate_skills")
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Experience> experiencias = new HashSet<>();

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Education> formacoes = new HashSet<>();

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Voluntariado> voluntariados = new HashSet<>();

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CandidateIdioma> idiomas = new HashSet<>();

    @OneToMany(mappedBy = "candidate")
    private Set<Match> matches = new HashSet<>();

    @OneToMany(mappedBy = "candidate")
    private Set<SavedJob> vagasSalvas = new HashSet<>();

    @OneToMany(mappedBy = "candidate")
    private Set<ProfileView> viewsNoPerfil = new HashSet<>();

    @OneToMany(mappedBy = "candidate")
    private Set<Notification> notificacoes = new HashSet<>();

}