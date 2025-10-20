package br.com.gigalike.veiculos.utilitarios;
import br.com.gigalike.veiculos.dto.VeiculoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConverterJsonParaVeiculo {
    public static final Logger logger = LoggerFactory.getLogger(ConverterJsonParaVeiculo.class);

    public static VeiculoDto converterJson(String json){
        VeiculoDto veiculoDto;
        final ObjectMapper mapper = new ObjectMapper();
        try {
            veiculoDto = mapper.readValue(json,VeiculoDto.class);
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return veiculoDto;
    }
}
