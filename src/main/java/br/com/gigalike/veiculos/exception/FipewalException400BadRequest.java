package br.com.gigalike.veiculos.exception;

public class FipewalException400BadRequest extends RuntimeException {
    public FipewalException400BadRequest(String message) {
        super(message);
    }
}
