package br.com.gigalike.veiculos.utilitarios;
import br.com.gigalike.veiculos.model.TipoVeiculo;


public class FipeUrlBuilder {
    private final static String BASE = "https://parallelum.com.br/fipe/api/v1/";
    private String url;


    private FipeUrlBuilder(int codigoTipo) {
        String strTipo = TipoVeiculo.getStrTipo(codigoTipo);
        this.url = BASE + strTipo + "/marcas";
    }


    public static FipeUrlBuilder create(int codigoTipo) {
        return new FipeUrlBuilder(codigoTipo);
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
