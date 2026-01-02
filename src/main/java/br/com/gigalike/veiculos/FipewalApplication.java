
package br.com.gigalike.veiculos;
import br.com.gigalike.veiculos.utilitarios.DotEnvInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FipewalApplication {
    	public static void main(String[] args) {
            SpringApplication app = new SpringApplication(FipewalApplication.class);
            //Carregamento da classe responsável por carregar o arquivo .env
            app.addInitializers(new DotEnvInitializer());
		    app.run(args);
	}
}

