package com.example.desafio_cadastro_cliente.cadastros.controller;

import com.example.desafio_cadastro_cliente.cadastros.dto.FuncionarioDto;
import com.example.desafio_cadastro_cliente.cadastros.domain.Endereco;
import com.example.desafio_cadastro_cliente.cadastros.domain.Funcionario;
import com.example.desafio_cadastro_cliente.cadastros.service.EnderecoService;
import com.example.desafio_cadastro_cliente.cadastros.service.FuncionarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/funcionarios")
public class FuncionarioController {

    private final FuncionarioService service;
    private final EnderecoService enderecoService;

    public FuncionarioController(FuncionarioService service, EnderecoService enderecoService) {
        this.service = service;
        this.enderecoService = enderecoService;
    }

    @ApiOperation(value = "Obter detalhes do cadastro de um funcionário", notes = "Busca os detalhes de um funcionário pelo ID.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Funcionário encontrado"),
            @ApiResponse(code = 404, message = "Funcionário não encontrado para o ID informado")
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    public Funcionario listarPorId(@PathVariable Long id) {
        return service.listarPorId(id);
    }

    @ApiOperation(value = "Obter funcionários por filtro", notes = "Busca funcionários pelo filtro informado.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Funcionário encontrado"),
            @ApiResponse(code = 404, message = "Funcionário não encontrado para o filtro informado")
    })
    @GetMapping(value = "/filtrar", produces = "application/json")
    public List<Funcionario> listarPorFiltro(Funcionario filtro) {
        return service.buscarPor(filtro);
    }

    @ApiOperation(value = "Listar todos os funcionários", notes = "Lista todos os funcionários cadastrados com paginação.")
    @GetMapping(produces = "application/json")
    public List<Funcionario> listarTodos(@RequestParam int pagina,
                                         @RequestParam int qtdItens,
                                         @RequestParam String ordenacao) {
        Pageable paginacao = PageRequest.of(pagina, qtdItens, Sort.Direction.ASC, ordenacao);
        return service.listarTodos(paginacao);
    }

    @ApiOperation(value = "Listar endereço de um funcionário", notes = "Lista o endereço vinculado a um funcionário pelo ID.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Endereço encontrado"),
            @ApiResponse(code = 404, message = "Endereço não encontrado para o ID do funcionário informado")
    })
    @GetMapping(value = "/{id}/endereco", produces = "application/json")
    public Endereco listarEnderecos(@PathVariable Long id) {
        return service.listarEnderecosPorFuncionario(id);
    }

    @ApiOperation(value = "Cadastrar um funcionário", notes = "Cadastra um novo funcionário.")
    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Funcionario cadastrar(@RequestBody @Valid FuncionarioDto funcionarioDto) {
        Funcionario funcionario = service.cadastrar(funcionarioDto);
        enderecoService.vincularFuncionario(funcionario.getEndereco(), funcionario);
        return funcionario;
    }

    @ApiOperation(value = "Editar um funcionário", notes = "Edita o cadastro de um funcionário existente.")
    @PutMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editar(@PathVariable Long id, @RequestBody @Valid FuncionarioDto funcionarioDto) {
        service.editar(id, funcionarioDto);
    }

    @ApiOperation(value = "Excluir um funcionário", notes = "Remove o cadastro de um funcionário pelo ID.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.remover(id);
    }
}
