package com.fiap.gs2025.IncludIA_Java.dtos.response;

import com.fiap.gs2025.IncludIA_Java.models.SavedJob;

import java.time.Instant;
import java.util.UUID;

public record SavedJobResponse(
        UUID id,
        UUID candidateId,
        JobVagaResponse vaga,
        Instant savedAt
) {
    public SavedJobResponse(SavedJob savedJob) {
        this(
                savedJob.getId(),
                savedJob.getCandidate().getId(),
                new JobVagaResponse(savedJob.getVaga()),
                savedJob.getSavedAt()
        );
    }
}