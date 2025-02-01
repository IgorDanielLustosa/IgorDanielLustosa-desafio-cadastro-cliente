package com.example.desafio_cadastro_cliente.cadastros.repository;

import com.example.desafio_cadastro_cliente.cadastros.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNomeContaining(String nome);
}
