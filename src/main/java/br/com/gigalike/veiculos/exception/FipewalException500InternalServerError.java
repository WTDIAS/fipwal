package br.com.gigalike.veiculos.exception;

public class FipewalException500InternalServerError extends RuntimeException{
    public FipewalException500InternalServerError(String mensagem){
        super(mensagem);
    }
}
