package com.example.desafio_cadastro_cliente.cadastros.service;

import com.example.desafio_cadastro_cliente.cadastros.dto.FuncionarioDto;
import com.example.desafio_cadastro_cliente.cadastros.domain.Endereco;
import com.example.desafio_cadastro_cliente.cadastros.domain.Funcionario;
import com.example.desafio_cadastro_cliente.cadastros.repository.FuncionarioRepository;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository repository;
    private final EnderecoService enderecoService;

    public FuncionarioService(FuncionarioRepository repository, EnderecoService enderecoService) {
        this.repository = repository;
        this.enderecoService = enderecoService;
    }

    public List<Funcionario> listarTodos(Pageable paginacao) {
        Page<Funcionario> funcionarios = repository.findAll(paginacao);
        return funcionarios.getContent();
    }

    public Funcionario listarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Pessoa não encontrada com o ID informado."));
    }

    public Endereco listarEnderecosPorFuncionario(Long id) {
        Funcionario funcionario = this.listarPorId(id);
        return funcionario.getEndereco();
    }

    public List<Funcionario> buscarPor(Funcionario filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Funcionario> example = Example.of(filtro, matcher);

        return repository.findAll(example, Sort.by("id").ascending());
    }

    public Funcionario cadastrar(FuncionarioDto dto) {
        Endereco endereco = enderecoService.salvar(dto.getCep());
        Funcionario funcionario = new Funcionario(dto.getNome(), dto.getDocumento(), endereco);
        return repository.save(funcionario);
    }

    public void editar(Long id, FuncionarioDto dto) {
        Funcionario funcionario = this.listarPorId(id);
        Long enderecoId = funcionario.getEndereco().getId(); // Substituindo var para compatibilidade com Java 8
        Endereco endereco = enderecoService.editar(enderecoId, dto.getCep());

        funcionario.setNome(dto.getNome());
        funcionario.setDocumento(dto.getDocumento());
        funcionario.setEndereco(endereco);

        enderecoService.vincularFuncionario(endereco, funcionario);
        repository.save(funcionario);
    }

    public void remover(Long id) {
        repository.findById(id).ifPresent(funcionario -> {
            repository.delete(funcionario);
        });

        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cliente não encontrado."
            );
        }
    }

}
