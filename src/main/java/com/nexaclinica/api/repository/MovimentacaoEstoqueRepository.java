package com.nexaclinica.api.repository;

import com.nexaclinica.api.entity.MovimentacaoEstoque;
import com.nexaclinica.api.entity.enums.TipoMovimentacao;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Long> {

    @Override
    @EntityGraph(attributePaths = {"produto"})
    List<MovimentacaoEstoque> findAll();

    @Override
    @EntityGraph(attributePaths = {"produto"})
    List<MovimentacaoEstoque> findAll(Sort sort);

    @Override
    @EntityGraph(attributePaths = {"produto"})
    Optional<MovimentacaoEstoque> findById(Long id);

    @EntityGraph(attributePaths = {"produto"})
    List<MovimentacaoEstoque> findByProdutoId(Long produtoId);

    @EntityGraph(attributePaths = {"produto"})
    List<MovimentacaoEstoque> findByTipo(TipoMovimentacao tipo);
}
