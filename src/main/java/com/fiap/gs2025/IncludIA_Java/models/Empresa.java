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

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private Set<Recruiter> recruiters = new HashSet<>();

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private Set<JobVaga> vagas = new HashSet<>();

}