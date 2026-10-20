package com.nexaclinica.api.repository;

import com.nexaclinica.api.entity.Procedimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {
    List<Procedimento> findByAtivoTrue();
    List<Procedimento> findByNomeContainingIgnoreCase(String nome);
}
