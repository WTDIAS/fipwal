package br.com.gigalike.veiculos.model;

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
}
