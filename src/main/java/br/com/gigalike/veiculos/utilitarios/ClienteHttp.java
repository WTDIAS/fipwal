package br.com.gigalike.veiculos.utilitarios;
import br.com.gigalike.veiculos.exception.ExceptionNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
/** Faz a conexão com a API (Link exemplo: https://parallelum.com.br/fipe/api/v1/carros/marcas) e retorna o JSON */
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

        if(response == null){
            logger.warn("Não foi possível obter dados da API de integração.");
            throw new ExceptionNotFound("Não foi possível obter dados da API de integração.");
        }else{
            logger.info("Dados obtidos da integração: "+ response.body());
            return response.body();
        }


    }
}
