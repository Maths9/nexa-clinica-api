package com.nexaclinica.api.repository;

import com.nexaclinica.api.entity.Agendamento;
import com.nexaclinica.api.entity.enums.StatusAgendamento;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    @Override
    @EntityGraph(attributePaths = {"cliente", "procedimento"})
    List<Agendamento> findAll();

    @Override
    @EntityGraph(attributePaths = {"cliente", "procedimento"})
    List<Agendamento> findAll(Sort sort);

    @Override
    @EntityGraph(attributePaths = {"cliente", "procedimento"})
    Optional<Agendamento> findById(Long id);

    @EntityGraph(attributePaths = {"cliente", "procedimento"})
    List<Agendamento> findByData(LocalDate data);

    @EntityGraph(attributePaths = {"cliente", "procedimento"})
    List<Agendamento> findByDataBetween(LocalDate inicio, LocalDate fim);

    @EntityGraph(attributePaths = {"cliente", "procedimento"})
    List<Agendamento> findByClienteId(Long clienteId);

    @EntityGraph(attributePaths = {"cliente", "procedimento"})
    List<Agendamento> findByStatus(StatusAgendamento status);

    long countByDataAndStatus(LocalDate data, StatusAgendamento status);
}
