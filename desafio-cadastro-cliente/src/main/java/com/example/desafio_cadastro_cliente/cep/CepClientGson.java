package com.example.desafio_cadastro_cliente.cep;

import com.example.desafio_cadastro_cliente.exception.CepException;
import com.example.desafio_cadastro_cliente.cadastros.domain.Endereco;
import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class CepClientGson {

    private static final String cepUrl = "https://viacep.com.br/ws/";
    private static final Gson gson = new Gson();

    public static Endereco buscarCep(String cep) {
        validarCep(cep);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(cepUrl + cep + "/json");
            try (CloseableHttpResponse response = client.execute(request)) {
                String json = EntityUtils.toString(response.getEntity(), "UTF-8");
                return gson.fromJson(json, Endereco.class);
            }
        } catch (IOException e) {
            throw new CepException("Erro ao buscar CEP: " + e.getMessage());
        }
    }

    private static void validarCep(String cep) {
        if (cep == null || cep.trim().isEmpty()) {
            throw new CepException("O cep informado não pode ser nulo ou vazio: " + cep);
        }

        if (cep.length() != 8) {
            throw new CepException("CEP " + cep + " inválido! Informe o CEP com 8 dígitos, informando apenas números.");
        }
    }

    public static String removerHifen(String cep) {
        return cep.replaceAll("-", "");
    }
}
