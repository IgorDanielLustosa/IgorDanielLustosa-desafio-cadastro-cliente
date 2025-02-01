package com.example.desafio_cadastro_cliente.cadastros.controller;

import com.example.desafio_cadastro_cliente.cadastros.dto.ClienteDto;
import com.example.desafio_cadastro_cliente.cadastros.domain.Endereco;
import com.example.desafio_cadastro_cliente.cadastros.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/clientes", produces = "application/json")
@Validated
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    // Endpoint para listar todos os clientes
    @Operation(description = "Lista todos os clientes cadastrados", tags = {"Clientes"})
    @GetMapping
    public ResponseEntity<List<ClienteDto>> buscar(@RequestParam(required = false) String filtro) {
        List<ClienteDto> clientes = service.buscarTodos(filtro);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    // Endpoint para obter detalhes de um cliente
    @Operation(description = "Obter detalhes do cadastro de um cliente", tags = {"Clientes"})
    @GetMapping(value = "/{id}")
    public ClienteDto buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // Endpoint para listar os endereços vinculados a um cliente
    @Operation(description = "Lista os endereço vinculados a um cliente", tags = {"Clientes"})
    @GetMapping(value = "/{id}/endereco")
    public Endereco buscarEnderecos(@PathVariable Long id) {
        return service.buscarEnderecosPorCliente(id);
    }

    // Endpoint para cadastrar um novo cliente
    @Operation(description = "Realiza o cadastro de um cliente", tags = {"Clientes"})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDto cadastrar(@RequestBody @Valid ClienteDto clienteDto) {
        return service.salvarCliente(clienteDto); // Corrigido para salvarCliente
    }

    // Endpoint para editar o cadastro de um cliente
    @Operation(description = "Edita o cadastro de um cliente", tags = {"Clientes"})
    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id,
                                       @Parameter(description = "Dados do cliente")
                                       @RequestBody @Valid ClienteDto clienteDto) {
        service.editar(id, clienteDto);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para excluir um cliente
    @Operation(description = "Remove o cadastro de um cliente", tags = {"Clientes"})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
