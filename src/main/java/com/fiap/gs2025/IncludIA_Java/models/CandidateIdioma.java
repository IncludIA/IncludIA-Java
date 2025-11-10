package com.fiap.gs2025.IncludIA_Java.models;

import com.fiap.gs2025.IncludIA_Java.enums.ProficiencyLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@NoArgsConstructor
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setNivelProficiencia(@NotNull(message = "Nível de proficiência é obrigatório") ProficiencyLevel nivelProficiencia) {
        this.nivelProficiencia = nivelProficiencia;
    }

    public @NotNull(message = "Nível de proficiência é obrigatório") ProficiencyLevel getNivelProficiencia() {
        return nivelProficiencia;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Candidate getCandidate() {
        return candidate;
    }
}