package com.nexaclinica.api.service;

import com.nexaclinica.api.entity.Agendamento;
import com.nexaclinica.api.entity.enums.StatusAgendamento;
import com.nexaclinica.api.repository.AgendamentoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }

    public Agendamento buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado"));
    }

    public List<Agendamento> buscarPorData(LocalDate data) {
        return agendamentoRepository.findByData(data);
    }

    public List<Agendamento> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return agendamentoRepository.findByDataBetween(inicio, fim);
    }

    public List<Agendamento> buscarPorCliente(Long clienteId) {
        return agendamentoRepository.findByClienteId(clienteId);
    }

    public Agendamento salvar(Agendamento agendamento) {
        // Impede agendamentos novos no passado
        if (agendamento.getId() == null && agendamento.getData() != null && agendamento.getHora() != null) {
            LocalDate hoje = LocalDate.now();
            if (agendamento.getData().isBefore(hoje)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Não é possível criar agendamento em datas que já passaram.");
            }
            if (agendamento.getData().isEqual(hoje) && agendamento.getHora().isBefore(java.time.LocalTime.now())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Não é possível criar agendamento em horário que já passou hoje.");
            }
        }
        return agendamentoRepository.save(agendamento);
    }

    public Agendamento atualizar(Long id, Agendamento dados) {
        Agendamento existente = buscarPorId(id);
        existente.setData(dados.getData());
        existente.setHora(dados.getHora());
        existente.setDuracaoMin(dados.getDuracaoMin());
        existente.setValor(dados.getValor());
        if (dados.getStatus() != null) {
            existente.setStatus(dados.getStatus());
        }
        existente.setObservacoes(dados.getObservacoes());
        if (dados.getCliente() != null) {
            existente.setCliente(dados.getCliente());
        }
        if (dados.getProcedimento() != null) {
            existente.setProcedimento(dados.getProcedimento());
        }
        return agendamentoRepository.save(existente);
    }

    public Agendamento atualizarStatus(Long id, StatusAgendamento status) {
        Agendamento existente = buscarPorId(id);
        existente.setStatus(status);
        return agendamentoRepository.save(existente);
    }

    public void deletar(Long id) {
        Agendamento existente = buscarPorId(id);
        agendamentoRepository.delete(existente);
    }
}
