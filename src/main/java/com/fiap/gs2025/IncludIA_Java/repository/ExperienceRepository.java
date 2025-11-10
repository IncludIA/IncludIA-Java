package com.fiap.gs2025.IncludIA_Java.repository;

import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, UUID> {
    List<Experience> findByCandidate(Candidate candidate);
}