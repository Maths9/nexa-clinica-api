package com.nexaclinica.api.service;

import com.nexaclinica.api.entity.MovimentacaoEstoque;
import com.nexaclinica.api.entity.Produto;
import com.nexaclinica.api.entity.enums.TipoMovimentacao;
import com.nexaclinica.api.repository.MovimentacaoEstoqueRepository;
import com.nexaclinica.api.repository.ProdutoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EstoqueService {

    private final ProdutoRepository produtoRepository;
    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    public EstoqueService(ProdutoRepository produtoRepository,
                          MovimentacaoEstoqueRepository movimentacaoEstoqueRepository) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Produto buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    public List<Produto> produtosEstoqueBaixo() {
        return produtoRepository.findAll().stream()
                .filter(p -> p.getQuantidade() <= p.getEstoqueMinimo())
                .collect(Collectors.toList());
    }

    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Long id, Produto dados) {
        Produto produto = buscarProdutoPorId(id);
        produto.setNome(dados.getNome());
        if (dados.getQuantidade() != null) {
            produto.setQuantidade(dados.getQuantidade());
        }
        produto.setEstoqueMinimo(dados.getEstoqueMinimo());
        produto.setPrecoCusto(dados.getPrecoCusto());
        return produtoRepository.save(produto);
    }

    public MovimentacaoEstoque registrarMovimentacao(Long produtoId, TipoMovimentacao tipo, int quantidade, String obs) {
        Produto produto = buscarProdutoPorId(produtoId);

        if (tipo == TipoMovimentacao.ENTRADA) {
            produto.setQuantidade(produto.getQuantidade() + quantidade);
        } else {
            int novaQtd = produto.getQuantidade() - quantidade;
            if (novaQtd < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Quantidade em estoque insuficiente para realizar a saída");
            }
            produto.setQuantidade(novaQtd);
        }
        produtoRepository.save(produto);

        MovimentacaoEstoque mov = MovimentacaoEstoque.builder()
                .tipo(tipo)
                .quantidade(quantidade)
                .observacoes(obs)
                .produto(produto)
                .build();
        return movimentacaoEstoqueRepository.save(mov);
    }

    public List<MovimentacaoEstoque> listarMovimentacoes(Long produtoId) {
        return movimentacaoEstoqueRepository.findByProdutoId(produtoId);
    }

    public List<MovimentacaoEstoque> listarTodasMovimentacoes() {
        return movimentacaoEstoqueRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public void deletarProduto(Long id) {
        // Remove movimentacoes first to avoid FK violation
        Produto produto = buscarProdutoPorId(id);
        movimentacaoEstoqueRepository.deleteAll(
                movimentacaoEstoqueRepository.findByProdutoId(id)
        );
        produtoRepository.delete(produto);
    }
}
