package com.example.desafio_cadastro_cliente.cadastros.service;

import com.example.desafio_cadastro_cliente.cadastros.domain.*;
import com.example.desafio_cadastro_cliente.cadastros.repository.EnderecoRepository;
import com.example.desafio_cadastro_cliente.cep.CepClientRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EnderecoService {

    private final EnderecoRepository repository;
    private final CepClientRestTemplate cepClient2;

    public EnderecoService(EnderecoRepository repository, CepClientRestTemplate cepClient2) {
        this.repository = repository;
        this.cepClient2 = cepClient2;
    }

    // Metodo para listar endereço por ID
    public Endereco listarEnderecoPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Endereco não encontrado com o ID informado."));
    }

    // Metodo para listar todos os endereços
    public List<Endereco> listarTodos() {
        return repository.findAll();
    }

    // Metodo para salvar endereço com CEP sem máscara
    public Endereco salvar(String cep) {
        Endereco endereco = cepClient2.buscarCep(cep);

        // Remover hífen do CEP para armazenar sem máscara
        endereco.setCep(removerHifen(cep));

        return repository.save(endereco);
    }

    // Metodo para salvar endereço com serviço cepClient2
    public Endereco salvarComCepClient2(String cep) {
        Endereco endereco = cepClient2.buscarCep(cep);

        // Remover hífen do CEP para armazenar sem máscara
        endereco.setCep(removerHifen(cep));

        return repository.save(endereco);
    }

    // Metodo para vincular o cliente ao endereço
    public void vincularCliente(Endereco endereco, Cliente cliente) {
        endereco.setCliente(cliente);
        repository.save(endereco);
    }

    // Metodo para editar um endereço
    public Endereco editar(Long id, String cep) {
        Endereco endereco = this.listarEnderecoPorId(id);

        // Buscar dados do CEP
        Endereco buscarPorCep = cepClient2.buscarCep(cep);

        // Remover o hífen do CEP para armazenar sem máscara
        String cepSemHifen = removerHifen(buscarPorCep.getCep());
        endereco.setCep(cepSemHifen);
        endereco.setLogradouro(buscarPorCep.getLogradouro());
        endereco.setBairro(buscarPorCep.getBairro());
        endereco.setCidade(buscarPorCep.getLocalidade());
        endereco.setUf(buscarPorCep.getUf());

        // Salvar o endereço atualizado no banco
        return repository.save(endereco);
    }

    // Metodo para remover o hífen do CEP
    private String removerHifen(String cep) {
        return cep.replaceAll("[-]", "");
    }

    // Metodo para obter a quantidade de funcionários por CEP
    public List<FuncionariosPorCep> qtdFuncionariosPorCep() {
        return repository.totalFuncionariosPorCep();
    }

    // Metodo para obter a quantidade de clientes por CEP
    public List<ClientesPorCep> qtdClientesPorCep() {
        return repository.totalClientesPorCep();
    }

    // Metodo para aplicar a máscara ao CEP quando necessário exibir
    public String aplicarMascaraCep(String cep) {
        return cep.replaceAll("(\\d{5})(\\d{3})", "$1-$2"); // Máscara do CEP: XXXXX-XXX
    }

    public void vincularFuncionario(Endereco endereco, Funcionario funcionario) {
        endereco.setFuncionario(funcionario);  // Associa o funcionário ao endereço
        repository.save(endereco);             // Salva o endereço com o funcionário associado
    }

}
