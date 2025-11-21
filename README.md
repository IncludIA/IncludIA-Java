# 🧠 Includ.IA - Cognitive Engine (Microserviço de IA)

> 🚀 **Global Solution 2025 - O Futuro do Trabalho**
>
> 🎓 *Disruptive Architectures: *Java Advanced*

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.7-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Oracle](https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![Generative AI](https://img.shields.io/badge/Gen_AI-Powered-8A2BE2?style=for-the-badge)

A **Includ.IA Cognitive Engine** é o núcleo inteligente da plataforma Includ.IA. Uma solução backend robusta que orquestra Inteligência Artificial Generativa, processamento de dados em tempo real e algoritmos de compatibilidade (Match) para eliminar vieses em processos seletivos.

---

## 🌐 Links e Demonstração

<div align="center">

[![Deploy](https://img.shields.io/badge/☁️%20Deploy-Acessar%20Sistema-blue?style=for-the-badge)](https://INSIRA_SEU_LINK_DO_DEPLOY_AQUI)
[![Swagger](https://img.shields.io/badge/📄%20Swagger-Documentação-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)](https://INSIRA_SEU_LINK_DO_DEPLOY_AQUI/swagger-ui.html)

[![Pitch](https://img.shields.io/badge/🎬%20Vídeo-Pitch-FF0000?style=for-the-badge&logo=youtube&logoColor=white)](https://INSIRA_SEU_LINK_DO_YOUTUBE_AQUI)
[![Demo](https://img.shields.io/badge/📺%20Demo-Técnica-212121?style=for-the-badge&logo=youtube&logoColor=white)](https://INSIRA_SEU_LINK_DA_DEMO_AQUI)

</div>

---

## 📋 Índice

* [💡 Visão Geral](#-visão-geral)
* [🛠 Arquitetura Técnica](#-arquitetura-técnica)
* [🚀 Como Executar Localmente](#-como-executar-localmente)
* [🔌 API Reference (Payloads JSON)](#-api-reference-payloads-json)
    * [1. Autenticação e Cadastro](#1-autenticação-e-cadastro)
    * [2. Gestão Corporativa](#2-gestão-corporativa)
    * [3. Vagas Inteligentes](#3-vagas-inteligentes)
    * [4. Perfil e Currículo](#4-perfil-e-currículo)
    * [5. Match e Interações](#5-match-e-interações)
    * [6. Chat Real-Time](#6-chat-real-time)
* [⚡ Testando o WebSocket](#-testando-o-websocket)
* [👥 Integrantes](#-integrantes)

---

## 💡 Visão Geral

O sistema foi projetado para resolver a exclusão no mercado de trabalho através de tecnologia:

1.  **Recrutamento às Cegas (Blind Recruitment):** Dados sensíveis (CPF, Endereço, Gênero) são mascarados nas etapas iniciais.
2.  **IA Generativa Integrada:**
    * *Vagas:* Reescreve descrições para remover termos excludentes (etarismo, machismo).
    * *Candidatos:* Gera resumos profissionais focados estritamente em *Hard* e *Soft Skills*.
3.  **Matchmaking Engine:** Algoritmo que cruza competências, localização (raio em km) e modelo de trabalho para gerar um *Score de Compatibilidade*.

---

## 🛠 Arquitetura Técnica

* **Core:** Java 21 + Spring Boot 3.
* **Persistência:** Oracle Database (Relacional) + JPA/Hibernate.
* **Performance:** Redis para cache de Feed e Sessões.
* **Assincronismo:** RabbitMQ para processamento de Matches e Notificações.
* **Comunicação:** REST API (Cliente/Servidor) e WebSocket (STOMP) para Chat.
* **IA:** Cliente HTTP integrado a microserviço Python/Azure para inferência de modelos LLM (Gemini/GPT).

---

## 🚀 Como Executar Localmente

### Pré-requisitos
* Docker e Docker Compose instalados.

### Passo a Passo

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/seu-time/IncludIA-Java.git
    cd IncludIA-Java
    ```

2.  **Suba a Infraestrutura (Oracle, Redis, RabbitMQ):**
    ```bash
    docker-compose up -d
    ```
    *Aguarde o Oracle Database inicializar (pode levar alguns minutos).*

3.  **Execute a Aplicação:**
    ```bash
    ./gradlew clean bootRun
    ```
    > 🟢 **API Online:** `http://localhost:8080`
    >
    > 📄 **Swagger UI:** `http://localhost:8080/swagger-ui.html`

---

## 🔌 API Reference (Payloads JSON)

Utilize os exemplos abaixo para testar os endpoints no **Swagger** ou **Postman**.

### 1. Autenticação e Cadastro

**Registrar Candidato** (`POST /auth/register-candidate`)
```json
{
  "nome": "Alex Pereira",
  "cpf": "12345678900",
  "email": "alex@email.com",
  "senha": "SenhaForte@123",
  "resumoPerfil": "Desenvolvedor Full Stack com foco em Java e React.",
  "cep": "01310100",
  "logradouro": "Av. Paulista",
  "numero": "1000",
  "bairro": "Bela Vista",
  "cidade": "São Paulo",
  "estado": "SP",
  "raioBuscaKm": 30
}
````

**Registrar Recrutador** (`POST /auth/register-recruiter`)

```json
{
  "nome": "Julia Recruiter",
  "email": "julia@techjobs.com",
  "senha": "SenhaForte@123",
  "empresaId": "cole-o-uuid-da-empresa-aqui"
}
```

**Login** (`POST /auth/login`)

```json
{
  "email": "alex@email.com",
  "senha": "SenhaForte@123"
}
```

-----

### 2\. Gestão Corporativa

**Criar Empresa** (`POST /empresas`)

```json
{
  "nomeOficial": "Inovação Tech SA",
  "nomeFantasia": "InovaTech",
  "cnpj": "12345678000199",
  "localizacao": "São Paulo, SP",
  "descricao": "Empresa líder em soluções de IA para o varejo.",
  "cultura": "Focada em diversidade, inovação e work-life balance.",
  "fotoCapaUrl": "[https://exemplo.com/capa.jpg](https://exemplo.com/capa.jpg)"
}
```

-----

### 3\. Vagas Inteligentes

**Criar Vaga** (`POST /vagas`)

> *Nota: A IA processará a `descricaoOriginal` automaticamente para torná-la inclusiva.*

```json
{
  "titulo": "Engenheiro de Software Java",
  "descricaoOriginal": "Procuramos um ninja que respire código e não tenha hora para sair.",
  "localizacao": "São Paulo, SP",
  "tipoVaga": "TEMPO_INTEGRAL",
  "modeloTrabalho": "HIBRIDO",
  "salarioMin": 9000.00,
  "salarioMax": 14000.00,
  "beneficios": "Vale Refeição, Plano de Saúde, Gympass",
  "experienciaRequerida": "Experiência com microserviços",
  "skillIds": [
    "uuid-da-skill-java",
    "uuid-da-skill-docker"
  ]
}
```

**Feed de Candidatos (Tinder)** (`GET /vagas/{idVaga}/candidates-feed`)
*Retorna a lista de candidatos ordenados por compatibilidade (Score) para a vaga especificada.*

-----

### 4\. Perfil e Currículo

**Adicionar Experiência** (`POST /profile/experience`)

```json
{
  "tituloCargo": "Desenvolvedor Backend Júnior",
  "tipoEmprego": "ESTAGIO",
  "dataInicio": "2023-01-15",
  "dataFim": "2024-01-15",
  "descricao": "Desenvolvimento de APIs REST e manutenção de legado.",
  "empresaId": "uuid-da-empresa-opcional"
}
```

**Adicionar Educação** (`POST /profile/education`)

```json
{
  "nomeInstituicao": "FIAP",
  "grau": "BACHARELADO",
  "areaEstudo": "Análise e Desenvolvimento de Sistemas",
  "dataInicio": "2024-02-01",
  "descricao": "Foco em arquitetura de software e IA."
}
```

**Adicionar Idioma** (`POST /profile/language`)

```json
{
  "nomeIdioma": "INGLES",
  "nivelProficiencia": "AVANCADO"
}
```

-----

### 5\. Match e Interações

**Dar Like/Dislike (Candidato ou Recrutador)** (`POST /swipe/candidate` ou `/swipe/recruiter/{vagaId}`)

```json
{
  "targetId": "uuid-do-alvo-(vaga-ou-candidato)",
  "isLiked": true
}
```

-----

### 6\. Chat Real-Time

**Enviar Mensagem** (`POST /chats/{chatId}/messages`)

```json
{
  "conteudo": "Olá! Gostaria de agendar uma entrevista.",
  "receiverId": "uuid-do-destinatario"
}
```

-----

## ⚡ Testando o WebSocket

Para validar a comunicação em tempo real sem um frontend móvel:

1.  Crie um arquivo `index.html` localmente.
2.  Insira o script de conexão **SockJS + STOMP**.
3.  Conecte em: `http://localhost:8080/ws` (ou link do deploy).
4.  Inscreva-se no tópico: `/topic/chat/{CHAT_ID}`.
5.  Ao enviar uma mensagem via API (Swagger), ela aparecerá instantaneamente no console do navegador.

-----

## 👥 Integrantes

  * **RM 555213** - Luiz Eduardo Da Silva Pinto

