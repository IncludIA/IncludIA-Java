package com.fiap.gs2025.IncludIA_Java.repository;

import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.JobVaga;
import com.fiap.gs2025.IncludIA_Java.models.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SavedJobRepository extends JpaRepository<SavedJob, UUID> {
    Optional<SavedJob> findByCandidateAndVaga(Candidate candidate, JobVaga vaga);
}