package com.fiap.gs2025.IncludIA_Java.dto.auth;

public record SocialLoginRequest(String token, String provider, String email, String nome) {}