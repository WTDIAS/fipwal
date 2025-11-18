package br.com.gigalike.veiculos.exception;

public class ExceptionInternalServerError extends RuntimeException{
    public ExceptionInternalServerError(String mensagem){
        super(mensagem);
    }
}
