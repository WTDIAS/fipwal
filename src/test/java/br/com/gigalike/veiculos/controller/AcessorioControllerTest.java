package br.com.gigalike.veiculos.controller;
import br.com.gigalike.veiculos.dto.AcessorioDto;
import br.com.gigalike.veiculos.exception.FipewalException400BadRequest;
import br.com.gigalike.veiculos.service.AcessorioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AcessorioController.class)
public class AcessorioControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AcessorioService acessorioService;
    private static final String endPointRaiz = "/acessorio/";

    @Test
    @DisplayName("Deve retornar o acessório quando buscar por ID existente")
    void deveRetornarUmAcessorioBuscadoPeloId() throws Exception {
        //ARRANGE
        AcessorioDto acessorioDto = new AcessorioDto(1L,"ar condicionado","descrição 1",550.50);
        when(acessorioService.buscarDtoPorId(1L)).thenReturn(acessorioDto);
        long idValido = 1L;
        //ACT+ASSERT
        mockMvc.perform(get(endPointRaiz+idValido).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("ar condicionado"))
                .andExpect(jsonPath("$.descricao").value("descrição 1"))
                .andExpect(jsonPath("$.preco").value(550.50));
    }

    @Test
    void deveRetornarFipeException400AcessorioNaoEncontrado() throws Exception {
        long idInexistente = 0L;
        when(acessorioService.buscarDtoPorId(idInexistente)).
                thenThrow(new FipewalException400BadRequest("Acessório não encontrado com ID: " + idInexistente));
        mockMvc.perform(get(endPointRaiz+idInexistente).contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Acessório não encontrado com ID: " + idInexistente));
    }
}

