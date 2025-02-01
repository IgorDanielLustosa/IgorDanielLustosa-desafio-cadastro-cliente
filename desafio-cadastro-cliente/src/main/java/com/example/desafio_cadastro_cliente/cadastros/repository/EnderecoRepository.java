package com.example.desafio_cadastro_cliente.cadastros.repository;

import com.example.desafio_cadastro_cliente.cadastros.domain.ClientesPorCep;
import com.example.desafio_cadastro_cliente.cadastros.domain.Endereco;
import com.example.desafio_cadastro_cliente.cadastros.domain.FuncionariosPorCep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query("SELECT new com.example.desafio_cadastro_cliente.cadastros.domain.FuncionariosPorCep(c.cep, COUNT(c.cep)) "
            + "FROM Endereco AS c WHERE c.funcionario IS NOT NULL GROUP BY c.cep ORDER BY c.id DESC")
    List<FuncionariosPorCep> totalFuncionariosPorCep();

    @Query("SELECT new com.example.desafio_cadastro_cliente.cadastros.domain.ClientesPorCep(c.cep, COUNT(c.cep)) "
            + "FROM Endereco AS c WHERE c.cliente IS NOT NULL GROUP BY c.cep ORDER BY c.id DESC")
    List<ClientesPorCep> totalClientesPorCep();
}
