package com.example.desafio_cadastro_cliente.cadastros.domain;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[0-9\\s\\+\\.\\-()]*$", message = "O número de telefone pode conter apenas números, espaços, pontos, parênteses e hífens.")
    private String numero;

    private String tipo; // residencial, comercial ou celular

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    //  Construtor vazio (necessário para o JPA)
    public Telefone() {}

    //  Construtor que aceita uma String para Jackson (corrige erro de desserialização)
    public Telefone(String numero) {
        this.numero = numero;
        this.tipo = "celular"; // Define padrão se não especificado
    }

    public Telefone(Telefone telefone) {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
