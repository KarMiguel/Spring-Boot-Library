# Library

Este é um projeto de aprendizado abrangente focado em várias funcionalidades essenciais para uma aplicação de biblioteca utilizando Spring Boot.

## Objetivo

Este projeto visa fornecer uma base sólida para aprender e explorar os seguintes conceitos:

- Upload e Download de Arquivo
- Autenticação de Usuário
- Envio de Email para Confirmação e Código para Mudança de Senha
- Consumo de API Externa de Notícias
- Implementação de HATEOAS
- Exploração da Arquitetura Spring
- Desenvolvimento de Operações CRUD para Livros e outros recursos essenciais para uma biblioteca.

## Tecnologias Utilizadas

- Spring Boot 3.2.0
- Spring Security
- Spring Data JPA
- Spring Web
- Spring Mail
- Spring HATEOAS
- Springdoc OpenAPI
- Flyway
- PostgreSQL
- Lombok
- Auth0 Java JWT
- Hibernate Validator
- Mockito
- Rest Assured
- Testcontainers
- API Externa de Notícias

## Funcionalidades

O projeto oferece uma variedade de funcionalidades essenciais, incluindo:

- Upload e Download de Arquivos para armazenar documentos relacionados à biblioteca.
- Autenticação de Usuários para acesso seguro à aplicação.
- Envio de Email para Confirmação de Conta e Código para Mudança de Senha, garantindo a segurança das contas dos usuários.
- Consumo de API Externa de Notícias para fornecer informações atualizadas e relevantes aos usuários.
- Implementação de HATEOAS para melhorar a navegação e a descoberta dos recursos da API.
- Operações CRUD para Livros e outros recursos, permitindo a gestão eficiente da biblioteca.

## Configuração e Execução

Para configurar e executar o projeto localmente, siga estas etapas:

1. Certifique-se de ter o Java 21 instalado em seu sistema.
2. Clone este repositório em sua máquina local.
3. Configure um banco de dados PostgreSQL e atualize as credenciais no arquivo `application.properties`.
4. Adicione as informações necessárias de configuração de email para habilitar o envio de emails para confirmação de conta e recuperação de senha.
5. Execute `mvn clean install` para compilar o projeto e baixar as dependências.
6. Execute `mvn spring-boot:run` para iniciar a aplicação.
7. Acesse a API através do endpoint fornecido pela documentação do Springdoc OpenAPI.

## Documentação das APIs
- http://localhost:8090/swagger-ui/index.html
  
## Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para abrir um problema ou enviar uma solicitação pull para melhorias.

## Licença

Este projeto é licenciado sob a [Licença MIT](https://opensource.org/licenses/MIT).
