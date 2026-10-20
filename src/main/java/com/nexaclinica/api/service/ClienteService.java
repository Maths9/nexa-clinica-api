package com.nexaclinica.api.service;

import com.nexaclinica.api.entity.Cliente;
import com.nexaclinica.api.entity.enums.TipoCliente;
import com.nexaclinica.api.repository.AgendamentoRepository;
import com.nexaclinica.api.repository.ClienteRepository;
import com.nexaclinica.api.repository.HistoricoClienteRepository;
import com.nexaclinica.api.repository.ReceitaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final HistoricoClienteRepository historicoRepository;
    private final ReceitaRepository receitaRepository;

    public ClienteService(
            ClienteRepository clienteRepository,
            AgendamentoRepository agendamentoRepository,
            HistoricoClienteRepository historicoRepository,
            ReceitaRepository receitaRepository) {
        this.clienteRepository = clienteRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.historicoRepository = historicoRepository;
        this.receitaRepository = receitaRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    public List<Cliente> buscarPorTipo(TipoCliente tipo) {
        return clienteRepository.findByTipo(tipo);
    }

    public List<Cliente> pesquisar(String termo) {
        return Stream.concat(
                Stream.concat(
                        clienteRepository.findByNomeContainingIgnoreCase(termo).stream(),
                        clienteRepository.findByTelefoneContaining(termo).stream()
                ),
                clienteRepository.findByEmailContainingIgnoreCase(termo).stream()
        ).distinct().collect(Collectors.toList());
    }

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        Cliente existente = buscarPorId(id);
        existente.setNome(clienteAtualizado.getNome());
        existente.setTelefone(clienteAtualizado.getTelefone());
        existente.setEmail(clienteAtualizado.getEmail());
        existente.setTipo(clienteAtualizado.getTipo());
        existente.setObservacoes(clienteAtualizado.getObservacoes());
        return clienteRepository.save(existente);
    }

    public void deletar(Long id) {
        Cliente existente = buscarPorId(id);
        // Remove registros dependentes para evitar violação de FK no PostgreSQL
        historicoRepository.deleteAll(historicoRepository.findByClienteId(id));
        agendamentoRepository.deleteAll(agendamentoRepository.findByClienteId(id));
        receitaRepository.deleteAll(receitaRepository.findByClienteId(id));
        clienteRepository.delete(existente);
    }
}
