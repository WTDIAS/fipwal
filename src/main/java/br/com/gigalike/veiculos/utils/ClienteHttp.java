package br.com.gigalike.veiculos.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
/** Faz a conexão com a API https://parallelum.com.br/fipe/api/v1/carros/marcas e retorna o JSON */
@Component
public class ClienteHttp {
    private static final Logger logger = LoggerFactory.getLogger(ClienteHttp.class);

    public String obterDadosApi(String endereco){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request,HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            logger.error("Erro ao tentar obter os dados da API!",e);
        }

        String json = response.body();
        return json;
    }
}
