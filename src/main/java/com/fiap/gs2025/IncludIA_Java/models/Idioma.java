package com.fiap.gs2025.IncludIA_Java.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fiap.gs2025.IncludIA_Java.enums.NomeIdioma;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "idiomas")
public class Idioma {
    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.ORDINAL)
    private NomeIdioma nome;

    @JsonManagedReference
    @OneToMany(mappedBy = "idioma")
    private Set<CandidateIdioma> candidateIdiomas = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public NomeIdioma getNome() {
        return nome;
    }

    public void setNome(NomeIdioma nome) {
        this.nome = nome;
    }

    public Set<CandidateIdioma> getCandidateIdiomas() {
        return candidateIdiomas;
    }

    public void setCandidateIdiomas(Set<CandidateIdioma> candidateIdiomas) {
        this.candidateIdiomas = candidateIdiomas;
    }
}