package com.nexaclinica.api.repository;

import com.nexaclinica.api.entity.Receita;
import com.nexaclinica.api.entity.enums.StatusFinanceiro;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    @Override
    @EntityGraph(attributePaths = {"cliente", "procedimento"})
    List<Receita> findAll();

    @Override
    @EntityGraph(attributePaths = {"cliente", "procedimento"})
    List<Receita> findAll(Sort sort);

    @Override
    @EntityGraph(attributePaths = {"cliente", "procedimento"})
    Optional<Receita> findById(Long id);

    @EntityGraph(attributePaths = {"cliente", "procedimento"})
    List<Receita> findByDataBetween(LocalDate inicio, LocalDate fim);

    @EntityGraph(attributePaths = {"cliente", "procedimento"})
    List<Receita> findByStatus(StatusFinanceiro status);

    @EntityGraph(attributePaths = {"cliente", "procedimento"})
    List<Receita> findByClienteId(Long clienteId);
}
