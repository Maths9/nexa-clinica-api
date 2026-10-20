# 🌿 Nexa Clínica — API REST (Backend)

[![Java](https://img.shields.io/badge/Java-21%20LTS-orange.svg?logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-brightgreen.svg?logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Data JPA](https://img.shields.io/badge/Spring%20Data-JPA-blue.svg)](https://spring.io/projects/spring-data-jpa)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15%2B-336791.svg?logo=postgresql)](https://www.postgresql.org/)
[![Flyway](https://img.shields.io/badge/Flyway-Migrations-red.svg?logo=flyway)](https://flywaydb.org/)
[![Swagger / OpenAPI](https://img.shields.io/badge/OpenAPI-3.0-green.svg?logo=swagger)](http://localhost:8080/swagger-ui.html)
[![Maven](https://img.shields.io/badge/Build-Maven-C71A36.svg?logo=apachemaven)](https://maven.apache.org/)

API REST corporativa do sistema **Nexa Clínica**, desenvolvida para gestão clínica, estética e dermatológica. O backend gerencia o ciclo completo de agendamentos, prontuários de clientes, histórico de atendimentos, movimentações de estoque, conciliação financeira (receitas e despesas), relatórios e autenticação com controle de acesso baseado em funções (**RBAC**).

---

## 🏛️ Estrutura de Pastas e Arquitetura

O backend segue a arquitetura em camadas (**Layered Architecture**) com injeção de dependências via Spring IoC:

```text
backend/nexa-clinica-api/
├── docker-compose.yml              # Subida rápida de PostgreSQL em container
├── pom.xml                         # Dependências Maven e plugins de build
├── README.md                       # Documentação técnica do backend
└── src/
    ├── main/
    │   ├── java/com/nexaclinica/api/
    │   │   ├── config/             # Segurança, CORS e serialização
    │   │   │   ├── CorsConfig.java
    │   │   │   └── SecurityConfig.java
    │   │   ├── controller/         # Camada Web / REST Controllers
    │   │   │   ├── AgendamentoController.java
    │   │   │   ├── ClienteController.java
    │   │   │   ├── DashboardController.java
    │   │   │   ├── EstoqueController.java
    │   │   │   ├── FinanceiroController.java
    │   │   │   ├── HistoricoClienteController.java
    │   │   │   ├── ProcedimentoController.java
    │   │   │   ├── RelatorioController.java
    │   │   │   └── UsuarioController.java
    │   │   ├── dto/                # Data Transfer Objects (Java Records)
    │   │   ├── entity/             # Entidades mapeadas no JPA / Hibernate
    │   │   │   ├── Agendamento.java
    │   │   │   ├── Cliente.java
    │   │   │   ├── Despesa.java
    │   │   │   ├── HistoricoCliente.java
    │   │   │   ├── MovimentacaoEstoque.java
    │   │   │   ├── Procedimento.java
    │   │   │   ├── Produto.java
    │   │   │   ├── Receita.java
    │   │   │   ├── Usuario.java
    │   │   │   └── enums/          # Tipos enumerados de domínio
    │   │   ├── repository/         # Interfaces Spring Data JPA (@EntityGraph)
    │   │   ├── service/            # Regras de negócio e transações (@Transactional)
    │   │   └── NexaClinicaApplication.java  # Classe de inicialização Spring Boot
    │   └── resources/
    │       ├── application.yml     # Configurações de datasource, porta, logs e Flyway
    │       └── db/migration/       # Scripts versionados de banco de dados (V1 a V5)
    └── test/                       # Testes de contexto e integração
```

---

## 🛠️ Tecnologias e Bibliotecas

- **Java 21 (LTS):** Compilado para bytecode Java 21, utilizando records, pattern matching e novas APIs de tempo (`java.time`).
- **Spring Boot 3.3.4:**
  - `spring-boot-starter-web`: Criação de APIs REST com Tomcat embutido na porta `8080`.
  - `spring-boot-starter-data-jpa`: Persistência de dados com Hibernate 6.
  - `spring-boot-starter-validation`: Validação de anotações (`@Valid`, `@NotNull`, etc.).
  - `spring-boot-starter-security`: Filtros de segurança e CORS.
  - `spring-boot-devtools`: Hot reload durante desenvolvimento.
- **PostgreSQL Driver 42.7.x:** Driver JDBC para comunicação com PostgreSQL.
- **Flyway Migration:** Versionamento e execução sequencial de scripts DDL e DML.
- **Lombok 1.18.34:** Redução de boilerplate (`@Getter`, `@Setter`, `@Builder`, etc.).
- **SpringDoc OpenAPI 2.6.0:** Documentação interativa via Swagger UI.

---

## ⚙️ Pré-requisitos

1. **Java JDK 21** instalado e configurado na variável de ambiente `JAVA_HOME`.
2. **PostgreSQL 15+** instalado ou **Docker** para subir o banco via Compose.
3. **IDE Recomendada:** IntelliJ IDEA (Community ou Ultimate) ou VS Code com o pacote *Extension Pack for Java*.

---

## 🚀 Como Executar o Backend

### 1. Iniciar o Banco de Dados (PostgreSQL)

#### Opção A — Via Docker Compose (Mais rápido):
Na pasta `backend/nexa-clinica-api/`:
```bash
docker compose up -d
```
Isso iniciará um container PostgreSQL 15 na porta `5432` com banco `nexa_clinica`.

#### Opção B — PostgreSQL Local:
Crie o banco de dados no seu PostgreSQL local (via pgAdmin ou psql):
```sql
CREATE DATABASE nexa_clinica;
```

Confira as credenciais no arquivo `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/nexa_clinica
    username: postgres
    password: sua_senha_aqui
```

---

### 2. Executar a Aplicação

#### Pelo IntelliJ IDEA (Recomendado):
1. No menu principal do IntelliJ, vá em **File > Open...**
2. Selecione especificamente a pasta:
   `c:\Users\devil\OneDrive\Documentos\ProjetoWEB\backend\nexa-clinica-api`
3. O IntelliJ detectará o projeto Maven automaticamente.
4. Abra o arquivo:
   `src/main/java/com/nexaclinica/api/NexaClinicaApplication.java`
5. Clique no ícone verde **Run ▶️** ao lado de `main()`.

#### Pelo Terminal:
```bash
# Na pasta do backend:
mvn spring-boot:run
```

A API iniciará na porta **`8080`**.

---

## 📖 Documentação da API (Swagger UI)

Com o backend em execução, acesse a documentação interativa com todos os endpoints, esquemas JSON e ferramenta de teste no navegador:

- **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🗄️ Migrações de Banco de Dados (Flyway)

Ao iniciar, o Flyway executa automaticamente as migrações localizadas em `src/main/resources/db/migration/`:

| Versão | Arquivo | Finalidade |
|---|---|---|
| **V1** | `V1__create_tables.sql` | Criação de todas as 9 tabelas (`usuarios`, `clientes`, `procedimentos`, `agendamentos`, `receitas`, `despesas`, `produtos`, `movimentacoes_estoque`, `historicos_cliente`) e índices de busca. |
| **V2** | `V2__seed_procedimentos.sql` | Carga inicial do catálogo de 8 tratamentos estéticos (Drenagem, Peeling, Limpeza, etc.). |
| **V3** | `V3__seed_produtos.sql` | Cadastro de insumos e cotas mínimas de inventário. |
| **V4** | `V4__seed_clientes_e_historicos.sql` | Carga de clientes de demonstração e históricos prévios. |
| **V5** | `V5__seed_usuarios.sql` | Credenciais iniciais para `ADMIN` e `SECRETARIA`. |

---

## 📡 Catálogo de Endpoints REST (`/api/v1`)

### 1. 👥 Clientes (`/api/v1/clientes`)
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/` | Lista todos os clientes cadastrados |
| `GET` | `/{id}` | Busca cliente específico por ID |
| `GET` | `/tipo/{tipo}` | Filtra clientes por status (`ATIVO`, `POTENCIAL`, `INATIVO`) |
| `GET` | `/pesquisar?termo={termo}` | Busca dinâmica por nome, telefone ou e-mail |
| `POST` | `/` | Cadastra novo cliente |
| `PUT` | `/{id}` | Atualiza dados do cliente existente |
| `DELETE` | `/{id}` | Exclui cliente com limpeza segura em cascata de registros vinculados |

---

### 2. 📅 Agendamentos (`/api/v1/agendamentos`)
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/` | Lista todos os agendamentos (otimizado via `@EntityGraph`) |
| `GET` | `/{id}` | Busca agendamento por ID |
| `GET` | `/data/{data}` | Lista agendamentos de uma data específica (`YYYY-MM-DD`) |
| `GET` | `/periodo?inicio={d1}&fim={d2}` | Lista agendamentos entre duas datas |
| `GET` | `/cliente/{clienteId}` | Lista histórico de agendamentos de um cliente |
| `POST` | `/` | Cria novo agendamento (com validação anti-retroativa) |
| `PUT` | `/{id}` | Atualiza agendamento existente |
| `PATCH` | `/{id}/status?status={status}` | Altera status (`CONFIRMADO`, `AGUARDANDO`, `CANCELADO`, `CONCLUIDO`) |
| `DELETE` | `/{id}` | Exclui agendamento criado por engano |

---

### 3. 📦 Estoque & Inventário (`/api/v1/estoque`)
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/produtos` | Lista todos os produtos em estoque |
| `GET` | `/produtos/{id}` | Busca produto por ID |
| `GET` | `/produtos/estoque-baixo` | Alerta de itens com quantidade <= estoque mínimo |
| `POST` | `/produtos` | Cadastra novo produto/insumo |
| `PUT` | `/produtos/{id}` | Atualiza dados cadastrais ou saldo direto de produto |
| `DELETE` | `/produtos/{id}` | Exclui insumo (limpando histórico de movimentações primeiro) |
| `GET` | `/movimentacoes` | Auditoria geral de movimentações ordenada por ID decrescente |
| `GET` | `/produtos/{produtoId}/movimentacoes` | Histórico de movimentações de um item específico |
| `POST` | `/produtos/{produtoId}/movimentacao` | Registra movimentação (`tipo=ENTRADA|SAIDA`, `quantidade`, `obs`) |

---

### 4. 💰 Financeiro (`/api/v1/financeiro`)
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/receitas` | Lista todas as receitas |
| `GET` | `/despesas` | Lista todas as despesas |
| `GET` | `/receitas/periodo?inicio={d1}&fim={d2}` | Receitas filtradas por período |
| `GET` | `/despesas/periodo?inicio={d1}&fim={d2}` | Despesas filtradas por período |
| `GET` | `/pendentes` | Contas a receber em aberto (`status = PENDENTE`) |
| `POST` | `/receitas` | Registra nova receita |
| `POST` | `/despesas` | Registra nova despesa operacional |
| `PATCH` | `/receitas/{id}/pagar` | Marca receita pendente como `PAGO` |
| `DELETE` | `/receitas/{id}` | Remove registro de receita |
| `DELETE` | `/despesas/{id}` | Remove registro de despesa |

---

### 5. 📋 Prontuário & Histórico Clínico (`/api/v1/historicos`)
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/` | Lista todos os históricos de atendimento |
| `GET` | `/{id}` | Busca histórico por ID |
| `GET` | `/cliente/{clienteId}` | Lista todos os atendimentos pregressos de um cliente |
| `POST` | `/` | Registra atendimento e atualiza `ultima_visita` do cliente |
| `PUT` | `/{id}` | Atualiza registro histórico |
| `DELETE` | `/{id}` | Remove registro do prontuário |

---

### 6. 🧴 Procedimentos (`/api/v1/procedimentos`)
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/` | Lista procedimentos estéticos ativos com duração e valor padrão |
| `POST` | `/` | Cadastra novo procedimento no catálogo |

---

### 7. 📊 Dashboard & Relatórios (`/api/v1/dashboard` e `/api/v1/relatorios`)
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/v1/dashboard` | Consolida faturamento, despesas, atendimentos do dia e alertas |
| `GET` | `/api/v1/relatorios/resumo` | Métricas executivas consolidadas para relatórios |

---

### 8. 🔐 Usuários & Autenticação (`/api/v1/usuarios`)
| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/login` | Valida credenciais de e-mail e senha |
| `GET` | `/` | Lista membros da equipe com papéis (`ADMIN` / `SECRETARIA`) |
| `GET` | `/{id}` | Busca usuário por ID |
| `POST` | `/` | Cadastra novo operador do sistema |
| `PUT` | `/{id}` | Atualiza perfil de usuário |
| `PATCH` | `/{id}/ativar` | Alterna status entre Ativo e Inativo |
| `DELETE` | `/{id}` | Exclui operador |

---

## 💡 Regras de Negócio Implementadas

1. **Validação Anti-Retroativa de Agendamento:**
   - O `AgendamentoService` rejeita com `400 BAD_REQUEST` cadastros com datas anteriores à data atual ou horários que já passaram no dia de hoje.
2. **Prevenção do Problema N+1 (JPA):**
   - Os repositórios (`AgendamentoRepository`, `ReceitaRepository`, `MovimentacaoEstoqueRepository`) utilizam `@EntityGraph(attributePaths = {"..."})` para carregar relacionamentos em uma única consulta SQL com `LEFT JOIN`.
3. **Auditoria de Estoque Atômica:**
   - A baixa de estoque (`SAIDA`) valida se há saldo suficiente antes de decrementar. O saldo e o log de auditoria são salvos na mesma transação `@Transactional`.
4. **Proteção Contra Erros de Serialização Jackson:**
   - As entidades possuem anotações `@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})` e o `application.yml` desabilita `fail-on-empty-beans`, evitando falhas ao serializar proxies do Hibernate.
5. **CORS Centralizado:**
   - `CorsConfig` e `@CrossOrigin` configurados para aceitar conexões do frontend em `http://localhost:5173`.

---

## 🔒 Credenciais Padrão (Seed V5)

| Perfil | E-mail | Senha |
|---|---|---|
| **ADMIN** | `clarissa@clinica.com.br` | `admin123` |
| **SECRETARIA** | `mariana@clinica.com.br` | `secretaria123` |
| **SECRETARIA** | `camila@clinica.com.br` | `secretaria123` |
