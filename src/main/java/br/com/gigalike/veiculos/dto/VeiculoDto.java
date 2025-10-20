package br.com.gigalike.veiculos.dto;
import br.com.gigalike.veiculos.utilitarios.PrecoDeserializer;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VeiculoDto(
        long id,
        @JsonAlias("TipoVeiculo") int tipoVeiculo,
        @JsonAlias("Marca") String marca,
        @JsonAlias("Modelo") String modelo,
        @JsonAlias("AnoModelo") int ano,

        @JsonAlias("Valor")
        @JsonDeserialize(using = PrecoDeserializer.class)
        Double preco,

        Double capacidadeCarga,
        Double capacidadePortaMalas,
        int cilindradas,
        String observacao,
        DocumentoDto documentoDto,
        ProprietarioDto proprietarioDto,
        Set<AcessorioDto> acessoriosDto){

}

