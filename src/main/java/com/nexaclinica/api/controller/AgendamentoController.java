package com.nexaclinica.api.controller;

import com.nexaclinica.api.entity.Agendamento;
import com.nexaclinica.api.entity.enums.StatusAgendamento;
import com.nexaclinica.api.service.AgendamentoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/agendamentos")
@CrossOrigin(origins = "*")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @GetMapping
    public ResponseEntity<List<Agendamento>> listarTodos() {
        return ResponseEntity.ok(agendamentoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.buscarPorId(id));
    }

    @GetMapping("/data/{data}")
    public ResponseEntity<List<Agendamento>> buscarPorData(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return ResponseEntity.ok(agendamentoService.buscarPorData(data));
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Agendamento>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(agendamentoService.buscarPorPeriodo(inicio, fim));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Agendamento>> buscarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(agendamentoService.buscarPorCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<Agendamento> salvar(@RequestBody Agendamento agendamento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoService.salvar(agendamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agendamento> atualizar(@PathVariable Long id, @RequestBody Agendamento agendamento) {
        return ResponseEntity.ok(agendamentoService.atualizar(id, agendamento));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Agendamento> atualizarStatus(
            @PathVariable Long id, @RequestParam StatusAgendamento status) {
        return ResponseEntity.ok(agendamentoService.atualizarStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
