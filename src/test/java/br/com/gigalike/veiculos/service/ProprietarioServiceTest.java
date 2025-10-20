package br.com.gigalike.veiculos.service;

import br.com.gigalike.veiculos.dto.ProprietarioDto;
import br.com.gigalike.veiculos.exception.FipewalException;
import br.com.gigalike.veiculos.mapper.ProprietarioMapper;
import br.com.gigalike.veiculos.model.Proprietario;
import br.com.gigalike.veiculos.repository.ProprietarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Proprietario proprietarioSemId = new Proprietario("nome","123456789");
        Proprietario proprietarioComId = new Proprietario(1L,"Nome","123456789");
        ProprietarioDto proprietarioDtoSemId = new ProprietarioDto(null,"Nome","123456789");
        ProprietarioDto proprietarioDtoComId = new ProprietarioDto(1L,"Nome","123456789");

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
    void deveLancarExcecaoQuandoNomeProprietarioForVazio(){
        //ARRANGE
        ProprietarioDto proprietarioDtoInvalido = new ProprietarioDto(null,"","123456789");
        //ACT
        FipewalException excecao = assertThrows(FipewalException.class,() -> proprietarioService.salvarProprietario(proprietarioDtoInvalido));
        //ASSERT
        assertEquals("Informe um nome para o proprietário.", excecao.getMessage());
        verify(proprietarioMapper,never()).toEntity(any());
        verify(proprietarioMapper,never()).toDto(any());
    }


    @Test
    void deveLancarExcecaoQuandoNomeProprietarioForNulo(){
        //ARRANGE
        ProprietarioDto proprietarioDtoInvalido = new ProprietarioDto(null,null,"123456789");
        //ACT & ASSERT
        FipewalException excecao = assertThrows(FipewalException.class,() -> proprietarioService.salvarProprietario(proprietarioDtoInvalido));
        assertEquals("Informe um nome para o proprietário.", excecao.getMessage());
        verify(proprietarioMapper,never()).toEntity(any());
        verify(proprietarioMapper,never()).toDto(any());
    }

    @Test
    void deveBuscaProprietarioPorIdNoBancoDeDados() {
        //ARRANGE
        Proprietario proprietario = new Proprietario(1L,"Nome","123456789");
        ProprietarioDto proprietarioDto = new ProprietarioDto(1L,"Nome","123456789");
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
    void deveLancarExcecaoQuandoProprietarioNaoForEncontrado(){
        //ARRANGE
        Long idInvalido = 0L;
        when(proprietarioRepository.findById(idInvalido)).thenReturn(Optional.empty());

        //ACT & ASSERT
        FipewalException excecao = assertThrows(FipewalException.class,()->proprietarioService.buscaProprietarioPorId(idInvalido));
        assertEquals("Proprietário não encontrado.",excecao.getMessage());
        verify(proprietarioMapper,never()).toDto(any());
        verify(proprietarioRepository,times(1)).findById(idInvalido);

    }
}