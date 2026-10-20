package com.nexaclinica.api.repository;

import com.nexaclinica.api.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByQuantidadeLessThanEqual(int quantidade);
    List<Produto> findByNomeContainingIgnoreCase(String nome);
}
