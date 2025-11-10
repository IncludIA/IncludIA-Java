package com.fiap.gs2025.IncludIA_Java.repository;

import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.Recruiter;
import com.fiap.gs2025.IncludIA_Java.models.SavedCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SavedCandidateRepository extends JpaRepository<SavedCandidate, UUID> {
    Optional<SavedCandidate> findByRecruiterAndCandidate(Recruiter recruiter, Candidate candidate);
}