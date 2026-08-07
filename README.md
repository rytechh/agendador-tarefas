# ⏱️Agendador-Tarefas — Task Scheduler Ecosystem

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-7.0-green.svg)](https://www.mongodb.com/)
[![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-red.svg)](https://spring.io/projects/spring-security)

Microsserviço especialista na criação, controle de estado e gestão do ciclo de vida de tarefas dentro do **Task Scheduler Ecosystem**, utilizando armazenamento orientado a documentos com **MongoDB**.

> 💡 **Nota:** Este serviço é um componente interno do ecossistema. Para a documentação completa da API, orquestração e contratos de entrada, acesse o repositório do [BFF Agendador de Tarefas](https://github.com/rytechh/bff-agendador-tarefas).

---

## 📌 Responsabilidades do Serviço

* **Gestão de Tarefas:** Agendamento, busca, atualização e cancelamento de tarefas.
* **Controle de Ciclo de Vida:** Controle de transição dos estados das tarefas (`PENDENTE`, `NOTIFICADO`, `CANCELADO`).
* **Comunicação Inter-Serviços:** Integração com o microsserviço de usuários (`UsuarioClient`) para validação de contexto e autorização.
* **Segurança e Filtro de Token:** Validação de segurança via `SecurityConfig` e utilitário `JwtUtil` para garantir requisições autenticadas.
* **Persistência NoSQL:** Armazenamento flexível e de alta performance de documentos no **MongoDB**.

---

## 🛠️ Tech Stack & Infraestrutura

* **Linguagem/Framework:** Java 21 / Spring Boot 3.x
* **Persistência:** Spring Data MongoDB
* **Segurança & Integração:** Spring Security, JWT (`JwtUtil`) e OpenFeign / RestClient (`UsuarioClient`)
* **Porta Padrão de Execução:** `8081`

---

## ⚙️ Variáveis de Ambiente Recomendadas

| Variável | Descrição | Valor Padrão (Local) |
| :--- | :--- | :--- |
| `SERVER_PORT` | Porta de execução do microsserviço | `8081` |
| `SPRING_DATA_MONGODB_URI` | URI de conexão com o MongoDB | `mongodb://localhost:27017/db_tarefas` |
| `URL_USUARIO` | URL do Usuário (usado pelo `UsuarioClient`) | `http://localhost:8080` |

---

## 👤 Autor

Desenvolvido por **Raian Santos** — [@rytechh](https://github.com/rytechh)
