package com.nexaclinica.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Request body for creating or updating a HistoricoCliente entry.
 */
public record HistoricoClienteRequest(
        LocalDate data,
        Long clienteId,
        Long procedimentoId,
        BigDecimal valor,
        String observacoes
) {}
