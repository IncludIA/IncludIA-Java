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
    @Query("SELECT c FROM Chat c WHERE c.match.candidate.id = :userId OR c.match.vaga.recruiter.id = :userId")
    List<Chat> findChatsByUserId(@Param("userId") UUID userId);
}