package com.fiap.gs2025.IncludIA_Java.repository;

import com.fiap.gs2025.IncludIA_Java.models.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, UUID> {
    Optional<Recruiter> findByEmail(String email);
}