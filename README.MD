# FIPWAL

Sistema de cadastro e consulta de veículos utilizando a API Fipe

---

## Sobre o Projeto

Este projeto é uma aplicação em Java construída com Spring Boot para cadastrar, consultar e gerenciar informações de veículos, proprietários, documentos e acessórios. Ele se conecta à [API Fipe](https://deividfortuna.github.io/fipe/) para obter dados de veículos como carros, motos e caminhões, permitindo o cadastro local desses dados junto a informações extras no banco.

Principais funcionalidades:
- Buscar veículos, marcas, modelos e anos diretamente da API Fipe
- Cadastrar veículos após consulta e enriquecimento de dados
- Cadastrar e associar acessórios aos veículos
- Cadastrar proprietários e documentos
- Visualizar detalhes de veículos, acessórios, documentos e proprietários
- Relacionar entidades (acessórios, documentos, proprietários) aos veículos

---

## Estrutura do Menu

Ao executar, o projeto apresentará um menu com as seguintes opções:

```
0 - Sair
1 - Escolher veículo na API
2 - Cadastrar documento
3 - Cadastrar proprietário
4 - Cadastrar acessório
5 - Visualizar veículo cadastrado
6 - Incluir acessório ao veículo
7 - Incluir documento ao veículo
8 - Incluir proprietário ao veículo
9 - Visualizar todos veículos
10 - Visualizar todos acessórios
11 - Visualizar todos documentos
12 - Visualizar todos proprietários
```

---

## Como Funciona a Busca de Veículo na API Fipe

- Escolha o tipo de veículo: carro, moto ou caminhão
- O sistema faz uma requisição para buscar marcas, você escolhe uma marca
- Depois seleciona o modelo desejado
- Informa o ano do veículo
- O sistema consulta todos os dados na API Fipe e converte em uma entidade de veículo para cadastro local

---

## Tecnologias

- Java 17+
- Spring Boot
- JUnit (para testes)
- SLF4J (Logging)
- API HTTP Java

---

## Instalação e Execução

1. **Pré-requisitos:**  
   - Java 17 ou superior instalado
   - Maven instalado

2. **Clone o repositório:**
    ```bash
    git clone https://github.com/WTDIAS/fipwal.git
    cd fipwal
    ```

3. **Build do projeto:**
    ```bash
    mvn clean install
    ```

4. **Executar a aplicação:**
    ```bash
    mvn spring-boot:run
    ```

   Você verá o menu principal no terminal para interagir pelo console.

---

## Principais Classes

- `FipewalApplication`: Classe principal que inicializa o projeto Spring Boot.
- `Principal`: Gerencia todo o fluxo da aplicação e apresentação do menu interativo.
- `ClienteHttp`: Responsável por consumir a API Fipe.
- `Veiculo`, `Acessorio`, `Documento`, `Proprietario`: Representam as entidades do banco e das operações.
- Serviços: Classes como `VeiculoService`, `AcessorioService`, etc para regras de negócio.

---

## Exemplo de Uso

- Para buscar um veículo, escolha opção 1, siga os prompts de seleção do tipo, marca, modelo e ano.
- O veículo encontrado pela API Fipe poderá ser cadastrado no banco e você pode incluir acessórios, proprietários e documentos.
- Opções do menu permitem visualizar todos os registros e gerenciar as relações.

---

## API Externa

- Utiliza a [API pública da Fipe](https://parallelum.com.br/fipe/api/v1/) para consulta de dados reais dos veículos.

---

## Testes

Execute os testes unitários com:

```bash
mvn test
```

---

## Licença

Este projeto está sob a licença MIT.

---

## Autor

Feito por WTDIAS.  
Para sugestões ou dúvidas, abra uma Issue ou contribua pelo GitHub.
