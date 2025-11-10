package com.fiap.gs2025.IncludIA_Java.repository;

import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EducationRepository extends JpaRepository<Education, UUID> {
    List<Education> findByCandidate(Candidate candidate);
}