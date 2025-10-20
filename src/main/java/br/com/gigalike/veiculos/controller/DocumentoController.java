package br.com.gigalike.veiculos.controller;

import br.com.gigalike.veiculos.dto.DocumentoDto;
import br.com.gigalike.veiculos.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documento")
public class DocumentoController {
    @Autowired
    DocumentoService documentoService = new DocumentoService();

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
    public ResponseEntity<DocumentoDto> cadastrarDocumento(@RequestBody DocumentoDto documentoDto){
        DocumentoDto documentoDtoSalvo = documentoService.salvarDocumentoNoBd(documentoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(documentoDtoSalvo);
    }
   //conferir delete e retorno de não encontrado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePorId(@PathVariable Long id){
        documentoService.excluirDocumento(id);
        return ResponseEntity.noContent().build();
    }

}
