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
@Table(name = "idiomas")
public class Idioma {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String nome;

    @OneToMany(mappedBy = "idioma")
    private Set<CandidateIdioma> candidateIdiomas = new HashSet<>();


    private Idioma(UUID id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public static Idioma create(String nome) {
        return new Idioma(UUID.randomUUID(), nome);
    }
}