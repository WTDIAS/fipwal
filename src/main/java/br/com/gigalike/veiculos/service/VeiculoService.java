package br.com.gigalike.veiculos.service;
import br.com.gigalike.veiculos.dto.VeiculoDto;
import br.com.gigalike.veiculos.exception.ExceptionConflict;
import br.com.gigalike.veiculos.exception.ExceptionNotFound;
import br.com.gigalike.veiculos.mapper.VeiculoMapper;
import br.com.gigalike.veiculos.model.*;
import br.com.gigalike.veiculos.repository.AcessorioRepository;
import br.com.gigalike.veiculos.repository.DocumentoRepository;
import br.com.gigalike.veiculos.repository.ProprietarioRepository;
import br.com.gigalike.veiculos.repository.VeiculoRepository;
import br.com.gigalike.veiculos.utilitarios.ConverterJsonParaVeiculo;
import br.com.gigalike.veiculos.utilitarios.FipeUrlBuilder;
import br.com.gigalike.veiculos.utilitarios.ClienteHttp;
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
    @Autowired
    private ProprietarioRepository proprietarioRepository;
    @Autowired
    private DocumentoRepository documentoRepository;


    public VeiculoDto buscaPorId(long id) {
        Veiculo veiculo = veiculoRepository.findById(id).orElseThrow(()->new ExceptionNotFound("Veiculo não encontrado!"));
        return veiculoMapper.toDto(veiculo);
    }

    public List<VeiculoDto> buscarVeiculosAtivos() {
        List<Veiculo> veiculos = veiculoRepository.findTop100ByAtivoTrue();
        return veiculoMapper.listToDto(veiculos);
    }

    public List<VeiculoDto> buscarVeiculosInativos() {
        List<Veiculo> veiculos = veiculoRepository.findTop100ByAtivoFalse();
        return veiculoMapper.listToDto(veiculos);
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
        if (!veiculoRepository.existsById(id)) {
            throw new ExceptionNotFound("Veículo com ID " + id + " não encontrado para exclusão.");
        }
        veiculoRepository.deleteById(id);
    }

    public VeiculoDto incluirAcessorioAoVeiculo(Long idVeiculo, Long idAcessorio) {
        Veiculo veiculo = veiculoRepository.findById(idVeiculo).orElseThrow(
                () -> new ExceptionNotFound("Veiculo " + idVeiculo + " não encontrado."));

        Acessorio acessorio = acessorioRepository.findById(idAcessorio).orElseThrow(
                () -> new ExceptionNotFound("Acessório " + idAcessorio + " não encontrado."));

        if(!acessorio.isAtivo()){
            throw new ExceptionConflict("Acessório INATIVO, não é possível atribuí-lo ao veículo.");
        }
        veiculo.adicionaAcessorio(acessorio);
        Veiculo veiculoSalvo = veiculoRepository.save(veiculo);
        return veiculoMapper.toDto(veiculoSalvo);
    }

    public VeiculoDto incluirProprietario(long idVeiculo, long idProprietario) {
        Veiculo veiculo = veiculoRepository.findById(idVeiculo).orElseThrow(
                () -> new ExceptionNotFound("Não encontrado veiculo com id " + idVeiculo));

        if (veiculo.getProprietario() != null) {
            throw new ExceptionConflict("Veículo já possui um proprietário cadastrado.");
        }

        Proprietario proprietario = proprietarioRepository.findById(idProprietario).orElseThrow(
                () -> new ExceptionNotFound("Não encontrado proprietário com id " + idProprietario));

        if (!proprietario.isAtivo()){
            throw new ExceptionConflict("Proprietário INATIVO, não foi possível atribuí-lo ao veículo.");
        }

        veiculo.setProprietario(proprietario);
        return veiculoMapper.toDto(veiculoRepository.save(veiculo));
    }

    public VeiculoDto incluirDocumento(Long idVeiculo, long idDocumento ) {
        Veiculo veiculo = veiculoRepository.findById(idVeiculo).orElseThrow(
                () -> new ExceptionNotFound("Não encontrado veiculo com id " + idVeiculo));

        if (veiculo.getDocumento() != null) {
            throw new ExceptionConflict("Veículo "+idVeiculo+" já possui um documento cadastrado.");
        }

        Documento documento = documentoRepository.findById(idDocumento).orElseThrow(
                () -> new ExceptionNotFound("Não encontrado documento com id " + idDocumento));

        if (!documento.isAtivo()){
            throw new ExceptionConflict("Documento INATIVO, não foi possível atribuí-lo ao veículo.");
        }

        veiculo.setDocumento(documento);
        return veiculoMapper.toDto(veiculoRepository.save(veiculo));
    }

    //******************** API **********************************

    /**
     *
     *
     */
    public String buscaMarcasNaApi(String tipoVeiculo) {
        String url = FipeUrlBuilder.create(tipoVeiculo).build();
        return clienteHttp.obterDadosApi(url);
    }


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
        //Se a propriedade codigoFipe do DTO for nul já retorna.
        if (veiculoDto.codigoFipe() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CodigoFipe null. Veículo não encontrado.");
        } else {
            //Busca todos veículos com dodigoFipe informado
            List<Veiculo> veiculos = veiculoRepository.findAllByCodigoFipe(veiculoDto.codigoFipe());
            if (veiculos != null && !veiculos.isEmpty()) {
                for (Veiculo veiculo : veiculos) {
                    //Se existir algum com mesmo codigoFipe e mesmo ano retorna
                    if (veiculo.getAno() == veiculoDto.ano()) {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body("Veículo já cadastrado com código FIPE: " + veiculoDto.codigoFipe());
                    }
                }
                //Não existe cadastrado com mesmo codigoFipe e ano então efetua o cadastro
                Veiculo veiculoSalvo = veiculoRepository.save(veiculoMapper.toEntity(veiculoDto));
                return ResponseEntity.ok(veiculoSalvo);
            }
            //Se a lista for vazia é porque não existe nenhum cadastro com este códigoFipe ou nula efetua o cadastro.
            Veiculo veiculoSalvo = veiculoRepository.save(veiculoMapper.toEntity(veiculoDto));
            return ResponseEntity.ok(veiculoSalvo);
        }

    }

}





