# Backend-Test-TOTVS-20240208

## Introdução

Neste desafio você deverá implementar uma API REST para um sistema simples de contas a pagar. O sistema permitirá realizar o CRUD de uma conta a pagar, alterar a situação dela quando for efetuado pagamento, obter informações sobre as contas cadastradas no banco de dados, e importar um lote de contas de um arquivo CSV, conforme descrito abaixo.

## Antes de começar
 
- Considere como deadline da avaliação a partir do início do teste. Caso tenha sido convidado a realizar o teste e não seja possível concluir dentro deste período, avise a pessoa que o convidou para receber instruções sobre o que fazer.
- Documentar todo o processo de investigação para o desenvolvimento da atividade (README.md no seu repositório); os resultados destas tarefas são tão importantes do que o seu processo de pensamento e decisões à medida que as completa, por isso tente documentar e apresentar os seus hipóteses e decisões na medida do possível.

## Instruções iniciais obrigatórias

- Utilize as seguintes tecnologias: Java 17 ou superior, Spring boot, PostgreSQL, Docker, Docker Compose, Github, Flyway, DDD, Rest API, JPA

### Organização:
- Organizar o projeto com Domain Driven Design
- Aplicação de padrões Clean Code

## Requisitos Gerais
1. Utilizar a linguagem de programação Java, versão 17 ou superior.
2. Utilizar Spring Boot.
3. Utilizar o banco de dados PostgreSQL.
4. A aplicação deve ser executada em um container Docker.
5. Tanto a aplicação, banco de dados, quanto outros serviços necessários para executar a aplicação, devem ser orquestrados utilizando Docker Compose.
6. O código do projeto deve ser hospedado no GitHub ou GitLab.
7. Utilizar mecanismo de autenticação.
8. Organizar o projeto com Domain Driven Design.
9. Utilizar o Flyway para criar a estrutura de banco de dados.
10. Utilizar JPA.
11. Todas as APIs de consulta devem ser paginadas.

## Requisitos Específicos
1. Cadastrar a tabela no banco de dados para armazenar as contas a pagar. Deve incluir no mínimo os seguintes campos: (Faça a tipagem conforme achar adequado)
    a. id
    b. data_vencimento
    c. data_pagamento
    d. valor
    e. descricao
    f. situacao

2. Implementar a entidade “Conta” na aplicação, de acordo com a tabela criada anteriormente.
3. Implementar as seguintes APIs:
    a. Cadastrar conta;
    b. Atualizar conta;
    c. Alterar a situação da conta;
    d. Obter a lista de contas a pagar, com filtro de data de vencimento e descrição;
    e. Obter conta filtrando o id;
    f. Obter valor total pago por período.

4. Implementar mecanismo para importação de contas a pagar via arquivo csv.
   a. O arquivo será consumido via API.

5. Implementar testes unitários.

## Readme do Repositório

- Deve conter o título do projeto
- Uma descrição sobre o projeto em frase
- Deve conter uma lista com linguagem, framework e/ou tecnologias usadas
- Como instalar e usar o projeto (instruções)
- Não esqueça o [.gitignore](https://www.toptal.com/developers/gitignore)
- Se está usando github pessoal, referencie que é um challenge by coodesh:  

>  This is a challenge by [Coodesh](https://coodesh.com/)

## Finalização e Instruções para a Apresentação

1. Adicione o link do repositório com a sua solução no teste
2. Adicione o link da apresentação do seu projeto no README.md.
3. Verifique se o Readme está bom e faça o commit final em seu repositório;
4. Envie e aguarde as instruções para seguir. Sucesso e boa sorte. =)

## Suporte

Use a [nossa comunidade](https://discord.gg/rdXbEvjsWu) para tirar dúvidas sobre o processo ou envie uma mensagem diretamente a um especialista no chat da plataforma. 
