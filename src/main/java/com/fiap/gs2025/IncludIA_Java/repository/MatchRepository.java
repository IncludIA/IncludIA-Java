package com.fiap.gs2025.IncludIA_Java.repository;

import com.fiap.gs2025.IncludIA_Java.enums.MatchStatus;
import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.JobVaga;
import com.fiap.gs2025.IncludIA_Java.models.Match;
import com.fiap.gs2025.IncludIA_Java.models.Recruiter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MatchRepository extends JpaRepository<Match, UUID> {
    Optional<Match> findByCandidateAndVaga(Candidate candidate, JobVaga vaga);
    Page<Match> findByCandidateAndStatus(Candidate candidate, MatchStatus status, Pageable pageable);
    Page<Match> findByVaga_RecruiterAndStatus(Recruiter recruiter, MatchStatus status, Pageable pageable);
}