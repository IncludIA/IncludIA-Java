package com.fiap.gs2025.IncludIA_Java.models;

import com.fiap.gs2025.IncludIA_Java.enums.ProficiencyLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "candidate_idiomas",
        uniqueConstraints = @UniqueConstraint(columnNames = {"candidate_id", "idioma_id"})) //
public class CandidateIdioma {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idioma_id", nullable = false)
    private Idioma idioma;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProficiencyLevel nivelProficiencia;

}