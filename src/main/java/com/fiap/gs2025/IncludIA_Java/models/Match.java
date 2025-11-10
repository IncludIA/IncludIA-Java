package com.fiap.gs2025.IncludIA_Java.models;

import com.fiap.gs2025.IncludIA_Java.enums.MatchStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@Table(name = "matches", uniqueConstraints = @UniqueConstraint(columnNames = {"candidate_id", "job_vaga_id"}))
public class Match {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_vaga_id", nullable = false)
    private JobVaga vaga;

    @Column(precision = 5, scale = 2)
    private BigDecimal matchScore;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchStatus status;

    private boolean isLikedByCandidate;
    private boolean isLikedByRecruiter;

    private void updateStatus() {
        if (isLikedByCandidate && isLikedByRecruiter) {
            this.status = MatchStatus.MATCHED;
        } else if (!isLikedByCandidate) {
            this.status = MatchStatus.REJEITADO_CANDIDATO;
        } else if (!isLikedByRecruiter) {
            this.status = MatchStatus.REJEITADO_RECRUTADOR;
        }
    }

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

    public JobVaga getVaga() {
        return vaga;
    }

    public void setVaga(JobVaga vaga) {
        this.vaga = vaga;
    }

    public BigDecimal getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(BigDecimal matchScore) {
        this.matchScore = matchScore;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public boolean isLikedByCandidate() {
        return isLikedByCandidate;
    }

    public void setLikedByCandidate(boolean likedByCandidate) {
        isLikedByCandidate = likedByCandidate;
    }

    public boolean isLikedByRecruiter() {
        return isLikedByRecruiter;
    }

    public void setLikedByRecruiter(boolean likedByRecruiter) {
        isLikedByRecruiter = likedByRecruiter;
    }
}