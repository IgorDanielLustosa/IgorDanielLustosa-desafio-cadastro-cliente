package com.example.desafio_cadastro_cliente.cadastros.domain;

public class FuncionariosPorCep {

    private String cep;
    private Long total;

    public FuncionariosPorCep(String cep, Long total) {
        this.cep = cep;
        this.total = total;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
