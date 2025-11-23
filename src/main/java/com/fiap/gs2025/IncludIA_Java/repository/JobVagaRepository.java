package com.fiap.gs2025.IncludIA_Java.repository;

import com.fiap.gs2025.IncludIA_Java.enums.ModeloTrabalho;
import com.fiap.gs2025.IncludIA_Java.enums.TipoContrato;
import com.fiap.gs2025.IncludIA_Java.models.Empresa;
import com.fiap.gs2025.IncludIA_Java.models.JobVaga;
import com.fiap.gs2025.IncludIA_Java.models.Recruiter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface JobVagaRepository extends JpaRepository<JobVaga, UUID> {
    List<JobVaga> findByRecruiter(Recruiter recruiter);
    List<JobVaga> findByEmpresa(Empresa empresa);
    Page<JobVaga> findByRecruiter(Recruiter recruiter, Pageable pageable);
    Page<JobVaga> findByIsAtivaTrue(Pageable pageable);

    // Usando @Query para facilitar filtros dinâmicos simples
    @Query("SELECT v FROM JobVaga v WHERE v.isAtiva = true " +
            "AND (:modelo IS NULL OR v.modeloTrabalho = :modelo) " +
            "AND (:tipo IS NULL OR v.tipoVaga = :tipo) " +
            "AND (:salarioMin IS NULL OR v.salarioMax >= :salarioMin)")
    Page<JobVaga> findByFilters(
            @Param("modelo") ModeloTrabalho modelo,
            @Param("tipo") TipoContrato tipo,
            @Param("salarioMin") BigDecimal salarioMin,
            Pageable pageable);
}