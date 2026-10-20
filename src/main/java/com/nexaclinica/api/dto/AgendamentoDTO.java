package com.nexaclinica.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record AgendamentoDTO(
    Long id,
    LocalDate data,
    LocalTime hora,
    Long clienteId,
    String clienteNome,
    Long procedimentoId,
    String procedimentoNome,
    Integer duracaoMin,
    BigDecimal valor,
    String status,
    String observacoes
) {}
