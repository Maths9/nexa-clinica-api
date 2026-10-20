package com.nexaclinica.api.service;

import com.nexaclinica.api.dto.HistoricoClienteDTO;
import com.nexaclinica.api.dto.HistoricoClienteRequest;
import com.nexaclinica.api.entity.Cliente;
import com.nexaclinica.api.entity.HistoricoCliente;
import com.nexaclinica.api.entity.Procedimento;
import com.nexaclinica.api.repository.ClienteRepository;
import com.nexaclinica.api.repository.HistoricoClienteRepository;
import com.nexaclinica.api.repository.ProcedimentoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HistoricoClienteService {

    private final HistoricoClienteRepository historicoRepository;
    private final ClienteRepository clienteRepository;
    private final ProcedimentoRepository procedimentoRepository;

    public HistoricoClienteService(HistoricoClienteRepository historicoRepository,
                                   ClienteRepository clienteRepository,
                                   ProcedimentoRepository procedimentoRepository) {
        this.historicoRepository = historicoRepository;
        this.clienteRepository = clienteRepository;
        this.procedimentoRepository = procedimentoRepository;
    }

    // ---- Conversão entity -> DTO ----

    private HistoricoClienteDTO toDTO(HistoricoCliente h) {
        return new HistoricoClienteDTO(
                h.getId(),
                h.getData(),
                h.getCliente().getId(),
                h.getCliente().getNome(),
                h.getProcedimento().getId(),
                h.getProcedimento().getNome(),
                h.getValor(),
                h.getObservacoes()
        );
    }

    // ---- Queries ----

    public List<HistoricoClienteDTO> listarPorCliente(Long clienteId) {
        return historicoRepository
                .findByClienteIdOrderByDataDesc(clienteId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<HistoricoClienteDTO> listarTodos() {
        return historicoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public HistoricoClienteDTO buscarPorId(Long id) {
        return historicoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado"));
    }

    // ---- Mutações ----

    public HistoricoClienteDTO registrar(HistoricoClienteRequest request) {
        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        Procedimento procedimento = procedimentoRepository.findById(request.procedimentoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Procedimento não encontrado"));

        HistoricoCliente historico = HistoricoCliente.builder()
                .data(request.data())
                .valor(request.valor())
                .observacoes(request.observacoes())
                .cliente(cliente)
                .procedimento(procedimento)
                .build();

        // Atualiza a última visita do cliente
        cliente.setUltimaVisita(request.data().atStartOfDay());
        clienteRepository.save(cliente);

        return toDTO(historicoRepository.save(historico));
    }

    public HistoricoClienteDTO atualizar(Long id, HistoricoClienteRequest request) {
        HistoricoCliente existente = historicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado"));

        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        Procedimento procedimento = procedimentoRepository.findById(request.procedimentoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Procedimento não encontrado"));

        existente.setData(request.data());
        existente.setValor(request.valor());
        existente.setObservacoes(request.observacoes());
        existente.setCliente(cliente);
        existente.setProcedimento(procedimento);

        return toDTO(historicoRepository.save(existente));
    }

    public void deletar(Long id) {
        if (!historicoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado");
        }
        historicoRepository.deleteById(id);
    }
}
