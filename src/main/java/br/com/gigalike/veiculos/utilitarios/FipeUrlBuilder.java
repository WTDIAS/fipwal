package br.com.gigalike.veiculos.utilitarios;


public class FipeUrlBuilder {
    /**
     * Link de exemplo para consulta no navegador:
     * https://parallelum.com.br/fipe/api/v1/carros/marcas/22/modelos/672/anos/1991-1
     * */
    private final static String BASE = "https://parallelum.com.br/fipe/api/v1/";
    private String url;


    public static FipeUrlBuilder create(String tipoVeiculo) {
        return new FipeUrlBuilder(tipoVeiculo);
    }


    private FipeUrlBuilder(String tipoVeiculo) {
        this.url = BASE + tipoVeiculo + "/marcas";
    }


    public FipeUrlBuilder comMarca(int codigoMarca) {
        this.url += "/" + codigoMarca + "/modelos";
        return this;
    }


    public FipeUrlBuilder comModelo(int codigoModelo) {
        this.url += "/" + codigoModelo + "/anos";
        return this;
    }


    public FipeUrlBuilder comAno(String anoStr) {
        this.url += "/" + anoStr;
        return this;
    }


    public String build() {
        return this.url;
    }
}
