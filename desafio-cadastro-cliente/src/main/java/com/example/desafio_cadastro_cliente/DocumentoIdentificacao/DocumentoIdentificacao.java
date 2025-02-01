package com.example.desafio_cadastro_cliente.DocumentoIdentificacao;

public interface DocumentoIdentificacao {

    TipoDocumentoIdentificacao getTipo();

    String getNumero();

    default boolean isPessoaFisica() {
        return false;
    }

    default boolean isPessoaJuridica() {
        return false;
    }

    static DocumentoIdentificacao from(String documento) {
        if (documento == null || documento.trim().isEmpty()) { // Substituí isBlank() por trim().isEmpty()
            throw new IllegalArgumentException("Documento de identificação não pode ser nulo ou vazio.");
        }

        if (documento.length() == 11) {
            return CPF.from(documento); // Chama o método estático na classe CPF
        }

        if (documento.length() == 14) {
            return CNPJ.from(documento); // Chama o método estático na classe CNPJ
        }

        throw new IllegalArgumentException(
                "Documento inválido: " + documento + ". Informe um CPF (11 dígitos) ou CNPJ (14 dígitos) válido.");
    }
}
