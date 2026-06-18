# ✅ Spring API Tamplate

API REST para register e login de usuários, com autenticação JWT, serviço de email para confirmação de cadastro e reset de password.

---

# 📌 Sobre o projeto

Projeto desenvolvido para facilitar a inicializaçao de outros novos projeto, contanto com um sistema de registe/login já implementado para podermos focar na regra de negócio.

O projeto vai além de um CRUD simples, implementando conceitos importantes de backend moderno como:

- autenticação JWT
- Refresh Token
- autorização com Spring Security
- tratamento global de exceções
- validações
- arquitetura REST
- proteção de rotas
- separação por camadas

---

# 🚀 Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Security
- JWT
- Spring MVC
- JPA / Hibernate
- MySQL
- Flyway
- Maven

---

# 🔐 Autenticação

A API utiliza autenticação baseada em JWT.

## Fluxo

1. Usuário realiza login
2. Recebe um token JWT
3. Envia o token nas requisições protegidas

```http
Authorization: Bearer SEU_TOKEN
```

---

# 👮 Autorização

O sistema utiliza autorização baseada em roles.

Exemplo:

- ROLE_USER
- ROLE_ADMIN

As permissões são controladas através do Spring Security.

---

# 📚 Funcionalidades

## 👤 Usuário

- Cadastro de usuário
- Login
- Reenvio de email para confirmação
- Recuperação de senha
- Criptografia de senha
- Controle de permissões

---

## 🔐 Segurança

- Rotas protegidas
- Validação de token JWT
- Controle de acesso por perfil
- Tratamento de autenticação e autorização

---

# 📡 Principais endpoints

## 🔐 Autenticação

### Login

```http
POST /v1/auth/login
```

### Cadastro

```http
POST /v1/auth/register
```

### Recuperação de senha

```http
POST /v1/auth/forgot-password
```

---



# ⚙️ Como rodar o projeto

## Clone o repositório

```bash
git clone https://github.com/dionathanpassos/spring-api-tamplate.git
```

---

## Acesse a pasta

```bash
cd spring-api-tamplate
```

---

## Configure as variáveis de ambiente

```env
DB_URL=jdbc:mysql://localhost:3306/seu_banco
DB_USER=root
DB_PASSWORD=sua_senha
JWT_SECRET=sua_chave_secreta
```

---

## Configure as variáveis de ambiente para o serviço de email

Nesse projeto estamos usando o serviço do Gmail.

Sugiro que essas informações naão sejam inseridas diretamente no arquivo properties, mas sim em variáveis de ambiente.

```env
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seuemail@gmail.com
spring.mail.password=sua_senha_de_app
```

---

## Execute o projeto

```bash
./mvnw spring-boot:run
```

---

# ⚙️ Configuração do banco

Crie um banco MySQL:

```sql
CREATE DATABASE seu_banco;
```

---

# 🧪 Exemplo de requisição

## Cadastro

```http
POST /v1/auth/register
```

```json
{
  "name": "Dionathan",
  "email": "user@email.com",
  "password": "123456"
}
```

---

## Login

```http
POST /v1/auth/login
```

```json
{
  "email": "user@email.com",
  "password": "123456"
}
```

---

## Resposta

```json
{
  "accessToken": "jwt_token_access_aqui",
  "refresToken": "jwt_token_refres_aqui"
}
```

---

# ❗ Tratamento de erros

A API segue um padrão padronizado de resposta para erros.

## Exemplo

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Token inválido ou expirado",
  "path": "/rota-protegida",
  "method": "GET",
  "timestamp": "2026-05-14T21:58:24"
}
```

---

# 🧠 Conceitos implementados

- Autenticação JWT
- Autorização com Spring Security
- Filtro JWT customizado
- Global Exception Handler
- DTOs
- Validações
- Separação Controller / Service / Repository
- Password Encoder
- Roles e Authorities
- Proteção de endpoints
- Configuração stateless

---


# 👨‍💻 Autor

Desenvolvido por Dionathan Passos

---

# 📬 Contato

Email: devdionathanpassos@gmail.com

---

# ⭐ Observação

Este projeto foi desenvolvido com foco em aprendizado e simulação de cenários reais de backend, aplicando boas práticas de arquitetura, segurança e desenvolvimento de APIs REST.
