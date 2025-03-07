# Projeto de Mensageria com Apache Kafka

Este projeto consiste em um sistema de mensageria para validação de boletos bancários utilizando **Apache Kafka** para comunicação entre duas APIs desenvolvidas em **Java 17 com Spring Boot 3.x**.

## 📌 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **Apache Kafka**
- **Docker & Docker Compose**
- **PostgreSQL/MySQL (ou outro banco de dados, se aplicável)**
- **Lombok**
- **Spring Cloud Stream (opcional, para facilitar integração com Kafka)**

## 📁 Estrutura do Projeto

```
mensageria/
│── api-boleto/          # API responsável pela emissão de boletos
│── validador-boleto/    # API responsável por validar boletos recebidos
│── docker-compose.yml   # Arquivo para subir Kafka e outras dependências
│── README.md            # Documentação do projeto
└── ...
```

## 🚀 Como Executar o Projeto

### Pré-requisitos

Antes de iniciar, certifique-se de ter instalado:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Java 17](https://adoptium.net/)
- [Apache Kafka](https://kafka.apache.org/)

###1️⃣ Subindo os serviços com Docker

```sh
docker-compose up -d
```

Isso iniciará o Apache Kafka e outros serviços necessários.

### 2️⃣ Executando as APIs

Navegue até cada API e execute:

```sh
cd api-boleto
./mvnw spring-boot:run
```

```sh
cd validador-boleto
./mvnw spring-boot:run
```

## 🔄 Fluxo de Comunicação

1. **A API de boletos (`api-boleto`)** gera um boleto e publica uma mensagem no tópico Kafka.
2. **A API de validação (`validador-boleto`)** consome essa mensagem e processa a validação do boleto.
3. Após a validação, o resultado pode ser salvo no banco de dados ou enviado para outro serviço.

## 📜 Endpoints

### API - Geração de Boleto (`api-boleto`)

| Método | Endpoint       | Descrição                |
|--------|--------------|------------------------|
| POST   | `/boletos`   | Gera um novo boleto e envia para Kafka |

### API - Validação de Boleto (`validador-boleto`)

| Método | Endpoint       | Descrição                     |
|--------|--------------|-----------------------------|
| GET    | `/boletos/{id}` | Retorna o status da validação do boleto |

## 🛠️ Testando com Postman ou cURL

### Enviar um boleto para validação:
```sh
curl -X POST "http://localhost:8080/boletos" -H "Content-Type: application/json" -d '{
    "numero": "123456789"
}'
```

### Consultar status do boleto:
```sh
curl -X GET "http://localhost:8081/boletos/123456789"
```

## 📌 Melhorias Futuras

- Implementação de **Autenticação e Autorização (OAuth2, JWT)**
- Monitoramento com **Prometheus e Grafana**
- Testes automatizados **(JUnit, Testcontainers para integração com Kafka)**

## 📜 Licença

Este projeto está sob a licença MIT.

