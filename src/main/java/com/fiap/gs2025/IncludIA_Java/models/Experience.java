package com.fiap.gs2025.IncludIA_Java.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Table(name = "experiences")
public class Experience {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String tituloCargo;

    @Column(nullable = false)
    private String tipoEmprego;

    @Column(nullable = false)
    private LocalDate dataInicio;

    private LocalDate dataFim;

    @Column(length = 2000)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTituloCargo() {
        return tituloCargo;
    }

    public void setTituloCargo(String tituloCargo) {
        this.tituloCargo = tituloCargo;
    }

    public String getTipoEmprego() {
        return tipoEmprego;
    }

    public void setTipoEmprego(String tipoEmprego) {
        this.tipoEmprego = tipoEmprego;
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}