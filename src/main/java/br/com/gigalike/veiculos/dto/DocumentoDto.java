package br.com.gigalike.veiculos.dto;

/**
 * DTO utilizado para transportar os dados de um Documento entre a camada de apresentação (JSON) e a aplicação.
 * @author Waldir Tiago Dias
 * @version 1.0 12/2025
 * */

public record DocumentoDto(long id, String renavam) {
}
