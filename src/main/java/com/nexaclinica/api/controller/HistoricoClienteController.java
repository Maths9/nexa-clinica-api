package com.nexaclinica.api.controller;

import com.nexaclinica.api.dto.HistoricoClienteDTO;
import com.nexaclinica.api.dto.HistoricoClienteRequest;
import com.nexaclinica.api.service.HistoricoClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/historicos")
@CrossOrigin(origins = "*")
public class HistoricoClienteController {

    private final HistoricoClienteService historicoService;

    public HistoricoClienteController(HistoricoClienteService historicoService) {
        this.historicoService = historicoService;
    }

    /**
     * GET /api/v1/historicos
     * Lista todos os históricos (útil para relatórios gerais).
     */
    @GetMapping
    public ResponseEntity<List<HistoricoClienteDTO>> listarTodos() {
        return ResponseEntity.ok(historicoService.listarTodos());
    }

    /**
     * GET /api/v1/historicos/{id}
     * Busca um registro específico pelo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HistoricoClienteDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(historicoService.buscarPorId(id));
    }

    /**
     * GET /api/v1/historicos/cliente/{clienteId}
     * Retorna o histórico completo de um cliente, ordenado por data decrescente.
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<HistoricoClienteDTO>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(historicoService.listarPorCliente(clienteId));
    }

    /**
     * POST /api/v1/historicos
     * Registra um novo atendimento no histórico e atualiza ultimaVisita do cliente.
     */
    @PostMapping
    public ResponseEntity<HistoricoClienteDTO> registrar(@RequestBody HistoricoClienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(historicoService.registrar(request));
    }

    /**
     * PUT /api/v1/historicos/{id}
     * Atualiza um registro existente de histórico.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HistoricoClienteDTO> atualizar(
            @PathVariable Long id,
            @RequestBody HistoricoClienteRequest request) {
        return ResponseEntity.ok(historicoService.atualizar(id, request));
    }

    /**
     * DELETE /api/v1/historicos/{id}
     * Remove um registro de histórico.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        historicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
