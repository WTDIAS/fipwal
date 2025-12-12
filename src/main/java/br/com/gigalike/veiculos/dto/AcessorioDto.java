package br.com.gigalike.veiculos.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO utilizado para transportar os dados de um Acessorio entre a camada de apresentação (JSON) e a aplicação.
 * @author Waldir Tiago Dias
 * @version 1.0 12/2025
 * */

@JsonIgnoreProperties
public record AcessorioDto(
        long id,
        @NotBlank(message = "Informe um nome para acessório.")
        String nome,
        String descricao,
        @NotBlank(message = "Informe um nome para acessório.")
        double preco,
        boolean ativo) {
}
