package br.com.gigalike.veiculos.controller;

import br.com.gigalike.veiculos.dto.DocumentoDto;
import br.com.gigalike.veiculos.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/documento")
public class DocumentoController {
    @Autowired
    private DocumentoService documentoService;

    @GetMapping
    public ResponseEntity<List<DocumentoDto>> buscaDocumentos(){
        List<DocumentoDto> documentoDtos = documentoService.buscarDocumentos();
        return ResponseEntity.ok().body(documentoDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoDto> buscaDocumentoPeloId(@PathVariable Long id){
        DocumentoDto documentoDto = documentoService.buscaEntidadeDocumentoPorId(id);
        return ResponseEntity.ok().body(documentoDto);
    }

    @PostMapping
    public ResponseEntity<DocumentoDto> cadastrarDocumento(@RequestBody DocumentoDto documentoDto, UriComponentsBuilder uriBuilder){
        DocumentoDto documentoDtoSalvo = documentoService.salvarDocumentoNoBd(documentoDto);
        var uri = uriBuilder.path("/documento/{id}").buildAndExpand(documentoDtoSalvo.id()).toUri();
        return ResponseEntity.created(uri).body(documentoDtoSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePorId(@PathVariable Long id){
        documentoService.excluirDocumento(id);
        return ResponseEntity.noContent().build();
    }

}
