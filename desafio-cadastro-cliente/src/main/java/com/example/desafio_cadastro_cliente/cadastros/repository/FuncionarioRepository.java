package com.example.desafio_cadastro_cliente.cadastros.repository;

import com.example.desafio_cadastro_cliente.cadastros.domain.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
