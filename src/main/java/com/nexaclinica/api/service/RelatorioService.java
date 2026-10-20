package com.nexaclinica.api.service;

import com.nexaclinica.api.dto.RelatorioDTO;
import com.nexaclinica.api.entity.Cliente;
import com.nexaclinica.api.entity.HistoricoCliente;
import com.nexaclinica.api.entity.Procedimento;
import com.nexaclinica.api.repository.ClienteRepository;
import com.nexaclinica.api.repository.HistoricoClienteRepository;
import com.nexaclinica.api.repository.ProcedimentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RelatorioService {

    private final HistoricoClienteRepository historicoRepository;
    private final ClienteRepository clienteRepository;
    private final ProcedimentoRepository procedimentoRepository;

    public RelatorioService(
            HistoricoClienteRepository historicoRepository,
            ClienteRepository clienteRepository,
            ProcedimentoRepository procedimentoRepository) {
        this.historicoRepository = historicoRepository;
        this.clienteRepository = clienteRepository;
        this.procedimentoRepository = procedimentoRepository;
    }

    public RelatorioDTO gerarResumo() {
        List<HistoricoCliente> historicos = historicoRepository.findAll();
        List<Procedimento> procedimentos = procedimentoRepository.findAll();
        List<Cliente> clientes = clienteRepository.findAll();

        long totalAtendimentos = historicos.size();

        BigDecimal faturamentoTotal = historicos.stream()
                .map(h -> h.getValor() != null ? h.getValor() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Agrupamento de atendimentos por procedimento
        Map<Long, List<HistoricoCliente>> porProc = historicos.stream()
                .filter(h -> h.getProcedimento() != null)
                .collect(Collectors.groupingBy(h -> h.getProcedimento().getId()));

        List<RelatorioDTO.ProcedimentoEstatisticaDTO> estatisticas = new ArrayList<>();
        for (Procedimento p : procedimentos) {
            List<HistoricoCliente> atendimentosDoProc = porProc.getOrDefault(p.getId(), Collections.emptyList());
            long qtd = atendimentosDoProc.size();
            BigDecimal totalProc = atendimentosDoProc.stream()
                    .map(h -> h.getValor() != null ? h.getValor() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            estatisticas.add(new RelatorioDTO.ProcedimentoEstatisticaDTO(
                    p.getNome(),
                    qtd,
                    p.getDuracaoMin() != null ? p.getDuracaoMin() : 60,
                    totalProc
            ));
        }

        estatisticas.sort(Comparator.comparing(RelatorioDTO.ProcedimentoEstatisticaDTO::totalAtendimentos).reversed());

        // Evolução de novos clientes por mês (últimos 6 meses)
        Map<String, Long> clientesPorMes = clientes.stream()
                .filter(c -> c.getCreatedAt() != null)
                .collect(Collectors.groupingBy(
                        c -> c.getCreatedAt().getMonth().getDisplayName(TextStyle.SHORT, new Locale("pt", "BR")),
                        Collectors.counting()
                ));

        List<RelatorioDTO.MesEstatisticaDTO> novosClientesMes = clientesPorMes.entrySet().stream()
                .map(e -> new RelatorioDTO.MesEstatisticaDTO(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        return new RelatorioDTO(
                totalAtendimentos,
                faturamentoTotal,
                estatisticas,
                novosClientesMes
        );
    }
}
