package com.example.desafio_cadastro_cliente.cadastros.domain;

import com.example.desafio_cadastro_cliente.DocumentoIdentificacao.TipoDocumentoIdentificacao;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    @Pattern(regexp = "^[a-zA-Z\\sÀ-ÿ]+$", message = "O nome deve conter apenas letras, espaços e caracteres especiais.")
    private String nome;

    @NotBlank
    @Pattern(regexp = "\\d{11}|\\d{14}", message = "O documento deve ser um CPF (11 dígitos) ou CNPJ (14 dígitos).")
    private String documento;

    @Enumerated(EnumType.STRING)
    private TipoDocumentoIdentificacao tipoDocumento;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true) // Aqui está o relacionamento com telefone
    private List<Telefone> telefones;  // Remover a anotação @NotEmpty

    @javax.validation.constraints.Email
    @NotBlank(message = "O e-mail principal é obrigatório.")
    private String email;  // E-mail principal

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Email> emailsAdicionais;  // Lista de e-mails adicionais

    // Construtor público (agora disponível para ser acessado)
    public Cliente(String nome, String documento, TipoDocumentoIdentificacao tipoDocumento, Endereco endereco, String email) {
        this.nome = nome;
        this.documento = documento;
        this.tipoDocumento = tipoDocumento;
        this.endereco = endereco;
        this.email = email;
    }

    // Construtor sem parâmetros (necessário para o Hibernate)
    public Cliente() {}

    // Métodos Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public TipoDocumentoIdentificacao getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumentoIdentificacao tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Email> getEmailsAdicionais() {
        return emailsAdicionais;
    }

    public void setEmailsAdicionais(List<Email> emailsAdicionais) {
        this.emailsAdicionais = emailsAdicionais;
    }

    // Método getCep() adicionado
    public String getCep() {
        return endereco != null ? endereco.getCep() : null; // Supondo que o CEP esteja na classe Endereco
    }
}
