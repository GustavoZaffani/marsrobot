# Desafio de Navegação de Robôs em Marte

## O Problema

### Terreno de Exploração

O terreno de exploração em Marte é representado por um plano retangular. Cada posição no plano é identificada por um par
de coordenadas (x, y) e uma orientação. A orientação pode ser uma das quatro direções: 'NORTH' (Norte), 'SOUTH' (Sul), '
EAST' (Leste) ou 'WEST' (Oeste).

### Comandos

Para controlar os robôs, a NASA envia uma sequência de comandos em forma de string. Cada comando pode conter as
seguintes instruções:

- 'L': Faz o robô girar 90 graus à esquerda sem se mover da posição atual.
- 'R': Faz o robô girar 90 graus à direita sem se mover da posição atual.
- 'M': Faz o robô mover-se uma posição para a frente na direção atual.

### Posição Inicial

Uma posição válida para um robô é representada por um conjunto de coordenadas (x, y) e uma orientação. Por exemplo, a
posição (0, 0, N) significa que o robô está localizado no canto inferior esquerdo do terreno, voltado para o Norte.

## Objetivo

O objetivo é criar um sistema que permita aos robôs explorar o terreno de Marte de acordo com os comandos recebidos. Os
robôs devem enviar imagens da região explorada de volta à Terra.

---

# Stack

Este projeto foi construído utilizando as seguintes tecnologias:

- Java 17
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Flyway](https://flywaydb.org/)
- [Docker](https://www.docker.com/get-started)
- [PostgreSQL](https://www.postgresql.org/)

# Como Rodar o Projeto

Este projeto foi construído utilizando [Spring Boot](https://spring.io/projects/spring-boot) e [Maven](https://maven.apache.org/). 

Para utilizá-lo basta executar o projeto localmente na sua IDE de preferência ou iniciar via Docker.

## Opção 1: Executar localmente na IDE

Para executar o projeto localmente na sua IDE, siga os seguintes passos:

1. Abra o projeto na sua IDE favorita.
2. Certifique-se de ter o Java Development Kit (JDK) versão 17 instalado.
3. Execute a aplicação Spring Boot.

O projeto será compilado e iniciado e as APIs estarão disponíveis em [http://localhost:8080](http://localhost:8080).

> **IMPORTANTE:** Para rodar local, será necessário iniciar um postgres na sua máquina e parametrizar no **application.properties.**

## Opção 2: Executar via Docker

Para executar o projeto via Docker, siga os seguintes passos:

1. Certifique-se de ter o Docker instalado e em execução em sua máquina.
2. Faça o pull da imagem do projeto e inicie o container com os seguintes comandos:

   ```bash
   docker pull gustavozaffani/marsrobot:latest
   
   docker run -p 8080:8080 gustavozaffani/marsrobot
   ```
> **IMPORTANTE:** Para não haver a necessidade de uma configuração inicial de banco de dados, a imagem Docker foi gerada apontando para um banco de dados na Cloud. No entanto a instância desse banco de dados (por ser free) permite apenas uma conexão simultânea. (Para conhecimento, foi utilizado o [elephantSQL](https://www.elephantsql.com/))       

---

# Documentação da API

## Endpoints

### 1. Inserir novo robô em Marte

- **Descrição**: Cria um novo robô em Marte com a posição inicial especificada.
- **Método HTTP**: POST
- **URL**: `/api/v1/robot`
- **Parâmetros da URL**: Nenhum
- **Corpo da requisição**: Objeto JSON contendo os dados do novo robô.
- **Resposta de sucesso**: Retorna as informações do robô criado.
- **Resposta de erro**: Retorna o erro específico para o caso.
- **Exemplo de requisição**:
  ```bash
  curl -X POST http://localhost:8080/api/v1/robots \
       -H 'Content-Type: application/json' \
       -d '{
             "name": "Rover",
             "direction": "NORTH",
             "positionX": 0,
             "positionY": 0
           }'
- **Exemplo de resposta**:
  ```json
  {
    "id": "ae3cb77d-52c4-4194-af9d-4474f921843f",
    "name": "Rover",
    "direction": "NORTH",
    "coordinateX": 0,
    "coordinateY": 0,
    "startOperation": "2024-03-27T22:39:23.8400429"
  }

### 2. Eliminar um robô em Marte

- **Descrição**: "Mata" um robô em Marte, removendo-o do terreno.
- **Método HTTP**: POST
- **URL**: `/api/v1/robot/kill/{id}`
- **Parâmetros da URL**: Identificador do robô a ser eliminado.
- **Corpo da requisição**: Nenhum
- **Resposta de sucesso**: Nenhum
- **Resposta de erro**: Retorna o erro específico para o caso.
- **Exemplo de requisição**:
  ```bash
  curl -X POST http://localhost:8080/api/v1/robots/kill \
       -H 'Content-Type: application/json' \
       -d '{}'

### 3. Recuperar informações de um robô em Marte

- **Descrição**: Recupera as informações atuais de um robô em Marte.
- **Método HTTP**: GET
- **URL**: `/api/v1/robot/{id}`
- **Parâmetros da URL**: Identificador do robô.
- **Corpo da requisição**: Nenhum
- **Resposta de sucesso**: Retorna um objeto JSON contendo os detalhes do robô solicitado.
- **Resposta de erro**:  Retorna um status de erro 404 se o robô não for encontrado.
- **Exemplo de requisição**:
  ```bash
    curl -X GET http://localhost:8080/api/v1/robots/123e4567-e89b-12d3-a456-556642440000  
- **Exemplo de resposta**:
  ```json
  {
    "id": "2667ac36-94e1-45b6-b4fd-759f5eecdc10",
    "name": "Rover",
    "direction": "NORTH",
    "coordinateX": 0,
    "coordinateY": 0,
    "startOperation": "2024-03-27T15:00:00",
    "isActive": false,
    "finishOperation": "2024-03-28T02:51:22.044133"
  }

### 4. Recuperar informações de todos os robôs de Marte

- **Descrição**: Recupera as informações atuais de todos os robôs em Marte.
- **Método HTTP**: GET
- **URL**: `/api/v1/robot`
- **Parâmetros da URL**: Paginação
- **Corpo da requisição**: Nenhum
- **Resposta de sucesso**: Retorna um objeto JSON contendo os detalhes de todos os robôs.
- **Resposta de erro**: Nenhum.
- **Exemplo de requisição**:
  ```bash
    curl -X GET http://localhost:8080/api/v1/robots  
- **Exemplo de resposta**:
  ```json
  {
    "content": [
        {
            "id": "2667ac36-94e1-45b6-b4fd-759f5eecdc10",
            "name": "Rover",
            "direction": "NORTH",
            "coordinateX": 0,
            "coordinateY": 0,
            "startOperation": "2024-03-27T15:00:00",
            "isActive": false,
            "finishOperation": "2024-03-28T02:51:22.044133"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "empty": false,
            "sorted": true,
            "unsorted": false
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalPages": 1,
    "totalElements": 1,
    "last": true,
    "size": 10,
    "number": 0,
    "sort": {
        "empty": false,
        "sorted": true,
        "unsorted": false
    },
    "numberOfElements": 1,
    "first": true,
    "empty": false
  }

### 5. Recupera imagem de um robô em Marte

- **Descrição**: Recupera a imagem da visão do robô em Marte.
- **Método HTTP**: GET
- **URL**: `/api/v1/robot/picture/{id}`
- **Parâmetros da URL**: Identificador do robô.
- **Corpo da requisição**: Nenhum
- **Resposta de sucesso**: Retorna um objeto JSON contendo um base64 da imagem.
- **Resposta de erro**: Retorna o erro específico para o caso.
- **Exemplo de requisição**:
  ```bash
    curl -X GET http://localhost:8080/api/v1/robots/picture/123e4567-e89b-12d3-a456-556642440000  
- **Exemplo de resposta**:
  ```json
  {
    "base64": "2667ac36-94e1-759f5eecdc10759f5eecdc10759f5eecdc10759f5eecdc1045b6759f5eecdc10759f5eecdc10-759f5eecdc10",
  }

### 6. Recupera o histórico de movimentação de um robô em Marte

- **Descrição**: Recupera o histórico completo de movimentações do robô em Marte.
- **Método HTTP**: GET
- **URL**: `/api/v1/robot/history/{id}`
- **Parâmetros da URL**: Identificador do robô.
- **Corpo da requisição**: Nenhum
- **Resposta de sucesso**: Retorna um objeto JSON contendo o histórico completo de movimentações do robô.
- **Resposta de erro**: Retorna o erro específico para o caso.
- **Exemplo de requisição**:
  ```bash
    curl -X GET http://localhost:8080/api/v1/robot/history/2667ac36-94e1-45b6-b4fd-759f5eecdc10  
- **Exemplo de resposta**:
  ```json
  {
    "id": "5f00fb22-45cd-4405-97d3-b38996088dd7",
    "name": "Zaffa",
    "direction": "WEST",
    "coordinateX": 10,
    "coordinateY": 5,
    "startOperation": "2024-03-28T03:03:05.842027",
    "isActive": true,
    "finishOperation": null,
    "histories": [
        {
            "id": "b65c1c55-d007-4efa-b694-8c1ccb0a4fb5",
            "sequence": 3,
            "commandDate": "2024-03-28T03:03:18.95181",
            "command": "TURN_RIGHT",
            "coordinateX": 10,
            "coordinateY": 5,
            "direction": "WEST"
        },
        {
            "id": "456ead59-215a-4c1c-8305-b4c1eabe3a52",
            "sequence": 2,
            "commandDate": "2024-03-28T03:03:18.919552",
            "command": "TURN_RIGHT",
            "coordinateX": 10,
            "coordinateY": 5,
            "direction": "SOUTH"
        },
        {
            "id": "71e5af5a-530e-4293-a1cb-9787eb3991b4",
            "sequence": 1,
            "commandDate": "2024-03-28T03:03:18.886144",
            "command": "TURN_RIGHT",
            "coordinateX": 10,
            "coordinateY": 5,
            "direction": "EAST"
        }
    ]
  }

### 7. Enviar comandos para um robô em Marte

- **Descrição**: Envia uma sequência de comandos para um robô em Marte.
- **Método HTTP**: POST
- **URL**: `/api/v1/robot/move/{id}`
- **Parâmetros da URL**: Identificador do robô.
- **Corpo da requisição**: Objeto JSON contendo a sequência de comandos.
- **Resposta de sucesso**: Retorna a localização final do robô após a execução dos comandos.
- **Resposta de erro**: Retorna o erro específico para o caso.
- **Exemplo de requisição**:
  ```bash
  curl -X POST http://localhost:8080/api/v1/robots/move/ae3cb77d-52c4-4194-af9d-4474f921843f \
       -H 'Content-Type: application/json' \
       -d '{
             "commands": "RRRLLMMM"
           }'
- **Exemplo de resposta**:
  ```json
  {
    "coordinateX": 10,
    "coordinateY": 5,
    "direction": "WEST"
  }
