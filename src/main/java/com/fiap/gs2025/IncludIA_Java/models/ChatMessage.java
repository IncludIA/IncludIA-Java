package com.fiap.gs2025.IncludIA_Java.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @Column(length = 2000, nullable = false)
    private String conteudo;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(nullable = false)
    private UUID senderId;

    @Column(nullable = false)
    private UUID receiverId;

    private boolean isRead;



}