package br.com.gigalike.veiculos.controller;
import br.com.gigalike.veiculos.dto.IdAcessorioIdRequestDto;
import br.com.gigalike.veiculos.dto.IdDocumentoRequestDto;
import br.com.gigalike.veiculos.dto.IdProprietarioRequestDto;
import br.com.gigalike.veiculos.dto.VeiculoDto;
import br.com.gigalike.veiculos.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {
    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    private ResponseEntity<List<VeiculoDto>> buscarVeiculosAtivos(){
        List<VeiculoDto> veiculoDtoList = veiculoService.buscarVeiculosAtivos();
        return ResponseEntity.ok(veiculoDtoList);
    }

    @GetMapping("/inativos")
    private ResponseEntity<List<VeiculoDto>> buscarVeiculosInativos(){
        List<VeiculoDto> veiculoDtoList = veiculoService.buscarVeiculosInativos();
        return ResponseEntity.ok(veiculoDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDto> buscaVeiculoPorId(@PathVariable Long id){
        VeiculoDto veiculoDto = veiculoService.buscaPorId(id);
        return ResponseEntity.ok(veiculoDto);
    }

    @PostMapping
    public ResponseEntity<VeiculoDto> cadastrarVeiculo(@RequestBody VeiculoDto veiculoDto, UriComponentsBuilder uriBuilder){
        VeiculoDto veiculoDtoSalvo = veiculoService.salvarVeiculoNoBd(veiculoDto);
        var uri = uriBuilder.path("/veiculos/{id}").buildAndExpand(veiculoDtoSalvo.id()).toUri();
        return ResponseEntity.created(uri).body(veiculoDtoSalvo);
    }

    @PutMapping("/{id}/acessorio")
    public ResponseEntity<VeiculoDto> incluirAcessorioAoVeiculo(@PathVariable Long id, @RequestBody IdAcessorioIdRequestDto idAcessorioIdRequestDto, UriComponentsBuilder uriBuilder){
        VeiculoDto veiculoDto = veiculoService.incluirAcessorioAoVeiculo(id, idAcessorioIdRequestDto.acessorioId());
        var uri = uriBuilder.path("/veiculos/{id}/acessorio").buildAndExpand(veiculoDto.id()).toUri();
        return ResponseEntity.created(uri).body(veiculoDto);
    }

    @PutMapping
    public ResponseEntity<VeiculoDto> atualizarVeiculo(@RequestBody VeiculoDto veiculoDto){
        VeiculoDto veiculoDtoAtualizado = veiculoService.atualizarVeiculoNoBd(veiculoDto);
        return ResponseEntity.ok().body(veiculoDtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVeiculo(@PathVariable Long id){
        veiculoService.deletarVeiculo(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idVeiculo}/proprietario")
    public ResponseEntity<VeiculoDto> incluirProprietario(@PathVariable Long idVeiculo, @RequestBody IdProprietarioRequestDto idProprietarioRequestDto, UriComponentsBuilder uriBuilder){
        VeiculoDto  veiculoDto = veiculoService.incluirProprietario(idVeiculo, idProprietarioRequestDto.proprietarioId());
        var uri = uriBuilder.path("veiculos/{idVeiculo}/proprietario").buildAndExpand(veiculoDto.id()).toUri();
        return ResponseEntity.created(uri).body(veiculoDto);
    }

    @PutMapping("/{idVeiculo}/documento")
    public ResponseEntity<VeiculoDto> incluirDocumento(@PathVariable Long idVeiculo, @RequestBody IdDocumentoRequestDto idDocumentoRequestDto, UriComponentsBuilder uriBuilder){
        VeiculoDto  veiculoDto = veiculoService.incluirDocumento(idVeiculo, idDocumentoRequestDto.documentoId());
        var uri = uriBuilder.path("veiculos/{idVeiculo}/documento").buildAndExpand(veiculoDto.id()).toUri();
        return ResponseEntity.created(uri).body(veiculoDto);
    }


    //******************** BUSCA NA API *************************

    /**
     * Para utilizar a integração com a API https://parallelum.com.br/fipe/api/v1/,
     * a primeira coisa a ser informada deve ser o tipo, sendo: carros, motos ou caminhoes
     * Exemplo de busca de carros: https://parallelum.com.br/fipe/api/v1/carros/marcas
     * */
    @GetMapping("/tipo/{tipoVeiculo}")
    public  ResponseEntity<String> exibeMarcas(@PathVariable String tipoVeiculo){
        String jsonMarcas = veiculoService.buscaMarcasNaApi(tipoVeiculo);
        return ResponseEntity.ok().body(jsonMarcas);
    }

    @GetMapping("tipo/{tipoVeiculo}/marcas/{codigoMarca}")
    public ResponseEntity<String> buscaModelosNaApi(@PathVariable String tipoVeiculo, @PathVariable int codigoMarca){
        String jsonModelos = veiculoService.buscaModelosNaApi(tipoVeiculo, codigoMarca);
        return ResponseEntity.ok().body(jsonModelos);
    }

    @GetMapping("tipo/{tipoVeiculo}/marcas/{codigoMarca}/modelos/{codigoModelo}")
    public ResponseEntity<String> buscaAnosNaApi(@PathVariable String tipoVeiculo, @PathVariable int codigoMarca, @PathVariable int codigoModelo){
        String jsonModelos = veiculoService.buscaAnosNaApi(tipoVeiculo, codigoMarca, codigoModelo);
        return ResponseEntity.ok().body(jsonModelos);
    }

    @GetMapping("tipo/{tipoVeiculo}/marcas/{codigoMarca}/modelos/{codigoModelo}/anos/{strAno}")
    public ResponseEntity buscaDadosDoVeiculoNaApi(@PathVariable String tipoVeiculo, @PathVariable int codigoMarca, @PathVariable int codigoModelo, @PathVariable String strAno){
        return veiculoService.buscaDadosVeiculoNaApi(tipoVeiculo, codigoMarca, codigoModelo, strAno);
    }

}
