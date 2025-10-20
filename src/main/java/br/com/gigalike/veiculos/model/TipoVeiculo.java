package br.com.gigalike.veiculos.model;

import br.com.gigalike.veiculos.exception.FipewalException;

public enum TipoVeiculo {
    indefinido(0),
    carros(1),
    motos(2),
    caminhoes(3);

    public final int valorTipo;

    TipoVeiculo(int valor){
        valorTipo = valor;
    }

   public int getIntTipo(){
        return this.valorTipo;
   }

   public static String getStrTipo(int intTipo){
        String tipoStr = TipoVeiculo.values()[0].name();
        for (TipoVeiculo tipoVeiculo : TipoVeiculo.values()){
            if (tipoVeiculo.getIntTipo() == intTipo){
                tipoStr = tipoVeiculo.name();
            }
        }
        return tipoStr;
   }
/*
    public static TipoVeiculo converteIntParaTipoVeiculo(int intTipo) {
        for (TipoVeiculo tipoVeiculo : TipoVeiculo.values()) {
            if (tipoVeiculo.valorTipo == intTipo) {
                return tipoVeiculo;
            }
        }
        // RECOMENDADO: Lançar exceção se o código for inválido
        throw new FipewalException("Código de tipo de veículo inválido: " + intTipo);
    }*/
}
