package com.nexaclinica.api.dto;

import java.math.BigDecimal;

public record ProdutoDTO(
    Long id,
    String nome,
    Integer quantidade,
    Integer estoqueMinimo,
    BigDecimal precoCusto
) {}
