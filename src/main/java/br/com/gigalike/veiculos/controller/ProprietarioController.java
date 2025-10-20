package br.com.gigalike.veiculos.controller;
import br.com.gigalike.veiculos.dto.ProprietarioDto;
import br.com.gigalike.veiculos.mapper.ProprietarioMapper;
import br.com.gigalike.veiculos.service.ProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proprietario")
public class ProprietarioController {

    @Autowired
    ProprietarioMapper proprietarioMapper;
    @Autowired
    ProprietarioService proprietarioService;


    @GetMapping
    public ResponseEntity<List<ProprietarioDto>> buscaProprietarios(){
        List<ProprietarioDto> proprietarioDtoList = proprietarioService.buscaProprietarios();
        return ResponseEntity.ok().body(proprietarioDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProprietarioDto> buscaProprietarioPorId(@PathVariable long id){
        ProprietarioDto proprietarioDto = proprietarioService.buscaProprietarioPorId(id);
        return ResponseEntity.ok().body(proprietarioDto);
    }

    @PostMapping
    public ResponseEntity<ProprietarioDto> salvarProprietario(@RequestBody ProprietarioDto proprietarioDto){
        ProprietarioDto proprietarioDtoSalvo = proprietarioService.salvarProprietario(proprietarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(proprietarioDtoSalvo);
    }
}
