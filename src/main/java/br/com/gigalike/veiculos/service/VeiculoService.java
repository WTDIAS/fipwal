package br.com.gigalike.veiculos.service;
import br.com.gigalike.veiculos.dto.VeiculoDto;
import br.com.gigalike.veiculos.exception.ExceptionBadRequest;
import br.com.gigalike.veiculos.exception.ExceptionInternalServerError;
import br.com.gigalike.veiculos.mapper.VeiculoMapper;
import br.com.gigalike.veiculos.model.*;
import br.com.gigalike.veiculos.repository.AcessorioRepository;
import br.com.gigalike.veiculos.repository.VeiculoRepository;
import br.com.gigalike.veiculos.utilitarios.ConverterJsonParaVeiculo;
import br.com.gigalike.veiculos.utilitarios.FipeUrlBuilder;
import br.com.gigalike.veiculos.utils.ClienteHttp;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@Transactional
public class VeiculoService {
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private AcessorioRepository acessorioRepository;
    @Autowired
    private ClienteHttp clienteHttp;
    @Autowired
    private VeiculoMapper veiculoMapper;


    public VeiculoDto buscaPorId(long id) {
        Veiculo veiculo = veiculoRepository.findById(id).orElseThrow(() -> new ExceptionInternalServerError("Veiculo não encontrado!"));
        return veiculoMapper.toDto(veiculo);
    }

    public List<VeiculoDto> buscarVeiculos() {
        List<Veiculo> veiculos = veiculoRepository.findTop10By();
        if (veiculos.isEmpty()){
            throw new ExceptionInternalServerError("Nenhum veiculo foi encontrado no banco de dados!");
        }
        return veiculoMapper.listToDto(veiculos);
    }

    public VeiculoDto incluirAcessorioAoVeiculo(Long idVeiculo, Long idAcessorio) {
        Veiculo veiculo = veiculoRepository.findById(idVeiculo).orElseThrow(() -> new ExceptionInternalServerError("Veiculo "+idVeiculo+" não encontrado."));
        Acessorio acessorio = acessorioRepository.findById(idAcessorio).orElseThrow(()->new ExceptionInternalServerError("Acessório "+idAcessorio+" não encontrado."));
        veiculo.adicionaAcessorio(acessorio);
        return veiculoMapper.toDto(veiculoRepository.save(veiculo));
    }

    public VeiculoDto salvarVeiculoNoBd(VeiculoDto veiculoDto) {
        Veiculo veiculo = veiculoMapper.toEntity(veiculoDto);
        return veiculoMapper.toDto(veiculoRepository.save(veiculo));
    }


    public VeiculoDto atualizarVeiculoNoBd(VeiculoDto veiculoDto) {
        Veiculo veiculo = veiculoMapper.toEntity(veiculoDto);
        return veiculoMapper.toDto(veiculoRepository.save(veiculo));
    }


    public void deletarVeiculo(Long id) {
        if (!veiculoRepository.existsById(id)){
            throw new ExceptionBadRequest("Veículo com ID " + id + " não encontrado para exclusão.");
        }
        veiculoRepository.deleteById(id);
    }

    //******************** API **********************************

    /**
     *
     * */
    public String buscaMarcasNaApi(String tipoVeiculo){
        String url = FipeUrlBuilder.create(tipoVeiculo).build();
        return clienteHttp.obterDadosApi(url);
    }

    //https://parallelum.com.br/fipe/api/v1/carros/marcas
    public String buscaModelosNaApi(String tipoVeiculo, int codigoMarca) {
        String url = FipeUrlBuilder.create(tipoVeiculo)
                .comMarca(codigoMarca)
                .build();
        return clienteHttp.obterDadosApi(url);
    }


    public String buscaAnosNaApi(String tipoVeiculo, int codigoMarca, int codigoModelo) {
        String url = FipeUrlBuilder.create(tipoVeiculo)
                .comMarca(codigoMarca)
                .comModelo(codigoModelo)
                .build();
        return clienteHttp.obterDadosApi(url);
    }


    public ResponseEntity buscaDadosVeiculoNaApi(String tipoVeiculo, int marca, int modelo, String anoStr) {
        String url = FipeUrlBuilder.create(tipoVeiculo)
                .comMarca(marca)
                .comModelo(modelo)
                .comAno(anoStr)
                .build();
        String json = clienteHttp.obterDadosApi(url);
        VeiculoDto veiculoDto = ConverterJsonParaVeiculo.converterJson(json);
        if (veiculoDto.codigoFipe() != null){
            Veiculo veiculoSalvo = veiculoRepository.save(veiculoMapper.toEntity(veiculoDto));
            return ResponseEntity.ok(veiculoSalvo);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CodigoFipe null. Veículo não encontrado.");
        }


    }

}
