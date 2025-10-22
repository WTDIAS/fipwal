package br.com.gigalike.veiculos.controller;
import br.com.gigalike.veiculos.dto.AcessorioDto;
import br.com.gigalike.veiculos.service.AcessorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
//Waldir 22/10/2025

@RestController
@RequestMapping("/acessorio")
public class AcessorioController {
    @Autowired
    AcessorioService acessorioService = new AcessorioService();

    @GetMapping("/{id}")
    public ResponseEntity<AcessorioDto> buscaAcessorioPorId(@PathVariable long id){
        AcessorioDto acessorioDto = acessorioService.buscarDtoPorId(id);
        return ResponseEntity.ok().body(acessorioDto);
    }

    @GetMapping
    public ResponseEntity<List<AcessorioDto>> buscaAcessorios(){
        List<AcessorioDto> acessorioList = acessorioService.buscarAcessorios();
        return ResponseEntity.ok().body(acessorioList);
    }

    @PostMapping
    public ResponseEntity<AcessorioDto> salvarAcessorioNoBd(@RequestBody AcessorioDto acessorioDto){
        AcessorioDto acessorioDtoSalvo = acessorioService.salvarAcessorioNoBd(acessorioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(acessorioDtoSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaAcessorio(@PathVariable long id){
        acessorioService.deletaAcessorio(id);
        return ResponseEntity.noContent().build();
    }
}
