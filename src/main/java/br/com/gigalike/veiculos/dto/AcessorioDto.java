package br.com.gigalike.veiculos.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public record AcessorioDto(
        long id,
        String nome,
        String descricao,
        double preco) {
}
