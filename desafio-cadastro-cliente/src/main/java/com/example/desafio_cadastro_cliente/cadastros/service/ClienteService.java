package com.example.desafio_cadastro_cliente.cadastros.service;

import com.example.desafio_cadastro_cliente.cadastros.dto.ClienteDto;
import com.example.desafio_cadastro_cliente.cadastros.domain.Cliente;
import com.example.desafio_cadastro_cliente.cadastros.domain.Endereco;
import com.example.desafio_cadastro_cliente.cadastros.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private final ClienteRepository clienteRepository;
    private final RestTemplate restTemplate;

    public ClienteService(ClienteRepository clienteRepository, RestTemplate restTemplate) {
        this.clienteRepository = clienteRepository;
        this.restTemplate = restTemplate;
    }

    // Metodo para salvar cliente
    public ClienteDto salvarCliente(ClienteDto clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setDocumento(clienteDTO.getDocumento());
        cliente.setTipoDocumento(clienteDTO.getTipoDocumento());

        // Buscar endereço pelo CEP
        Endereco endereco = buscarEnderecoViaCep(clienteDTO.getCep());  // Alteração: Chamada do método de busca de CEP
        cliente.setEndereco(endereco);

        // Mapeamento do e-mail principal com validação
        cliente.setEmail(clienteDTO.getEmail());

        // Mapeamento dos e-mails adicionais com validação
        cliente.setEmailsAdicionais(clienteDTO.getEmailsAdicionais());

        // Salva o cliente no banco de dados
        cliente = clienteRepository.save(cliente);

        // Retorna o DTO do cliente
        return ClienteDto.map(cliente);
    }

    // Metodo para buscar o endereço de um cliente
    private Endereco buscarEnderecoViaCep(String cep) {  // Alteração: Metodo de busca do endereço via CEP
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        Endereco endereco = restTemplate.getForObject(url, Endereco.class);

        // Verificação de erros ou ausência de dados.
        if (endereco == null || endereco.getCep() == null || endereco.getCep().isEmpty()) {
            throw new RuntimeException("CEP inválido ou não encontrado: " + cep);  // Alteração: Lançamento de exceção em caso de erro no CEP
        }

        // Se a cidade não for encontrada, podemos lançar uma exceção ou tratar de forma mais amigável
        if (endereco.getCidade() == null || endereco.getCidade().isBlank()) {
            endereco.setCidade("Cidade não encontrada");  // Alteração: Caso a cidade não seja encontrada, atribui "Cidade não encontrada"
        }

        return endereco;
    }

    // Metodo para buscar todos os clientes com filtro
    public List<ClienteDto> buscarTodos(String filtro) {
        List<Cliente> clientes;
        if (filtro != null && !filtro.isEmpty()) {
            clientes = clienteRepository.findByNomeContaining(filtro); // Metodo customizado de filtro pelo nome
        } else {
            clientes = clienteRepository.findAll();
        }
        return clientes.stream().map(ClienteDto::map).collect(Collectors.toList());
    }

    // Metodo para buscar um cliente por ID
    public ClienteDto buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (!cliente.isPresent()) {
            throw new RuntimeException("Cliente não encontrado com ID: " + id);
        }
        return ClienteDto.map(cliente.get());
    }

    // Metodo para editar cliente
    public void editar(Long id, ClienteDto clienteDTO) {
        Cliente cliente = buscarClientePorId(id);
        cliente.setNome(clienteDTO.getNome());
        cliente.setDocumento(clienteDTO.getDocumento());
        cliente.setTipoDocumento(clienteDTO.getTipoDocumento());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setEmailsAdicionais(clienteDTO.getEmailsAdicionais());
        cliente.setEndereco(buscarEnderecoViaCep(clienteDTO.getCep()));  // Alteração: Chamada do método de busca de CEP na edição
        clienteRepository.save(cliente);
    }

    // Metodo para excluir um cliente
    public void excluir(Long id) {
        clienteRepository.deleteById(id);
    }

    // Metodo para buscar cliente por ID (usado internamente)
    private Cliente buscarClientePorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (!cliente.isPresent()) {
            throw new RuntimeException("Cliente não encontrado com o ID informado.");
        }
        return cliente.get();
    }

    // Metodo para buscar os endereços de um cliente
    public Endereco buscarEnderecosPorCliente(Long id) {
        Cliente cliente = buscarClientePorId(id);  // Chama o metodo auxiliar para buscar o cliente
        return cliente.getEndereco();  // Retorna o endereço associado ao cliente
    }

}
