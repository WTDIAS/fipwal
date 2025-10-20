package br.com.gigalike.veiculos.service;
import br.com.gigalike.veiculos.dto.DocumentoDto;
import br.com.gigalike.veiculos.exception.FipewalException;
import br.com.gigalike.veiculos.mapper.DocumentoMapper;
import br.com.gigalike.veiculos.model.Documento;
import br.com.gigalike.veiculos.repository.DocumentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class DocumentoService {
    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private DocumentoMapper documentoMapper;

    public DocumentoDto salvarDocumentoNoBd(DocumentoDto documentoDto){
        Documento documento = documentoMapper.toEntity(documentoDto);
        return documentoMapper.toDto(documentoRepository.save(documento));
    }

    public DocumentoDto buscaEntidadeDocumentoPorId(long id){
        Documento documento = documentoRepository.findById(id).orElseThrow(()->new FipewalException("Documento não encontrado."));
        return documentoMapper.toDto(documento);
    }

    public List<DocumentoDto> buscarDocumentos() {
        List<Documento> documentos = documentoRepository.findTop10By();
        if (documentos.isEmpty()){
            throw new FipewalException("Nenhum documento encontrado.");
        }
        return documentoMapper.listToDto(documentos);
    }

    public void excluirDocumento(Long id) {
        if (!documentoRepository.existsById(id)){
            throw new FipewalException("Não encontrado documento com ID igual a "+id);
        }
        documentoRepository.deleteById(id);
    }
}
