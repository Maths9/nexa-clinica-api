package com.nexaclinica.api.controller;

import com.nexaclinica.api.entity.Despesa;
import com.nexaclinica.api.entity.Receita;
import com.nexaclinica.api.service.FinanceiroService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/financeiro")
@CrossOrigin(origins = "*")
public class FinanceiroController {

    private final FinanceiroService financeiroService;

    public FinanceiroController(FinanceiroService financeiroService) {
        this.financeiroService = financeiroService;
    }

    @GetMapping("/receitas")
    public ResponseEntity<List<Receita>> listarReceitas() {
        return ResponseEntity.ok(financeiroService.listarReceitas());
    }

    @GetMapping("/despesas")
    public ResponseEntity<List<Despesa>> listarDespesas() {
        return ResponseEntity.ok(financeiroService.listarDespesas());
    }

    @GetMapping("/receitas/periodo")
    public ResponseEntity<List<Receita>> buscarReceitasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(financeiroService.buscarReceitasPorPeriodo(inicio, fim));
    }

    @GetMapping("/despesas/periodo")
    public ResponseEntity<List<Despesa>> buscarDespesasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(financeiroService.buscarDespesasPorPeriodo(inicio, fim));
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<Receita>> buscarPendentes() {
        return ResponseEntity.ok(financeiroService.buscarPendentes());
    }

    @PostMapping("/receitas")
    public ResponseEntity<Receita> salvarReceita(@RequestBody Receita receita) {
        return ResponseEntity.status(HttpStatus.CREATED).body(financeiroService.salvarReceita(receita));
    }

    @PostMapping("/despesas")
    public ResponseEntity<Despesa> salvarDespesa(@RequestBody Despesa despesa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(financeiroService.salvarDespesa(despesa));
    }

    @PatchMapping("/receitas/{id}/pagar")
    public ResponseEntity<Receita> marcarComoPago(@PathVariable Long id) {
        return ResponseEntity.ok(financeiroService.marcarComoPago(id));
    }

    @DeleteMapping("/receitas/{id}")
    public ResponseEntity<Void> deletarReceita(@PathVariable Long id) {
        financeiroService.deletarReceita(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/despesas/{id}")
    public ResponseEntity<Void> deletarDespesa(@PathVariable Long id) {
        financeiroService.deletarDespesa(id);
        return ResponseEntity.noContent().build();
    }
}
