package com.example.desafio_cadastro_cliente.cadastros.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    // Método para validar o e-mail
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;  // E-mail vazio ou nulo não é válido
        }

        // Expressão regular simples para verificar se o e-mail contém o '@' e tem um formato básico
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();  // Retorna se o e-mail corresponde ao padrão
    }
}
