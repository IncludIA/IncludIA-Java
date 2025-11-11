package com.fiap.gs2025.IncludIA_Java.repository;

import com.fiap.gs2025.IncludIA_Java.models.Empresa;
import com.fiap.gs2025.IncludIA_Java.models.JobVaga;
import com.fiap.gs2025.IncludIA_Java.models.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface JobVagaRepository extends JpaRepository<JobVaga, UUID> {
    List<JobVaga> findByRecruiter(Recruiter recruiter);
    List<JobVaga> findByEmpresa(Empresa empresa);

    Page<JobVaga> findByRecruiter(Recruiter recruiter, Pageable pageable);
    Page<JobVaga> findByIsAtivaTrue(Pageable pageable);
}