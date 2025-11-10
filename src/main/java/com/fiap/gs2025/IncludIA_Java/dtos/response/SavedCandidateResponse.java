package com.fiap.gs2025.IncludIA_Java.dtos.response;

import com.fiap.gs2025.IncludIA_Java.models.SavedCandidate;

import java.time.Instant;
import java.util.UUID;

public record SavedCandidateResponse(
        UUID id,
        UUID recruiterId,
        CandidateProfileResponse candidate,
        Instant savedAt
) {
    public SavedCandidateResponse(SavedCandidate savedCandidate) {
        this(
                savedCandidate.getId(),
                savedCandidate.getRecruiter().getId(),
                new CandidateProfileResponse(savedCandidate.getCandidate()),
                savedCandidate.getSavedAt()
        );
    }
}