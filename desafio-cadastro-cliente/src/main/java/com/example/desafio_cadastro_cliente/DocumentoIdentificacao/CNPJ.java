package com.example.desafio_cadastro_cliente.DocumentoIdentificacao;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public final class CNPJ implements DocumentoIdentificacao {

    private final String numero;

    private CNPJ(String numero) {
        this.numero = numero;
    }

    public static CNPJ from(String numero) {
        CNPJValidator validator = new CNPJValidator();
        try {
            validator.assertValid(numero);
        } catch (InvalidStateException e) {
            throw new IllegalArgumentException("CNPJ inválido: " + numero);
        }
        return new CNPJ(numero);
    }

    @Override
    public TipoDocumentoIdentificacao getTipo() {
        return TipoDocumentoIdentificacao.CNPJ;
    }

    @Override
    public String getNumero() {
        return this.numero;
    }

    @Override
    public boolean isPessoaJuridica() {
        return true;
    }
}
