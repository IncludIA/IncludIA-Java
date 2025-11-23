package com.fiap.gs2025.IncludIA_Java.models;

import com.fasterxml.jackson.annotation.JsonBackReference; // IMPORT
import com.fiap.gs2025.IncludIA_Java.enums.GrauEducacao;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "t_inc_formacao")
public class Education {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String nomeInstituicao;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private GrauEducacao grau;

    private String areaEstudo;

    @Column(nullable = false)
    private LocalDate dataInicio;

    private LocalDate dataFim;

    @Column(length = 1000)
    private String descricao;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomeInstituicao() {
        return nomeInstituicao;
    }

    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
    }

    public GrauEducacao getGrau() {
        return grau;
    }

    public void setGrau(GrauEducacao grau) {
        this.grau = grau;
    }

    public String getAreaEstudo() {
        return areaEstudo;
    }

    public void setAreaEstudo(String areaEstudo) {
        this.areaEstudo = areaEstudo;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}