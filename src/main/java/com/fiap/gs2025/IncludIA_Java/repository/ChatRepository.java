package com.fiap.gs2025.IncludIA_Java.repository;

import com.fiap.gs2025.IncludIA_Java.models.Chat;
import com.fiap.gs2025.IncludIA_Java.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {

    Optional<Chat> findByMatch(Match match);

    @Query("SELECT c FROM Chat c " +
            "LEFT JOIN FETCH c.match m " +
            "LEFT JOIN FETCH m.candidate cand " +
            "LEFT JOIN FETCH m.vaga v " +
            "LEFT JOIN FETCH v.recruiter r " +
            "WHERE cand.id = :userId OR r.id = :userId")
    List<Chat> findChatsByUserId(@Param("userId") UUID userId);
}