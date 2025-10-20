package br.com.gigalike.veiculos.service;
import br.com.gigalike.veiculos.dto.DocumentoDto;
import br.com.gigalike.veiculos.mapper.DocumentoMapper;
import br.com.gigalike.veiculos.model.Documento;
import br.com.gigalike.veiculos.repository.DocumentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentoServiceTest {

    @InjectMocks
    private DocumentoService documentoService;
    @Mock
    private DocumentoRepository documentoRepository;
    @Mock
    private DocumentoMapper documentoMapper;
    @Mock
    private DocumentoDto documentoDto;
    @Mock
    private Documento documento;

    @Test
    void deveSalvarDocumentoNoBancoDeDados() {
        //ARRANGE
        when(documentoMapper.toEntity(documentoDto)).thenReturn(documento);
        when(documentoRepository.save(documento)).thenReturn(documento);
        when(documentoMapper.toDto(documento)).thenReturn(documentoDto);

        //ACT
        DocumentoDto documentoDtoResultado = documentoService.salvarDocumentoNoBd(documentoDto);

        //ASSERT
        assertEquals(documentoDto,documentoDtoResultado);
    }

    //********* TESTAR DEMAIS METODOS

}