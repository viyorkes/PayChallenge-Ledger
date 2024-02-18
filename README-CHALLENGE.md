# Backend-Test-TOTVS-20240208

## Visão Geral do Desafio

Foi desenvolvida uma API REST para um sistema de contas a pagar, habilitando operações de CRUD, atualização de status ao efetuar pagamentos, consulta de contas e importação de lotes via arquivo CSV.


## Tecnologias Utilizadas

- Linguagem de Programação: Java17 
- Frameworks: SpringBoot :2.7
- Banco de Dados: PostgreSQL 12
- Flyway
- Ferramentas Adicionais: Docker, Git


### Organização:
- Projeto organizado no Domain Driven Design
- Aplicação de padrões Clean Code realizadas.

### foram implementados as seguintes APIs:
   a. Cadastrar conta;


Para cadastrar uma nova conta no sistema, utilize o seguinte comando `curl`:



Note que as crases para fechar o bloco de código estão duplicadas na explicação acima para evitar que o Markdown as processe. No seu arquivo README, você deve usar apenas um conjunto de três crases para abrir e outro conjunto para fechar o bloco de código, como mostrado corretamente abaixo:






```bash
curl --location 'http://localhost:8080/api/contas' \
--header 'Content-Type: application/json' \
--data '{
  "dataVencimento": "2023-10-31",
  "dataPagamento": "2023-12-31",
  "valor": 400.00,
  "descricao": "Conta  carro",
  "situacao": "PAGA"
}'
```

<div align="center">
  <img src="img/database_info.pnj"/> 
</div>









   b. Atualizar conta;
   c. Alterar a situação da conta;
   d. Obter a lista de contas a pagar, com filtro de data de vencimento e descrição;
   e. Obter conta filtrando o id;
   f. Obter valor total pago por período.





## Como Executar

Para executar este projeto e testar as soluções do desafio, siga os passos abaixo:

1. Clone o repositório do projeto:
   ```bash
   git clone [URL do repositório]