package com.fiap.gs2025.IncludIA_Java.repository;

import com.fiap.gs2025.IncludIA_Java.enums.NomeIdioma;
import com.fiap.gs2025.IncludIA_Java.models.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IdiomaRepository extends JpaRepository<Idioma, UUID> {
    Optional<Idioma> findByNome(NomeIdioma nome);
}