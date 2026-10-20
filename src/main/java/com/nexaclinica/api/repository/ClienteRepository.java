package com.nexaclinica.api.repository;

import com.nexaclinica.api.entity.Cliente;
import com.nexaclinica.api.entity.enums.TipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByTipo(TipoCliente tipo);
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    List<Cliente> findByTelefoneContaining(String telefone);
    List<Cliente> findByEmailContainingIgnoreCase(String email);
}
