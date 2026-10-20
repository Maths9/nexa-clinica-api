package com.nexaclinica.api.service;

import com.nexaclinica.api.dto.DashboardDTO;
import com.nexaclinica.api.entity.Agendamento;
import com.nexaclinica.api.entity.enums.StatusAgendamento;
import com.nexaclinica.api.entity.enums.StatusFinanceiro;
import com.nexaclinica.api.entity.enums.TipoCliente;
import com.nexaclinica.api.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private final ClienteRepository clienteRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;
    private final ProdutoRepository produtoRepository;
    private final ProcedimentoRepository procedimentoRepository;

    public DashboardService(
            ClienteRepository clienteRepository,
            AgendamentoRepository agendamentoRepository,
            ReceitaRepository receitaRepository,
            DespesaRepository despesaRepository,
            ProdutoRepository produtoRepository,
            ProcedimentoRepository procedimentoRepository) {
        this.clienteRepository = clienteRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.receitaRepository = receitaRepository;
        this.despesaRepository = despesaRepository;
        this.produtoRepository = produtoRepository;
        this.procedimentoRepository = procedimentoRepository;
    }

    public DashboardDTO obterMetricas() {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioMes = hoje.withDayOfMonth(1);
        LocalDate fimMes = hoje.withDayOfMonth(hoje.lengthOfMonth());

        // Clientes
        long totalClientes = clienteRepository.count();
        long clientesAtivos = clienteRepository.findByTipo(TipoCliente.ATIVO).size();
        long novosClientesMes = clienteRepository.findAll().stream()
                .filter(c -> c.getCreatedAt() != null && !c.getCreatedAt().toLocalDate().isBefore(inicioMes))
                .count();

        // Agendamentos de Hoje
        List<Agendamento> agendamentosHoje = agendamentoRepository.findByData(hoje);
        long totalAgendamentosHoje = agendamentosHoje.size();
        long agendamentosConfirmadosHoje = agendamentosHoje.stream()
                .filter(a -> a.getStatus() == StatusAgendamento.CONFIRMADO)
                .count();

        List<DashboardDTO.AgendamentoItemDTO> agendamentosHojeDetalhes = agendamentosHoje.stream()
                .sorted(Comparator.comparing(Agendamento::getHora))
                .map(a -> new DashboardDTO.AgendamentoItemDTO(
                        a.getId(),
                        a.getHora(),
                        a.getCliente() != null ? a.getCliente().getNome() : "Cliente não informado",
                        a.getProcedimento() != null ? a.getProcedimento().getNome() : "Procedimento não informado",
                        a.getStatus() != null ? a.getStatus().name() : "AGUARDANDO",
                        a.getValor() != null ? a.getValor() : BigDecimal.ZERO
                ))
                .collect(Collectors.toList());

        // Financeiro do Mês
        BigDecimal faturamentoMes = receitaRepository.findByDataBetween(inicioMes, fimMes).stream()
                .filter(r -> r.getStatus() == StatusFinanceiro.PAGO)
                .map(r -> r.getValor() != null ? r.getValor() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal despesasMes = despesaRepository.findByDataBetween(inicioMes, fimMes).stream()
                .map(d -> d.getValor() != null ? d.getValor() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal lucroMes = faturamentoMes.subtract(despesasMes);

        // Estoque Crítico
        long produtosCriticos = produtoRepository.findAll().stream()
                .filter(p -> p.getEstoqueMinimo() != null && p.getQuantidade() != null && p.getQuantidade() <= p.getEstoqueMinimo())
                .count();

        // Procedimentos Populares (baseados em agendamentos)
        List<Agendamento> todosAgendamentos = agendamentoRepository.findAll();
        Map<String, Long> contagemPorProc = todosAgendamentos.stream()
                .filter(a -> a.getProcedimento() != null)
                .collect(Collectors.groupingBy(a -> a.getProcedimento().getNome(), Collectors.counting()));

        long totalProcs = contagemPorProc.values().stream().mapToLong(Long::longValue).sum();

        String[] cores = {"bg-[#c47a85]", "bg-[#b5606e]", "bg-[#d89ba4]", "bg-[#eec5cb]"};
        List<DashboardDTO.ProcedimentoItemDTO> procedimentosPopulares = new ArrayList<>();
        int corIdx = 0;

        List<Map.Entry<String, Long>> ordenados = contagemPorProc.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(4)
                .collect(Collectors.toList());

        for (Map.Entry<String, Long> entry : ordenados) {
            int pct = totalProcs > 0 ? (int) Math.round((entry.getValue() * 100.0) / totalProcs) : 0;
            procedimentosPopulares.add(new DashboardDTO.ProcedimentoItemDTO(
                    entry.getKey(),
                    entry.getValue(),
                    pct,
                    cores[corIdx % cores.length]
            ));
            corIdx++;
        }

        // Se ainda não houver agendamentos, lista os primeiros procedimentos cadastrados com 0
        if (procedimentosPopulares.isEmpty()) {
            procedimentoRepository.findByAtivoTrue().stream().limit(4).forEach(p -> {
                procedimentosPopulares.add(new DashboardDTO.ProcedimentoItemDTO(
                        p.getNome(),
                        0,
                        0,
                        cores[procedimentosPopulares.size() % cores.length]
                ));
            });
        }

        return new DashboardDTO(
                totalClientes,
                clientesAtivos,
                novosClientesMes,
                totalAgendamentosHoje,
                agendamentosConfirmadosHoje,
                faturamentoMes,
                despesasMes,
                lucroMes,
                produtosCriticos,
                agendamentosHojeDetalhes,
                procedimentosPopulares
        );
    }
}
