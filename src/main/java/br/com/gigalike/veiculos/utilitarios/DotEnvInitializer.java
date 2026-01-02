package br.com.gigalike.veiculos.utilitarios;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Classe criada para carregar as variáveis de ambiente do .env
 * */
public class DotEnvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // Lógica: Carrega o .env e injeta no ambiente do Spring antes dos beans serem criados
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(),entry.getValue()));
    }
}
