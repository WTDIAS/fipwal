package br.com.gigalike.veiculos.utilitarios;

public class StringNormalizer {

    public static String toUpperCase(String strMinuscula){
        return strMinuscula != null ? strMinuscula.toUpperCase() : strMinuscula;
    }

    public static String toLowerCase(String strMaiuscula){
        return strMaiuscula != null ? strMaiuscula.toLowerCase() : strMaiuscula;
    }
}
