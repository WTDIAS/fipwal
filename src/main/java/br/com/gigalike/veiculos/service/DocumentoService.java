package br.com.gigalike.veiculos.service;
import br.com.gigalike.veiculos.dto.DocumentoDto;
import br.com.gigalike.veiculos.exception.ExceptionBadRequest;
import br.com.gigalike.veiculos.exception.ExceptionInternalServerError;
import br.com.gigalike.veiculos.mapper.DocumentoMapper;
import br.com.gigalike.veiculos.model.Documento;
import br.com.gigalike.veiculos.model.Veiculo;
import br.com.gigalike.veiculos.repository.DocumentoRepository;
import br.com.gigalike.veiculos.repository.VeiculoRepository;
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
    @Autowired
    private VeiculoRepository veiculoRepository;


    public DocumentoDto salvarDocumentoNoBd(DocumentoDto documentoDto){
        Documento documento = documentoMapper.toEntity(documentoDto);
        return documentoMapper.toDto(documentoRepository.save(documento));
    }

    public DocumentoDto buscaEntidadeDocumentoPorId(long id){
        Documento documento = documentoRepository.findById(id).orElseThrow(()->new ExceptionBadRequest("Documento não encontrado."));
        return documentoMapper.toDto(documento);
    }

    public List<DocumentoDto> buscarDocumentos() {
        List<Documento> documentos = documentoRepository.findTop10By();
        if (documentos.isEmpty()){
            throw new ExceptionInternalServerError("Nenhum documento encontrado.");
        }
        return documentoMapper.listToDto(documentos);
    }

    public void excluirDocumento(Long id) {
        Documento documento = documentoRepository.findById(id).orElseThrow(
                ()->new ExceptionBadRequest("Não encontrado documento com ID igual a "+id));
        Veiculo veiculo = veiculoRepository.findByDocumentoId(id);
        if (veiculo != null){
            veiculo.setDocumento(null);
        }
        documento.setAtivo(false);
    }
}
