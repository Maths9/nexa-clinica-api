package com.nexaclinica.api.controller;

import com.nexaclinica.api.entity.MovimentacaoEstoque;
import com.nexaclinica.api.entity.Produto;
import com.nexaclinica.api.entity.enums.TipoMovimentacao;
import com.nexaclinica.api.service.EstoqueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estoque")
@CrossOrigin(origins = "*")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    // ---- Produtos ----

    @GetMapping("/produtos")
    public ResponseEntity<List<Produto>> listarProdutos() {
        return ResponseEntity.ok(estoqueService.listarProdutos());
    }

    @GetMapping("/produtos/estoque-baixo")
    public ResponseEntity<List<Produto>> produtosEstoqueBaixo() {
        return ResponseEntity.ok(estoqueService.produtosEstoqueBaixo());
    }

    @GetMapping("/produtos/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(estoqueService.buscarProdutoPorId(id));
    }

    @PostMapping("/produtos")
    public ResponseEntity<Produto> salvarProduto(@RequestBody Produto produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estoqueService.salvarProduto(produto));
    }

    @PutMapping("/produtos/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        return ResponseEntity.ok(estoqueService.atualizarProduto(id, produto));
    }

    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        estoqueService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

    // ---- Movimentações ----

    @GetMapping("/movimentacoes")
    public ResponseEntity<List<MovimentacaoEstoque>> listarTodasMovimentacoes() {
        return ResponseEntity.ok(estoqueService.listarTodasMovimentacoes());
    }

    @GetMapping("/produtos/{produtoId}/movimentacoes")
    public ResponseEntity<List<MovimentacaoEstoque>> listarMovimentacoes(@PathVariable Long produtoId) {
        return ResponseEntity.ok(estoqueService.listarMovimentacoes(produtoId));
    }

    @PostMapping("/produtos/{produtoId}/movimentacao")
    public ResponseEntity<MovimentacaoEstoque> registrarMovimentacao(
            @PathVariable Long produtoId,
            @RequestParam TipoMovimentacao tipo,
            @RequestParam int quantidade,
            @RequestParam(required = false) String obs) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(estoqueService.registrarMovimentacao(produtoId, tipo, quantidade, obs));
    }
}
