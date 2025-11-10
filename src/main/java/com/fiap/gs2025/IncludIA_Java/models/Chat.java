package com.fiap.gs2025.IncludIA_Java.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "chats")
public class Chat {

    @Id
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false, unique = true)
    private Match match;

    @Column(nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatMessage> messages = new HashSet<>();


    private Chat(UUID id, Match match, Instant createdAt) {
        this.id = id;
        this.match = match;
        this.createdAt = createdAt;
    }

    // Método Factory: Um chat é criado quando um Match é confirmado
    public static Chat create(Match match) {
        return new Chat(UUID.randomUUID(), match, Instant.now());
    }
}