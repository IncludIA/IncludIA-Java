package com.fiap.gs2025.IncludIA_Java.dto.response;

import com.fiap.gs2025.IncludIA_Java.enums.MatchStatus;
import com.fiap.gs2025.IncludIA_Java.models.Match;

import java.math.BigDecimal;
import java.util.UUID;

public record MatchResponse(
        UUID id,
        UUID candidateId,
        UUID vagaId,
        BigDecimal matchScore,
        MatchStatus status,
        boolean isLikedByCandidate,
        boolean isLikedByRecruiter
) {
    public MatchResponse(Match match) {
        this(
                match.getId(),
                match.getCandidate().getId(),
                match.getVaga().getId(),
                match.getMatchScore(),
                match.getStatus(),
                match.isLikedByCandidate(),
                match.isLikedByRecruiter()
        );
    }
}