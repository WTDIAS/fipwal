package br.com.gigalike.veiculos.service;
import br.com.gigalike.veiculos.dto.VeiculoDto;
import br.com.gigalike.veiculos.exception.FipewalException400BadRequest;
import br.com.gigalike.veiculos.exception.FipewalException500InternalServerError;
import br.com.gigalike.veiculos.mapper.VeiculoMapper;
import br.com.gigalike.veiculos.model.*;
import br.com.gigalike.veiculos.repository.AcessorioRepository;
import br.com.gigalike.veiculos.repository.VeiculoRepository;
import br.com.gigalike.veiculos.utilitarios.ConverterJsonParaVeiculo;
import br.com.gigalike.veiculos.utilitarios.FipeUrlBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
        Veiculo veiculo = veiculoRepository.findById(id).orElseThrow(() -> new FipewalException500InternalServerError("Veiculo não encontrado!"));
        return veiculoMapper.toDto(veiculo);
    }

    public List<VeiculoDto> buscarVeiculos() {
        List<Veiculo> veiculos = veiculoRepository.findTop10By();
        if (veiculos.isEmpty()){
            throw new FipewalException500InternalServerError("Nenhum veiculo foi encontrado no banco de dados!");
        }
        return veiculoMapper.listToDto(veiculos);
    }

    public VeiculoDto incluirAcessorioAoVeiculo(Long idVeiculo, Long idAcessorio) {
        Veiculo veiculo = veiculoRepository.findById(idVeiculo).orElseThrow(() -> new FipewalException500InternalServerError("Veiculo "+idVeiculo+" não encontrado."));
        Acessorio acessorio = acessorioRepository.findById(idAcessorio).orElseThrow(()->new FipewalException500InternalServerError("Acessório "+idAcessorio+" não encontrado."));
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
            throw new FipewalException400BadRequest("Veículo com ID " + id + " não encontrado para exclusão.");
        }
        veiculoRepository.deleteById(id);
    }

    //******************** API **********************************

    public String buscaMarcasNaApi(int codigoTipo){
        String url = FipeUrlBuilder.create(codigoTipo).build();
        return clienteHttp.obterDadosApi(url);
    }


    public String buscaModelosNaApi(int codigoTipo, int codigoMarca) {
        String url = FipeUrlBuilder.create(codigoTipo)
                .comMarca(codigoMarca)
                .build();
        return clienteHttp.obterDadosApi(url);
    }


    public String buscaAnosNaApi(int codigoTipo, int codigoMarca, int codigoModelo) {
        String url = FipeUrlBuilder.create(codigoTipo)
                .comMarca(codigoMarca)
                .comModelo(codigoModelo)
                .build();
        return clienteHttp.obterDadosApi(url);
    }


    public VeiculoDto buscaDadosVeiculoNaApi(int tipo, int marca, int modelo, String anoStr) {
        String url = FipeUrlBuilder.create(tipo)
                .comMarca(marca)
                .comModelo(modelo)
                .comAno(anoStr)
                .build();
        String json = clienteHttp.obterDadosApi(url);
        VeiculoDto veiculoDto = ConverterJsonParaVeiculo.converterJson(json);
        Veiculo veiculoSalvo = veiculoRepository.save(veiculoMapper.toEntity(veiculoDto));
        return veiculoMapper.toDto(veiculoSalvo);
    }

}
