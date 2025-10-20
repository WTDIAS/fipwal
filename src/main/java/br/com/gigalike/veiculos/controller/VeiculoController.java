package br.com.gigalike.veiculos.controller;
import br.com.gigalike.veiculos.dto.AcessorioIdRequestDto;
import br.com.gigalike.veiculos.dto.VeiculoDto;
import br.com.gigalike.veiculos.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {
    @Autowired
    VeiculoService veiculoService;

    @GetMapping
    private ResponseEntity<List<VeiculoDto>> buscarVeiculos(){
        List<VeiculoDto> veiculoDtoList = veiculoService.buscarVeiculos();
        return ResponseEntity.ok(veiculoDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDto> buscaVeiculoPorId(@PathVariable Long id){
        VeiculoDto veiculoDto = veiculoService.buscaPorId(id);
        return ResponseEntity.ok(veiculoDto);
    }


    @PostMapping
    public ResponseEntity<VeiculoDto> cadastrarVeiculo(@RequestBody VeiculoDto veiculoDto){
        VeiculoDto veiculoDtoSalvo = veiculoService.salvarVeiculoNoBd(veiculoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoDtoSalvo);
    }

    @PostMapping("/{idVeiculo}/acessorio")
    public ResponseEntity<VeiculoDto> incluirAcessorioAoVeiculo(@PathVariable Long idVeiculo, @RequestBody AcessorioIdRequestDto acessorioIdRequestDto){
        VeiculoDto veiculoDto = veiculoService.incluirAcessorioAoVeiculo(idVeiculo,acessorioIdRequestDto.acessorioId());
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoDto);
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


    //******************** BUSCA NA API *************************

    @GetMapping("/tipo/{codigoTipo}")
    public  ResponseEntity<String> exibeMarcas(@PathVariable int codigoTipo){
        String jsonMarcas = veiculoService.buscaMarcasNaApi(codigoTipo);
        return ResponseEntity.ok().body(jsonMarcas);
    }

    @GetMapping("tipo/{codigoTipo}/marca/{codigoMarca}")
    public ResponseEntity<String> buscaModelosNaApi(@PathVariable int codigoTipo, @PathVariable int codigoMarca){
        String jsonModelos = veiculoService.buscaModelosNaApi(codigoTipo, codigoMarca);
        return ResponseEntity.ok().body(jsonModelos);
    }

    @GetMapping("tipo/{codigoTipo}/marca/{codigoMarca}/modelo/{codigoModelo}")
    public ResponseEntity<String> buscaAnosNaApi(@PathVariable int codigoTipo, @PathVariable int codigoMarca, @PathVariable int codigoModelo){
        String jsonModelos = veiculoService.buscaAnosNaApi(codigoTipo, codigoMarca, codigoModelo);
        return ResponseEntity.ok().body(jsonModelos);
    }

    @GetMapping("tipo/{codigoTipo}/marca/{codigoMarca}/modelo/{codigoModelo}/ano/{strAno}")
    public ResponseEntity<VeiculoDto> buscaDadosDoVeiculoNaApi(@PathVariable int codigoTipo, @PathVariable int codigoMarca, @PathVariable int codigoModelo, @PathVariable String strAno){
        VeiculoDto veiculoDto = veiculoService.buscaDadosVeiculoNaApi(codigoTipo, codigoMarca, codigoModelo, strAno);
        return ResponseEntity.ok().body(veiculoDto);
    }

}
