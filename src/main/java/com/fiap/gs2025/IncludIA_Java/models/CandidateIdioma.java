package com.fiap.gs2025.IncludIA_Java.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fiap.gs2025.IncludIA_Java.enums.ProficiencyLevel;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "candidate_idiomas", uniqueConstraints = @UniqueConstraint(columnNames = {"candidate_id", "idioma_id"}))
public class CandidateIdioma {
    @Id
    private UUID id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idioma_id", nullable = false)
    private Idioma idioma;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProficiencyLevel nivelProficiencia;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public ProficiencyLevel getNivelProficiencia() {
        return nivelProficiencia;
    }

    public void setNivelProficiencia(ProficiencyLevel nivelProficiencia) {
        this.nivelProficiencia = nivelProficiencia;
    }
}