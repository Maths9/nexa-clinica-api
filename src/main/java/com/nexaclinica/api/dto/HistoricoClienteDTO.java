package com.nexaclinica.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record HistoricoClienteDTO(
        Long id,
        LocalDate data,
        Long clienteId,
        String clienteNome,
        Long procedimentoId,
        String procedimentoNome,
        BigDecimal valor,
        String observacoes
) {}
