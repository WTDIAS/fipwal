package br.com.gigalike.veiculos.dto;
import br.com.gigalike.veiculos.utilitarios.PrecoDeserializer;
import br.com.gigalike.veiculos.utils.TipoVeiculoDeserializer;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

/**
 * DTO utilizado para transportar os dados de um Veiculo entre a camada de apresentação (JSON) e a aplicação.
 * @author Waldir Tiago Dias
 * @version 1.0 12/2025
 * */

@JsonIgnoreProperties(ignoreUnknown = true)
public record VeiculoDto(
        long id,
        @JsonDeserialize(using = TipoVeiculoDeserializer.class)
        @JsonAlias("TipoVeiculo")
        String tipoVeiculo,
        @JsonAlias("Marca")
        String marca,
        @JsonAlias("Modelo")
        String modelo,
        @JsonAlias("AnoModelo")
        int ano,
        @NotNull(message = "O campo código fipe (CodigoFipe) é obrigatório.")
        @JsonAlias("CodigoFipe")
        String codigoFipe,
        @JsonAlias("Combustivel")
        String combustivel,
        Boolean ativo,

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

