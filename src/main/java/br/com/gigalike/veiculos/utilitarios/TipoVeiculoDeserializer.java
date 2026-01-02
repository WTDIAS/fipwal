package br.com.gigalike.veiculos.utilitarios;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class TipoVeiculoDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException, JacksonException {
        String tipo = jsonParser.getText();
        return switch (tipo) {
            case "1" -> "carros";
            case "2" -> "motos";
            case "3" -> "caminhoes";
            default -> tipo;
        };

    }
}
