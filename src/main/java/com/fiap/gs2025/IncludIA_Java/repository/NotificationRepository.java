package com.fiap.gs2025.IncludIA_Java.repository;

import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.Notification;
import com.fiap.gs2025.IncludIA_Java.models.Recruiter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByCandidateAndIsReadFalse(Candidate candidate);
    List<Notification> findByRecruiterAndIsReadFalse(Recruiter recruiter);
    Page<Notification> findByCandidateAndIsReadFalseOrderByCreatedAtDesc(Candidate candidate, Pageable pageable);
    Page<Notification> findByRecruiterAndIsReadFalseOrderByCreatedAtDesc(Recruiter recruiter, Pageable pageable);
}