# Projeto Demo App

O projeto demo app, tem o intituito de gerenciar transações de controle simples, com os recursos:

- criação de contas (*Accounts*).
- lançamento de transações (*Transactions*).
- baseado no tipo de operação (*OperationalTypes*), lançar o valor (*amount*) de forma positiva ou negativa.

### Pré-requisitos

Para a melhor execução do ambiente, relacionado ao projeto, é importante que se tenha, as seguintes soluções instaladas:

- OpenJDK 11 (https://adoptopenjdk.net/)
- Docker (https://docs.docker.com/engine/install/)
- Docker Compose (https://docs.docker.com/compose/install/)

## Desevolvimento

O projeto é executado, utilizando o _Spring Boot_ como framework de backend, e no ambiente de desenvolvimento e utilizando o banco de dados PostgreSQL.

Para execução do projeto é necessário ter o banco PostgreSQL na máquina, ou rodar utilizando container.

Para executar em formato de container, estando na pasta do projeto, execute:

```shell
docker-compose up --detach postgresql-server
```

Dessa forma, o servidor de banco de dados irá executar localmente, e escutando na porta padrão **5432**, com as seguintes dados de autenticação:

- **usuário**: demoapp
- **senha**: demoapp
- **conexão**: localhost:5432

Após o banco de dados estar em execução, abra o projeto na sua IDE de preferência, ou caso deseje excutar o ambiente baseado no código, execute:

```shell
#tenha certeza de ter o Java instalado e configurado corretamente.
./gradlew bootRun
```

Através de uma IDE, é possível executar a classe: `DemoAppApplication.groovy`

## Testes

Os testes foram desenvolvidos utilizando o **MockMVC**, e utilizando o banco de dados **H2** para guardar os dados em memória.

O arquivo profile utilizado para essa finalidade é o `application-test.yaml`.

Caso deseje executar os testes:

```shell
./gradlew test 
```

## Execução do Projeto Completo (build and deploy)

Foi incluído no projeto um `docker-compose.yaml` e um `Dockerfile` composto do processo de build e execução do projeto (multi-stage).

Caso deseje executar o projeto completo, com a build, e ambiente de execução, bem como o servidor **PostgreSQL**, na pasta do projeto, execute o seguinte comando:

```shell
docker-compose up --detach
```

Foi adicionado ao projeto alguns arquivos com extensão `.http` que visa facilitar a interação do projeto. 

Tais arquivos podem ser utilizado em conjunto com o VSCode(https://code.visualstudio.com/download) com o complemento Rest Client(https://marketplace.visualstudio.com/items?itemName=humao.rest-client).

Os arquivos em questão, estão na pasta `client`, e podem ser invocados de forma simples.