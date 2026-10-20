package com.nexaclinica.api.dto;

import java.math.BigDecimal;
import java.util.List;

public record RelatorioDTO(
        long totalAtendimentos,
        BigDecimal faturamentoTotal,
        List<ProcedimentoEstatisticaDTO> estatisticasProcedimentos,
        List<MesEstatisticaDTO> novosClientesPorMes
) {
    public record ProcedimentoEstatisticaDTO(
            String nome,
            long totalAtendimentos,
            int tempoMedioMin,
            BigDecimal valorTotal
    ) {}

    public record MesEstatisticaDTO(
            String mes,
            long novosClientes
    ) {}
}
