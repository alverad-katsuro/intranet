# Intranet API

Esta API ira disponibilizar os serviços para o frontend da intranet.

## Requisitos

1. Instale o OpenJDK 19.
2. Instale o Maven.
3. Instalar o PostgreSQL.


## Para Modo Dev

1. Definir as configurações do banco no application-dev.properties.

## Para Modo Prod

1. Definir as seguintes variaveis de ambiente ```POSTGRES_URL, POSTGRES_USER, POSTGRES_PASSWORD e PATHANEXOS```.

## Como Executar

1. Abra o terminal na pasta do projeto e execute o seguinte comando: ```mvn spring-boot:run```