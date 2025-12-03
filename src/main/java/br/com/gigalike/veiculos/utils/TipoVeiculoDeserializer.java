package br.com.gigalike.veiculos.utils;

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
        String numeroTipoVeiculo = jsonParser.getText();
        switch (numeroTipoVeiculo){
            case "1":return "carros";
            case "2":return "motos";
            case "3":return "caminhoes";
            default:  throw new IOException("O tipo de veículo deve ser: carros, motos ou caminhoes.");
        }

    }
}
