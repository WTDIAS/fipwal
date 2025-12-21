package br.com.gigalike.veiculos.service;
import br.com.gigalike.veiculos.dto.ProprietarioDto;
import br.com.gigalike.veiculos.exception.ExceptionBadRequest;
import br.com.gigalike.veiculos.mapper.ProprietarioMapper;
import br.com.gigalike.veiculos.model.Proprietario;
import br.com.gigalike.veiculos.repository.ProprietarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProprietarioServiceTest {
    @InjectMocks
    private ProprietarioService proprietarioService;
    @Mock
    private ProprietarioRepository proprietarioRepository;
    @Mock
    private ProprietarioMapper proprietarioMapper;


    @Test
    void deveSalvarProprietarioNoBancoDeDados() {
        //ARRANGE
        Proprietario proprietarioSemId = new Proprietario("Nome","123456789",true, null);
        Proprietario proprietarioComId = new Proprietario(1l,"Nome","123456789",true, null);
        ProprietarioDto proprietarioDtoSemId = new ProprietarioDto(null,"Nome","123456789", true);
        ProprietarioDto proprietarioDtoComId = new ProprietarioDto(1L,"Nome","123456789",true);

        when(proprietarioMapper.toEntity(proprietarioDtoSemId)).thenReturn(proprietarioSemId);
        when(proprietarioRepository.save(proprietarioSemId)).thenReturn(proprietarioComId);
        when(proprietarioMapper.toDto(proprietarioComId)).thenReturn(proprietarioDtoComId);

        //ACT
        ProprietarioDto proprietarioDtoResultado = proprietarioService.salvarProprietario(proprietarioDtoSemId);

        //ASSERT
        assertNotNull(proprietarioDtoResultado,"O DTO retornado não deve ser nulo. ");
        assertEquals(proprietarioDtoResultado,proprietarioDtoComId,"O DTO retornado deve ser o mesmo do DTO de entrada.");

        verify(proprietarioRepository,times(1)).save(proprietarioSemId);
        verify(proprietarioMapper,times(1)).toDto(proprietarioComId);
        verify(proprietarioMapper,times(1)).toEntity(proprietarioDtoSemId);
    }


    @Test
    void deveRetornarCodigo400QuandoNomeProprietarioForVazio(){
        //ARRANGE
        ProprietarioDto proprietarioDtoInvalido = new ProprietarioDto(null,"","123456789", true);
        //ACT
        ExceptionBadRequest excecao = assertThrows(ExceptionBadRequest.class,() -> proprietarioService.salvarProprietario(proprietarioDtoInvalido));
        //ASSERT
        assertEquals("Informe um nome para o proprietário.", excecao.getMessage());
        verify(proprietarioMapper,never()).toEntity(any());
        verify(proprietarioMapper,never()).toDto(any());
    }


    @Test
    void deveRetornarCodigo400QuandoNomeProprietarioForNulo(){
        //ARRANGE
        ProprietarioDto proprietarioDtoInvalido = new ProprietarioDto(null,null,"123456789", true);
        //ACT & ASSERT
        ExceptionBadRequest excecao = assertThrows(ExceptionBadRequest.class,() -> proprietarioService.salvarProprietario(proprietarioDtoInvalido));
        assertEquals("Informe um nome para o proprietário.", excecao.getMessage());
        verify(proprietarioMapper,never()).toEntity(any());
        verify(proprietarioMapper,never()).toDto(any());
    }


    @Test
    @DisplayName("Deve retornar um proprietário ao buscar por um ID válido.")
    void deveRetornarUmProprietarioPorId() {
        //ARRANGE
        Proprietario proprietario = new Proprietario(1L,"Nome","123456789",true,null);
        ProprietarioDto proprietarioDto = new ProprietarioDto(1L,"Nome","123456789",true);
        Long id = 1L;
        when(proprietarioRepository.findById(id)).thenReturn(Optional.of(proprietario));
        when(proprietarioMapper.toDto(proprietario)).thenReturn(proprietarioDto);

        //ACT
         ProprietarioDto proprietarioDtoResultado = proprietarioService.buscaProprietarioPorId(id);

        //ASSERT
        assertNotNull(proprietarioDtoResultado,"O resultado da busca não deve ser nulo.");
        assertEquals(proprietarioDtoResultado,proprietarioDto);
        verify(proprietarioRepository,times(1)).findById(id);
        verify(proprietarioMapper,times(1)).toDto(proprietario);

    }

    @Test
    @DisplayName("Deve retornar codigo 400 se o ID informado não for encontrado no bando de dados.")
    void deveRetornarCodigo400QuandoProprietarioNaoForEncontrado(){
        //ARRANGE
        Long idInvalido = 0L;
        when(proprietarioRepository.findById(idInvalido)).thenReturn(Optional.empty());

        //ACT & ASSERT
        ExceptionBadRequest excecao = assertThrows(ExceptionBadRequest.class,()->proprietarioService.buscaProprietarioPorId(idInvalido));
        assertEquals("Proprietário não encontrado.",excecao.getMessage());
        verify(proprietarioMapper,never()).toDto(any());
        verify(proprietarioRepository,times(1)).findById(idInvalido);

    }

    @Test
    @DisplayName("Deve retornar uma lista com alguns proprietarios cadastrados no banco de dados")
    void deveRetornarListaComProprietarios(){
        //ARRANGE
        Proprietario proprietario1 = new Proprietario(1L,"Proprietario1","123456789",true,null);
        Proprietario proprietario2 = new Proprietario(2L,"Proprietario2","559491847",true,null);
        List<Proprietario> proprietarioList = List.of(proprietario1,proprietario2);

        ProprietarioDto proprietarioDto1 = new ProprietarioDto(1L,"proprietarioDto1","123456789",true);
        ProprietarioDto proprietarioDto2 = new ProprietarioDto(2L,"proprietarioDto2","559491847",true);
        List<ProprietarioDto> proprietarioDtoList = List.of(proprietarioDto1,proprietarioDto2);

        when(proprietarioRepository.findTop10By()).thenReturn(proprietarioList);
        when(proprietarioMapper.listToDto(proprietarioList)).thenReturn(proprietarioDtoList);

        //ACT
        List<ProprietarioDto> resultadoList = proprietarioService.buscaProprietarios();

        //ASSERT
        assertEquals(resultadoList,proprietarioDtoList);
        verify(proprietarioRepository,times(1)).findTop10By();
        verify(proprietarioMapper,times(1)).listToDto(proprietarioList);

    }
}