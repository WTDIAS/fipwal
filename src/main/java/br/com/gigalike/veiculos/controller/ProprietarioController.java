package br.com.gigalike.veiculos.controller;
import br.com.gigalike.veiculos.dto.ProprietarioDto;
import br.com.gigalike.veiculos.mapper.ProprietarioMapper;
import br.com.gigalike.veiculos.service.ProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/proprietario")
public class ProprietarioController {

    @Autowired
    private ProprietarioMapper proprietarioMapper;
    @Autowired
    private ProprietarioService proprietarioService;


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
    public ResponseEntity<ProprietarioDto> salvarProprietario(@RequestBody ProprietarioDto proprietarioDto, UriComponentsBuilder uriBuilder){
        ProprietarioDto proprietarioDtoSalvo = proprietarioService.salvarProprietario(proprietarioDto);
        var uri = uriBuilder.path("/proprietario/{id}").buildAndExpand(proprietarioDtoSalvo.id()).toUri();
        return ResponseEntity.created(uri).body(proprietarioDtoSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProprietario(@PathVariable long id){
        proprietarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
