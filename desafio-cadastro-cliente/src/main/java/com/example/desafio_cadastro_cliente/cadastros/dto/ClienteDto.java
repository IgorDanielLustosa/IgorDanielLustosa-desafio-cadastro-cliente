package com.example.desafio_cadastro_cliente.cadastros.dto;

import com.example.desafio_cadastro_cliente.DocumentoIdentificacao.DocumentoIdentificacao;
import com.example.desafio_cadastro_cliente.cadastros.domain.Cliente;
import com.example.desafio_cadastro_cliente.cadastros.domain.Email;
import com.example.desafio_cadastro_cliente.cadastros.domain.Telefone;
import com.example.desafio_cadastro_cliente.DocumentoIdentificacao.TipoDocumentoIdentificacao;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class ClienteDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "Obrigatório informar o nome.")
    private String nome;

    @NotNull(message = "Obrigatório informar o documento de identificação do cliente.")
    private String documento;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private TipoDocumentoIdentificacao tipoDocumento;

    @Valid()
    private String cep;

    @NotNull(message = "O e-mail principal é obrigatório.")
    @javax.validation.constraints.Email(message = "O e-mail principal deve ser válido.")
    private String email;

    private List<Email> emailsAdicionais; // Alterado para 'List<Email>' para manter consistência com a classe 'Email'

    private List<Telefone> telefones;

    // Construtor público para facilitar o mapeamento do ClienteDTO
    public ClienteDto(Long id, String nome, String documento, TipoDocumentoIdentificacao tipoDocumento, String cep, String email, List<Email> emailsAdicionais, List<Telefone> telefones) {
        this.id = id;
        this.nome = nome;
        this.documento = documento;
        this.tipoDocumento = tipoDocumento;
        this.cep = cep;
        this.email = email;
        this.emailsAdicionais = emailsAdicionais;
        this.telefones = telefones;

        // Identificando automaticamente o tipo de documento
        if (this.documento != null && !this.documento.isEmpty()) {
            this.tipoDocumento = DocumentoIdentificacao.from(this.documento).getTipo();
        }
    }

    // Construtor protegido para uso interno
    protected ClienteDto() {}

    // Metodo estático para mapear de Cliente para ClienteDto
    public static ClienteDto map(Cliente cliente) {
        return new ClienteDto(
                cliente.getId(),
                cliente.getNome(),
                cliente.getDocumento(),
                cliente.getTipoDocumento(),
                cliente.getCep(),
                cliente.getEmail(),
                cliente.getEmailsAdicionais(),  // Alterado para 'getEmailsAdicionais()'
                cliente.getTelefones()
        );
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public TipoDocumentoIdentificacao getTipoDocumento() {
        return tipoDocumento;
    }

    public String getCep() {
        return cep;
    }

    public String getEmail() {
        return email;
    }

    public List<Email> getEmailsAdicionais() {
        return emailsAdicionais;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }
}
