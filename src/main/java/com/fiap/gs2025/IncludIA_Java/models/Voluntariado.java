package com.fiap.gs2025.IncludIA_Java.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "voluntariados")
public class Voluntariado {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String organizacao;

    @Column(nullable = false)
    private String funcao;

    @Column(length = 2000)
    private String descricao;

    @Column(nullable = false)
    private LocalDate dataInicio;

    private LocalDate dataFim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

}