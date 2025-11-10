package com.fiap.gs2025.IncludIA_Java.repository;

import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, UUID> {
    Optional<Candidate> findByEmail(String email);
}