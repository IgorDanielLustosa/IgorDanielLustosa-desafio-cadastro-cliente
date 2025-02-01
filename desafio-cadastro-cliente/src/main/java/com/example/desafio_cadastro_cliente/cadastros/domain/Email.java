package com.example.desafio_cadastro_cliente.cadastros.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @javax.validation.constraints.Email(message = "E-mail inválido.")
    @NotBlank(message = "E-mail não pode ser vazio.")
    private String email;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    //  Construtor vazio (necessário para o JPA)
    public Email() {}

    //  Construtor que aceita apenas o email como String (necessário para Jackson)
    public Email(String email) {
        this.email = email;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
