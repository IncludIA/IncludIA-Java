package com.fiap.gs2025.IncludIA_Java.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    // Lógica do Match
    private boolean CandidateLiked;
    private boolean RecruiterLiked;

    // Feature Tinder: Super Like
    private boolean CandidateSuperLike;
    private boolean RecruiterSuperLike;

    private LocalDateTime MatchedAt;
}
