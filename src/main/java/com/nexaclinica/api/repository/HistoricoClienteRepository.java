package com.nexaclinica.api.repository;

import com.nexaclinica.api.entity.HistoricoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoClienteRepository extends JpaRepository<HistoricoCliente, Long> {
    List<HistoricoCliente> findByClienteId(Long clienteId);
    List<HistoricoCliente> findByClienteIdOrderByDataDesc(Long clienteId);
}
