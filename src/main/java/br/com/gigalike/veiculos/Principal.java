/*
package br.com.gigalike.veiculos;
import br.com.gigalike.veiculos.exception.FipewalException;
import br.com.gigalike.veiculos.model.*;
import br.com.gigalike.veiculos.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private static final Logger logger = LoggerFactory.getLogger(Principal.class);
    private final ClienteHttp consumoApi = new ClienteHttp();
    private final VeiculoService veiculoService;
    private final AcessorioService acessorioService;
    private final DocumentoService documentoService;
    private final ProprietarioService proprietarioService;

    public Principal(
            VeiculoService veiculoService,
            AcessorioService acessorioService,
            DocumentoService documentoService,
            ProprietarioService proprietarioService) {
        this.veiculoService = veiculoService;
        this.acessorioService = acessorioService;
        this.documentoService = documentoService;
        this.proprietarioService = proprietarioService;
    }

    public void inicio() {
        while (true) {
            exibirMenuPrincipal();
            int opcao = capturaEConversaoEntradaPromptToInt();

            switch (opcao) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    buscarVeiculoNaApi();
                    break;
                case 2:
                    cadastrarDocumento();
                    break;
                case 3:
                    cadastrarProprietario();
                    break;
                case 4:
                    cadastrarAcessorio();
                    break;
                case 5:
                    visualizarVeiculoCadastrado();
                    break;
                case 6:
                    incluirAcessorioAoVeiculo();
                    break;
                case 7:
                    incluirDocumentoAoVeiculo();
                    break;
                case 8:
                    incluirProprietarioAoVeiculo();
                    break;
                case 9:
                    visualizarTodosVeiculos();
                    break;
                case 10:
                    visualizarTodosAcessorios();
                    break;
                case 11:
                    visualizarTodosDocumentos();
                    break;
                case 12:
                    visualizarTodosProprietarios();
                    break;
            }//switch
        }//while
    }//inicio


    private void exibirMenuPrincipal() {
        String menu = "\n" + """
                **************************************************
                        MENÚ PRINCIPAL - ESCOLHA UMA OPÇÃO
                **************************************************
                0 -  SAIR
                1 -  ESCOLHER VEÍCULO NA API
                2 -  CADASTRAR DOCUMENTO
                3 -  CADASTRAR PROPRIETARIO
                4 -  CADASTRAR ACESSÓRIO
                5 -  VISUALIZAR VEICULO
                6 -  INCLUIR ACESSÓRIO AO VEÍCULO
                7 -  INCLUIR DOCUMENTO AO VEÍCULO
                8 -  INCLUIR PROPRIETÁRIO AO VEÍCULO
                9 -  VISUALIZAR TODOS VEICULOS
                10 - VISUALIZAR TODOS ACESSORIOS
                11 - VISUALIZAR TODOS DOCUMENTOS
                12 - VISUALIZAR TODOS PROPRIETARIOS
                """;
        System.out.println(menu);
    }

    private void exibirMenuTipoVeiculo() {
        String menu = """
                **************************************************
                      MENÚ TIPO VEÍCULO - ESCOLHA UMA OPÇÃO
                **************************************************
                0 - SAIR
                1 - CARRO
                2 - MOTO
                3 - CAMINHÃO
                """;
        System.out.println(menu);
    }

    private void visualizarTodosProprietarios() {
        List<Proprietario> proprietarios = proprietarioService.buscarTodosProprietarios();
        exibirLista(proprietarios);
    }


    private void visualizarTodosDocumentos() {
        List<Documento> documentos = documentoService.buscarTodosDocumentos();
        exibirLista(documentos);
    }

    private void visualizarTodosAcessorios() {
        List<Acessorio> acessorios = acessorioService.buscarTodosAcessorios();
        exibirLista(acessorios);
    }

    private void visualizarTodosVeiculos() {
        List<Veiculo> veiculos = veiculoService.buscaTodosVeiculos();
        exibirLista(veiculos);
    }

    private <T> void exibirLista(List<T>lista){
        if (lista != null && !lista.isEmpty()){
            lista.stream().forEach(System.out::println);
        }else {
            System.out.println("Dados não encontrados para exibição.");
        }
    }

    private void visualizarVeiculoCadastrado() {
        int idVeiculo = solicitaEValidaIdVeiculo();
        Veiculo veiculo = buscaEntidadeVeiculo(idVeiculo);
        System.out.println(veiculo);
    }

    private void buscarVeiculoNaApi() {
        ConverteJsonParaVeiculo converteJsonParaVeiculo = new ConverteJsonParaVeiculo();
        Scanner leitor = new Scanner(System.in);
        exibirMenuTipoVeiculo();
        int opcaoTipoVeiculo = capturaEConversaoEntradaPromptToInt();
        String strTipo = tipoVeiculo.getStrTipo(opcaoTipoVeiculo);//MOTO, CARRO OU CAMINHAO

        System.out.println("Buscando marcas...");
        String jsonMarca = consumoApi.obterDadosApi("https://parallelum.com.br/fipe/api/v1/" + strTipo + "/marcas");
        System.out.println(jsonMarca);
        System.out.println("Informe o código referente a marca desejada Ex: 59: ");

        String codMarcaStr = leitor.nextLine();
        int codMarcaInt = converteEntradaStrParaInt(codMarcaStr);

        if (codMarcaInt == -1) {
            System.out.println("marca não encontrada!");
            return;
        }
        System.out.println("Buscando modelos...");
        String jsonModelo = consumoApi.obterDadosApi("https://parallelum.com.br/fipe/api/v1/" + strTipo + "/marcas/" + codMarcaInt + "/modelos");
        System.out.println(jsonModelo);
        System.out.println("Informe o código referente ao modelo desejado. Ex: 5940: ");

        String codModeloStr = leitor.nextLine();
        int codModeloInt = converteEntradaStrParaInt(codModeloStr);

        if (codModeloInt == -1) {
            System.out.println("modelo não encontrado!");
            return;
        }
        System.out.println("Buscando anos...");
        String jsonAnos = consumoApi.obterDadosApi("https://parallelum.com.br/fipe/api/v1/" + strTipo + "/marcas/" + codMarcaInt + "/modelos/" + codModeloInt + "/anos");
        System.out.println(jsonAnos);
        System.out.println("Informe o ano desejado. Ex. 2022-3");
        ;
        String ano = leitor.nextLine();
        String jsonDadosVeiculo = consumoApi.obterDadosApi("https://parallelum.com.br/fipe/api/v1/" + strTipo + "/marcas/" + codMarcaInt + "/modelos/" + codModeloInt + "/anos/" + ano);

        System.out.println("********* JSON VEÍCULO *********");
        System.out.println(jsonDadosVeiculo);
        Veiculo veiculo = converteJsonParaVeiculo.converterJson(jsonDadosVeiculo);
        System.out.println("********* VEICULO DEPOIS DE CONVERTIDO DE JSON ********");
        if (veiculo != null){
            veiculoService.salvarVeiculoNoBd(veiculo);
        }else {
            System.out.println("Não foi possível cadastrar veículo!");
        }

    }


    private void cadastrarAcessorio() {
        System.out.println("Informe o nome nome do acessório: ");
        Scanner leitor = new Scanner(System.in);
        String nomeAcessorio = leitor.nextLine();
        System.out.println("Informe a descrição do acessório: ");
        String descricaoAcessorio = leitor.nextLine();
        System.out.println("Informe o preço do acessório: ");
        String precoAcessorioStr = leitor.nextLine();
        double precoAcessorioDouble = converteEntradaStrParaDouble(precoAcessorioStr);
        if (precoAcessorioDouble == 0.00) {
            System.out.println("Houve um problema na conversão do preço, o preço será cadastrado como R$ 0.00");
        }
        Acessorio acessorio = new Acessorio();
        acessorio.setNome(nomeAcessorio);
        acessorio.setDescricao(descricaoAcessorio);
        acessorio.setPreco(precoAcessorioDouble);
        acessorioService.salvarAcessorioNoBd(acessorio);
    }

    private void cadastrarProprietario() {
        System.out.println("Informe o nome do proprietário: ");
        Scanner leitor = new Scanner(System.in);
        String nomeProprietario = leitor.nextLine();
        System.out.println("Informe o telefone do proprietáro: ");
        String telefoneProprietario = leitor.nextLine();
        Proprietario proprietario = new Proprietario();
        proprietario.setNome(nomeProprietario);
        proprietario.setTelefone(telefoneProprietario);
        proprietarioService.salvarProprietario(proprietario);
    }

    private void cadastrarDocumento() {
        System.out.println("Informe o renavam: ");
        Scanner leitor = new Scanner(System.in);
        String renavam = leitor.nextLine();
        Documento documento = new Documento(renavam);
        documentoService.salvarDocumentoNoBd(documento);
    }



    private Veiculo buscaEntidadeVeiculo(long idVeiculo) {
        Veiculo veiculo = veiculoService.buscPorId(idVeiculo);
        if (veiculo == null) {
            System.out.println("Veiculo não encontrado!");
            return null;
        }
        return veiculo;
    }

    private void incluirAcessorioAoVeiculo() {
        int idVeiculo = solicitaEValidaIdVeiculo();
        Veiculo veiculo = buscaEntidadeVeiculo(idVeiculo);
        System.out.println("marca: " + veiculo.getMarca() + " modelo: " + veiculo.getModelo());
        System.out.println("Informe o ID do acessório que deseja incluir ou zero para voltar ao menu principal");
        Scanner leitor = new Scanner(System.in);
        String opcaoStr = leitor.nextLine();
        int idAcessorio = converteEntradaStrParaInt(opcaoStr);
        if (idAcessorio != 0) {
            Acessorio acessorio = acessorioService.buscaAcessorioPorId(idAcessorio);
            if (acessorio != null) {
                System.out.println(acessorio.getNome());
                System.out.println("""
                        Confirma a inclusão deste acessóro ao veículo?
                        Digite 1 para SIM ou 0 para NÃO
                        """);
                int confirma = capturaEConversaoEntradaPromptToInt();
                if (confirma == 1) {
                    veiculoService.incluirAcessorio(veiculo, acessorio);
                }
            } else {
                System.out.println("Acessório não encontrado");
            }
        }
    }

    private void incluirProprietarioAoVeiculo() {
        int idVeiculo = solicitaEValidaIdVeiculo();
        if (idVeiculo != -1) {
            Veiculo veiculo = buscaEntidadeVeiculo(idVeiculo);
            if (veiculo != null) {
                Scanner leitor = new Scanner(System.in);
                System.out.println("Informe o ID do proprietario: ");
                String proprietarioStr = leitor.nextLine();
                int proprietarioInt = converteEntradaStrParaInt(proprietarioStr);
                if (proprietarioInt != -1) {
                    Proprietario proprietario = proprietarioService.buscaEntidadeProprietarioPorId(proprietarioInt);
                    if (proprietario == null) {
                        System.out.println("Proprietário não encontrado no cadastro!");
                        return;
                    }
                    veiculo.setProprietario(proprietario);
                    veiculoService.salvarVeiculoNoBd(veiculo);
                }
            }
        }
    }

    private void incluirDocumentoAoVeiculo() {
        int idVeiculo = solicitaEValidaIdVeiculo();
        if (idVeiculo != -1) {
            Veiculo veiculo = buscaEntidadeVeiculo(idVeiculo);
            if (veiculo != null) {
                Scanner leitor = new Scanner(System.in);
                System.out.println("Informe o ID do documento: ");
                String documentoStr = leitor.nextLine();
                int documentoInt = converteEntradaStrParaInt(documentoStr);
                if (documentoInt != -1) {
                    Documento documento = documentoService.buscaEntidadeDocumentoPorId(documentoInt);
                    if (documento == null) {
                        System.out.println("Documento não encontrado no cadastro!");
                        return;
                    }
                    veiculo.setDocumento(documento);
                    veiculoService.salvarVeiculoNoBd(veiculo);
                }
            }
        }
    }

    private int capturaEConversaoEntradaPromptToInt() {
        Scanner leitor = new Scanner(System.in);
        System.out.println("Digite a opção desejada: ");
        String opcaoStr = leitor.nextLine().trim();
        return converteEntradaStrParaInt(opcaoStr);
    }

    private int converteEntradaStrParaInt(String opcaoStr) {
        int opcaoInt = -1;
        if (opcaoStr == null || opcaoStr.isEmpty()) {
            return opcaoInt;
        }
        try {
            opcaoInt = Integer.parseInt(opcaoStr);
        } catch (NumberFormatException e) {
            logger.error("Erro ao tentar converter número!", e);
        }
        return opcaoInt;
    }

    private int solicitaEValidaIdVeiculo() {
        System.out.println("Informe o ID do veículo: ");
        Scanner leitor = new Scanner(System.in);
        String idVeiculoStr = leitor.nextLine();
        int idVeiculo = converteEntradaStrParaInt(idVeiculoStr);
        if (idVeiculo == -1) {
            System.out.println("Opção inválida!");
        }
        return idVeiculo;
    }

    private double converteEntradaStrParaDouble(String opcaoStr) {
        double opcaoDouble = 0.00;
        if (opcaoStr == null || opcaoStr.isEmpty()) {
            return opcaoDouble;
        }
        try {
            opcaoDouble = Double.parseDouble(opcaoStr);
        } catch (NumberFormatException e) {
            logger.error("Erro ao tentar converter número!", e);
        }
        return opcaoDouble;
    }


}
*/
