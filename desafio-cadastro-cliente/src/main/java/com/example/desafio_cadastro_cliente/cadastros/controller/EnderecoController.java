package com.example.desafio_cadastro_cliente.cadastros.controller;

import com.example.desafio_cadastro_cliente.cadastros.domain.ClientesPorCep;
import com.example.desafio_cadastro_cliente.cadastros.domain.Endereco;
import com.example.desafio_cadastro_cliente.cadastros.domain.FuncionariosPorCep;
import com.example.desafio_cadastro_cliente.cadastros.service.EnderecoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/enderecos")
public class EnderecoController {

    private final EnderecoService service;

    public EnderecoController(EnderecoService service) {
        this.service = service;
    }

    @ApiOperation(value = "Obter detalhes de um endereço", notes = "Retorna os detalhes do endereço pelo ID.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Endereço encontrado"),
            @ApiResponse(code = 404, message = "Endereço não encontrado para o ID informado")
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    public Endereco listarPorId(@PathVariable Long id) {
        return service.listarEnderecoPorId(id);
    }

    @ApiOperation(value = "Listar todos os endereços cadastrados", notes = "Retorna a lista completa de endereços cadastrados.")
    @GetMapping(produces = "application/json")
    public List<Endereco> listarTodos() {
        return service.listarTodos();
    }

    @ApiOperation(value = "Listar quantidade de funcionários por CEP", notes = "Retorna a quantidade de funcionários agrupados por CEP.")
    @GetMapping(value = "/funcionarios", produces = "application/json")
    public List<FuncionariosPorCep> qtdFuncionariosPorCep() {
        return service.qtdFuncionariosPorCep();
    }

    @ApiOperation(value = "Listar quantidade de clientes por CEP", notes = "Retorna a quantidade de clientes agrupados por CEP.")
    @GetMapping(value = "/clientes", produces = "application/json")
    public List<ClientesPorCep> qtdClientesPorCep() {
        return service.qtdClientesPorCep();
    }
}
