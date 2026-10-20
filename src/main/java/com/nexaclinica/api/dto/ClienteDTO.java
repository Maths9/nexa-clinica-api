package com.nexaclinica.api.dto;

public record ClienteDTO(
    Long id,
    String nome,
    String telefone,
    String email,
    String tipo,
    String observacoes
) {}
