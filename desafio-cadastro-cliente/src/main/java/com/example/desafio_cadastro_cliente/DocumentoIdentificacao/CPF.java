package com.example.desafio_cadastro_cliente.DocumentoIdentificacao;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public final class CPF implements DocumentoIdentificacao {

    private final String numero;

    private CPF(String numero) {
        this.numero = numero;
    }

    public static CPF from(String numero) {
        CPFValidator validator = new CPFValidator();
        try {
            validator.assertValid(numero);
        } catch (InvalidStateException e) {
            throw new IllegalArgumentException("CPF inválido: " + numero + ". Verifique se o CPF está correto.");
        }
        return new CPF(numero);
    }

    @Override
    public TipoDocumentoIdentificacao getTipo() {
        return TipoDocumentoIdentificacao.CPF;
    }

    @Override
    public String getNumero() {
        return this.numero;
    }

    @Override
    public boolean isPessoaFisica() {
        return true;
    }
}
