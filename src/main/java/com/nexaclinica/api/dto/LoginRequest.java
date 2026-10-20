package com.nexaclinica.api.dto;

public record LoginRequest(
        String email,
        String senha
) {}
