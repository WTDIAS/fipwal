package br.com.gigalike.veiculos.utilitarios;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.FileNotFoundException;
import java.io.IOException;

public class PrecoDeserializer extends JsonDeserializer<Double> {
    @Override
    public Double deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        // 1. OBTÉM O VALOR BRUTO (a String do JSON)
        String valor = jsonParser.getText();

        // 2. UTILIZA A LÓGICA DE LIMPEZA E CONVERSÃO
        if (valor == null || valor.trim().isEmpty()) {
            throw new FileNotFoundException("Preço do veículo nulo ou vazio, não foi possível fazer a conversão.");
        }


        // Remove R$, espaços e outros símbolos, mantendo apenas números, ponto e vírgula
        String valorLimpo = valor.replaceAll("[^0-9,.]", "");

        // Remove o ponto (separador de milhar)
        valorLimpo = valorLimpo.replace(".", "");

        // Substitui a vírgula (separador decimal) por ponto (padrão Double)
        valorLimpo = valorLimpo.replace(",", ".");

        return Double.valueOf(valorLimpo);

    }
}
