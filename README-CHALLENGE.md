# Backend-Test-TOTVS-20240208

Este projeto envolve a criação de uma API REST para um sistema de gerenciamento de contas a pagar, permitindo a execução de operações de CRUD, atualização do status de pagamentos, consultas detalhadas das contas, e importação de lotes de contas a partir de arquivos CSV.

## Sumário

- [Visão Geral do Desafio](#visão-geral-do-desafio)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Organização e Padrões](#organização-e-padrões)
- [Funcionalidades e Como Usar](#funcionalidades-e-como-usar)
    - [Cadastrar Conta](#cadastrar-conta)
    - [Atualizar Conta](#atualizar-conta)
    - [Alterar Situação da Conta](#alterar-situação-da-conta)
    - [Obter Lista de Contas a Pagar](#obter-lista-de-contas-a-pagar)
    - [Obter Conta por ID](#obter-conta-por-id)
    - [Obter Valor Total Pago por Período](#obter-valor-total-pago-por-período)
    - [Processar Arquivo Excel](#processar-arquivo-excel)
- [Testes Unitários](#testes-unitários)

## Visão Geral do Desafio

O objetivo foi desenvolver uma API REST capaz de gerenciar contas a pagar, suportando funcionalidades como CRUD completo, alteração de status de pagamento, consulta de contas, e importação de dados de arquivos CSV.

## Tecnologias Utilizadas

- **Linguagem de Programação**: Java 17
- **Frameworks**: Spring Boot 2.7
- **Banco de Dados**: PostgreSQL 12
- **Migração de Dados**: Flyway
- **Ferramentas Adicionais**: Docker, Git

## Organização e Padrões

O projeto foi estruturado seguindo o Domain-Driven Design (DDD) para melhor organização e manutenção do código. Também foram aplicados padrões de Clean Code para assegurar a clareza e a qualidade do código desenvolvido.

## Funcionalidades e Como Usar

## Iniciando o Projeto com Docker

Para configurar e iniciar o ambiente do projeto usando Docker Compose, siga os passos abaixo:

1. **Arquivo Docker Compose**: Certifique-se de ter o arquivo `docker-compose.yml` na raiz do projeto com a configuração fornecida anteriormente.

2. **Iniciar o Projeto**: Para iniciar todos os serviços definidos, execute o seguinte comando no terminal:

```bash
docker-compose up -d
```

## Funcionalidades e Como Usar


### Cadastrar Conta


Conta nova cadastrada seguindo o modelo passado:
Segue Curl: 

```bash
curl --location 'http://localhost:8080/api/contas' \
--header 'Content-Type: application/json' \
--data-raw '{
  "dataVencimento": "2023-10-31",
  "dataPagamento": "2023-12-31",
  "valor": 400.00,
  "descricao": "Conta de carro",
  "situacao": "PAGA"
}'
```


### Atualizar Conta


 Para atualizar uma  conta no sistema, utilize o seguinte comando `curl`:

```bash
curl --location --request PUT 'http://localhost:8080/api/contas/1' \
--header 'Content-Type: application/json' \
--data '{
"dataVencimento": "2024-01-31",
"dataPagamento": "2024-02-01",
"valor": 500.00,
"descricao": "Conta de Internet Atualizada",
"situacao": "PENDENTE"
}'
```



### Alterar a situação da conta;

Para essa funcionalidade foi criado um enum de situacao:
PENDENTE, PAGA, CANCELADA

Para atualizar uma situacao da conta no sistema, utilize o seguinte comando `curl`:

```bash
curl --location --request PUT 'http://localhost:8080/api/contas/1/situacao' \
--header 'Content-Type: application/json' \
--data '{"situacao": "PAGA"}'



```
### Obter a lista de contas a pagar, com filtro de data de vencimento e descrição;

Esse curl busca por data de vencimento e descricao trazendo as informacoes por paginacao:


```bash
curl --location 'http://localhost:8080/api/contas/contas-a-pagar?dataVencimento=2024-01-01&descricao=Descri%C3%A7%C3%A3o%20da%20conta&page=0&size=10' \
--header 'Accept: application/json'

```



### Obter conta filtrando o id;

Segue curl buscando conta pelo id:
```bash
curl --location 'http://localhost:8080/api/contas/1'
```



### Obter valor total pago por período.


Para realizar a chamada do desse curl com sucesso sera necessario cadastrar informacoes de datas inicio e fim
com a situacao "PAGA"


```bash
curl --location 'http://localhost:8080/api/contas/total-contas-pagas?inicio=2023-10-31&fim=2023-12-31' \
--header 'Accept: application/json'


```



## Funcionalidade de receber o caminho do excel por curl

foi criado a funcionalidade de ser passado o caminho do arquivo excel via post para a aplicacao ler as linhas
salvar no objeto e salvar no banco de dados:

na pasta resource existe um arquivo excel q pode ser utilizado para testar a chamada:
nome do arquivo:  contaexcel.xlsx

```bash
curl --location 'http://localhost:8080/api/contas/processar-excel' \
--header 'Content-Type: text/plain' \
--data 'contaexcel.xlsx'


```
### Teste do projeto:
foram criado testes unitarios do projeto onde os mesmos se encontram na pasta test

