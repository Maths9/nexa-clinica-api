package com.nexaclinica.api.repository;

import com.nexaclinica.api.entity.Despesa;
import com.nexaclinica.api.entity.enums.CategoriaDespesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByDataBetween(LocalDate inicio, LocalDate fim);
    List<Despesa> findByCategoria(CategoriaDespesa categoria);
}
