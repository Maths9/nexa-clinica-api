package com.nexaclinica.api.dto;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

public record DashboardDTO(
        long totalClientes,
        long clientesAtivos,
        long novosClientesMes,
        long agendamentosHoje,
        long agendamentosConfirmadosHoje,
        BigDecimal faturamentoMes,
        BigDecimal despesasMes,
        BigDecimal lucroMes,
        long produtosCriticos,
        List<AgendamentoItemDTO> agendamentosHojeDetalhes,
        List<ProcedimentoItemDTO> procedimentosPopulares
) {
    public record AgendamentoItemDTO(
            Long id,
            LocalTime hora,
            String clienteNome,
            String procedimentoNome,
            String status,
            BigDecimal valor
    ) {}

    public record ProcedimentoItemDTO(
            String nome,
            long quantidade,
            int percentual,
            String cor
    ) {}
}
