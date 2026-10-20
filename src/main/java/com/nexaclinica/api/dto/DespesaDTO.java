package com.nexaclinica.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DespesaDTO(
    Long id,
    LocalDate data,
    String categoria,
    String descricao,
    BigDecimal valor,
    String formaPagamento
) {}
