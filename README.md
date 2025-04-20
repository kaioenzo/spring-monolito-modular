# Projeto Conta Bancária com Spring Modulith

## Sobre o Projeto

Este projeto é uma aplicação de conta bancária desenvolvida com o objetivo de estudar e implementar conceitos de arquitetura modular utilizando o Spring Modulith. A aplicação gerencia operações bancárias como saques, depósitos e transferências, seguindo uma abordagem de design modular.

## O que é Spring Modulith?

Spring Modulith é uma extensão do ecossistema Spring que oferece suporte para o desenvolvimento de aplicações modulares. Ele proporciona:

- **Organização Modular**: Estrutura seu código em módulos bem definidos e desacoplados
- **Fronteiras Explícitas**: Define claramente as interfaces entre módulos
- **Testes Específicos de Módulo**: Facilita o teste de módulos isoladamente
- **Documentação Automatizada**: Permite visualizar e documentar a arquitetura modular da aplicação

## Estrutura do Projeto

O projeto segue uma organização modular, com os seguintes módulos principais:

- **Módulo Conta**: Gerencia entidades e lógica de negócios relacionadas às contas bancárias
- **Módulo Operação Bancária**: Lida com transações como saques, depósitos e transferências
- **Módulo Cliente**: Gerencia informações e operações relacionadas aos clientes

## Tecnologias Utilizadas

- Java 24
- Spring Boot
- Spring Modulith
- Spring Data JPA
- Spring MVC
- Lombok
- Jakarta EE
- Maven

## Benefícios da Abordagem Modular

- **Manutenção Simplificada**: Mudanças em um módulo têm impacto mínimo em outros
- **Desenvolvimento Paralelo**: Times podem trabalhar em módulos diferentes simultaneamente
- **Testabilidade Aprimorada**: Testes mais focados e eficientes por módulo
- **Compreensão Facilitada**: Estrutura clara e organizada do código
- **Evolução Gradual**: Possibilidade de evoluir ou substituir módulos independentemente

## Como Executar

1. Clone o repositório
2. Certifique-se de ter o JDK 24 e Maven instalados
3. Configure o banco de dados PostgreSQL (veja a seção abaixo)
4. Execute `mvn clean install` para construir o projeto
5. Execute `mvn spring-boot:run` para iniciar a aplicação
6. A aplicação estará disponível em `http://localhost:8080`

## Configuração do PostgreSQL

### Usando Docker (Recomendado)

O projeto inclui um arquivo `docker-compose.yml` para facilitar a configuração do PostgreSQL:

1. Certifique-se de ter o Docker e Docker Compose instalados
2. No diretório raiz do projeto, execute:

Para executar os testes de todos os módulos:
