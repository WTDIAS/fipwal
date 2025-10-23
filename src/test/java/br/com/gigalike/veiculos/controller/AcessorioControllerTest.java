package br.com.gigalike.veiculos.controller;
import br.com.gigalike.veiculos.dto.AcessorioDto;
import br.com.gigalike.veiculos.exception.FipewalException400BadRequest;
import br.com.gigalike.veiculos.exception.FipewalException500InternalServerError;
import br.com.gigalike.veiculos.service.AcessorioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AcessorioController.class)
public class AcessorioControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AcessorioService acessorioService;

    private static final String endPointRaiz = "/acessorio";

    @Test
    @DisplayName("Deve retornar o acessório quando buscar por ID existente")
    void deveRetornarUmAcessorioBuscadoPeloId() throws Exception {
        //ARRANGE
        AcessorioDto acessorioDto = new AcessorioDto(1L,"ar condicionado","descrição 1",550.50);
        when(acessorioService.buscarDtoPorId(1L)).thenReturn(acessorioDto);
        long idValido = 1L;
        //ACT+ASSERT
        mockMvc.perform(get(endPointRaiz+"/"+idValido).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("ar condicionado"))
                .andExpect(jsonPath("$.descricao").value("descrição 1"))
                .andExpect(jsonPath("$.preco").value(550.50));
    }

    @Test
    @DisplayName("Deve Retornar erro código 400 ao não encontrar acessório com ID.")
    void deveRetornarFipeException400AcessorioNaoEncontrado() throws Exception {
        long idInexistente = 0L;
        when(acessorioService.buscarDtoPorId(idInexistente)).
                thenThrow(new FipewalException400BadRequest("Acessório não encontrado com ID: " + idInexistente));
        mockMvc.perform(get(endPointRaiz+"/"+idInexistente).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Acessório não encontrado com ID: " + idInexistente));
    }


    @Test
    @DisplayName("Deve retornar uma lista com alguns acessórios cadastrados no banco.")
    void deveRetornarListaComAcessorios() throws Exception {
        AcessorioDto acessorioDto1 = new AcessorioDto(1L,"Acessorio1","Descrição1",1.00);
        AcessorioDto acessorioDto2 = new AcessorioDto(2L,"Acessorio2","Descrição2",2.00);

        when(acessorioService.buscarAcessorios()).thenReturn(List.of(acessorioDto1,acessorioDto2));

        mockMvc.perform(get(endPointRaiz).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Acessorio1"))
                .andExpect(jsonPath("$[1].nome").value("Acessorio2"));
    }

    @Test
    void deveRetornarCodigo500SeNaoencontrarNenhumAcessorio() throws Exception {
        when(acessorioService
                .buscarAcessorios()).thenThrow(new FipewalException500InternalServerError("Nenhum acessório encontrado."));

        mockMvc.perform(get(endPointRaiz).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Nenhum acessório encontrado."));
    }

    @Test
    @DisplayName("Deve retornar um AcessorioDto com ID após salvar no banco de dados")
    void deveRetornarUmAcessorioDtoComIdAposSalvar() throws Exception {
        AcessorioDto acessorioDto = new AcessorioDto(1L,"Acessorio","Descrição",1.00);

        when(acessorioService.salvarAcessorioNoBd(any(AcessorioDto.class))).thenReturn(acessorioDto);

        String json = """
                {
                    "nome": "Acessorio",
                    "descricao": "Descrição",
                    "preco": 1.00
                }
                """;

        mockMvc.perform(post(endPointRaiz)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Acessorio"))
                .andExpect(jsonPath("$.descricao").value("Descrição"))
                .andExpect(jsonPath("$.preco").value(1.00));
    }


    @Test
    @DisplayName("Deve retornar codigo 500 se o json for inválido")
    void deveRetornarCodigo500SeJsonForInvalido() throws Exception {
        String jsonInvalido = """
                        {}
                        """;
        when(acessorioService.salvarAcessorioNoBd(any(AcessorioDto.class)))
                .thenThrow(new FipewalException500InternalServerError(
                        "Houve um erro interno no servidor. Tente novamente mais tarde. Se o problema persistir informe o administrador."));

        mockMvc.perform(post(endPointRaiz)
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(jsonInvalido))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(
                        "Houve um erro interno no servidor. Tente novamente mais tarde. Se o problema persistir informe o administrador."));
    }


    @Test
    @DisplayName("Deve retornar codigo 400 se não for informado o nome do acessório.")
    void deveRetornarCodigo400ParaAcessorioSemNome() throws Exception {
        String jsonInvalido = """
                {
                    "nome": "",
                    "descricao": "Descrição",
                    "preco": 1.00
                }
                """;
        when(acessorioService.salvarAcessorioNoBd(any(AcessorioDto.class)))
                .thenThrow(new FipewalException400BadRequest("Dados inválidos para cadastro de acessório."));
        mockMvc.perform(post(endPointRaiz)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Dados inválidos para cadastro de acessório."));
    }


    @Test
    @DisplayName("Deve retornar codigo 400 se for informado preço zero.")
    void deveRetornarCodigo400ParaAcessorioComPrecoZero() throws Exception {
        String jsonInvalido = """
                {
                    "nome": "Nome acessorio",
                    "descricao": "Descrição",
                    "preco": 0.00
                }
                """;
        when(acessorioService.salvarAcessorioNoBd(any(AcessorioDto.class)))
                .thenThrow(new FipewalException400BadRequest("Dados inválidos para cadastro de acessório."));
        mockMvc.perform(post(endPointRaiz)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Dados inválidos para cadastro de acessório."));
    }



    @Test
    @DisplayName("Deve retornar código 204 ao excluir o acessório do banco de dados.")
    void deveRetornarCodigo204AoExcluirAcessorio() throws Exception {
        long idValido = 1;
        mockMvc.perform(delete(endPointRaiz+"/"+idValido).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

}

