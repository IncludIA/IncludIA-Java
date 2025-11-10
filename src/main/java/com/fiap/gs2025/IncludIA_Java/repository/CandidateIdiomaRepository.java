package com.fiap.gs2025.IncludIA_Java.repository;

import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.CandidateIdioma;
import com.fiap.gs2025.IncludIA_Java.models.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CandidateIdiomaRepository extends JpaRepository<CandidateIdioma, UUID> {
    Optional<CandidateIdioma> findByCandidateAndIdioma(Candidate candidate, Idioma idioma);
}