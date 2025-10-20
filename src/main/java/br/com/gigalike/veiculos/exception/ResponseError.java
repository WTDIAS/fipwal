package br.com.gigalike.veiculos.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ResponseError(HttpStatus httpStatus, String message, LocalDateTime now) {
}
