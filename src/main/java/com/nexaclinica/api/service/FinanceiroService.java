package com.nexaclinica.api.service;

import com.nexaclinica.api.entity.Despesa;
import com.nexaclinica.api.entity.Receita;
import com.nexaclinica.api.entity.enums.StatusFinanceiro;
import com.nexaclinica.api.repository.DespesaRepository;
import com.nexaclinica.api.repository.ReceitaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class FinanceiroService {

    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;

    public FinanceiroService(ReceitaRepository receitaRepository, DespesaRepository despesaRepository) {
        this.receitaRepository = receitaRepository;
        this.despesaRepository = despesaRepository;
    }

    public List<Receita> listarReceitas() {
        return receitaRepository.findAll();
    }

    public List<Despesa> listarDespesas() {
        return despesaRepository.findAll();
    }

    public List<Receita> buscarReceitasPorPeriodo(LocalDate inicio, LocalDate fim) {
        return receitaRepository.findByDataBetween(inicio, fim);
    }

    public List<Despesa> buscarDespesasPorPeriodo(LocalDate inicio, LocalDate fim) {
        return despesaRepository.findByDataBetween(inicio, fim);
    }

    public List<Receita> buscarPendentes() {
        return receitaRepository.findByStatus(StatusFinanceiro.PENDENTE);
    }

    public Receita salvarReceita(Receita receita) {
        return receitaRepository.save(receita);
    }

    public Despesa salvarDespesa(Despesa despesa) {
        return despesaRepository.save(despesa);
    }

    public Receita marcarComoPago(Long receitaId) {
        Receita receita = receitaRepository.findById(receitaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));
        receita.setStatus(StatusFinanceiro.PAGO);
        return receitaRepository.save(receita);
    }

    public void deletarReceita(Long id) {
        receitaRepository.deleteById(id);
    }

    public void deletarDespesa(Long id) {
        despesaRepository.deleteById(id);
    }
}
