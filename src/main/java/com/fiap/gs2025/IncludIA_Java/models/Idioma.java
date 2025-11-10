package com.fiap.gs2025.IncludIA_Java.models;

import com.fiap.gs2025.IncludIA_Java.enums.NomeIdioma;
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
    private NomeIdioma nome;

    @OneToMany(mappedBy = "idioma")
    private Set<CandidateIdioma> candidateIdiomas = new HashSet<>();

}