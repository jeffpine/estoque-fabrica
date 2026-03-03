# Estoque Fábrica – Otimização de Produção

## Objetivo
Dado um conjunto de matérias-primas disponíveis em estoque, o sistema analisa os produtos cadastrados e calcula automaticamente qual plano de produção gera maior valor financeiro respeitando as restrições de insumos disponíveis.

## Stack Tecnológica

### Backend
- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- JUnit 5
- Mockito

### Frontend
- Vue 3
- Vite
- Axios
- Tailwind CSS

## Modelo de Domínio

**Matéria-Prima:** id (UUID), nome, quantidadeEmEstoque

**Produto:** id (UUID), nome, valor, composicao (lista de matérias-primas + quantidade necessária)

## Funcionalidades Backend

- POST /materias-primas - Cadastrar nova matéria-prima
- GET /materias-primas - Listar todas as matérias-primas
- POST /produtos - Cadastrar novo produto
- GET /produtos - Listar todos os produtos
- PUT /produtos - Editar um produto
- DELETE /produtos/deletar/{nome} - Remover um produto

## Funcionalidades Frontend

1. Cadastro e listagem de matérias-primas
2. Cadastro de produtos com composição
3. Edição de produtos diretamente na interface
4. Remoção de produtos
5. Geração de plano de produção manual
6. Geração de plano de produção automático otimizado

## Otimização de Produção

### Endpoints:
GET /producao/otimizar
POST /producao/manual

### Estratégia utilizada:
1. Ordena produtos por maior valor
2. Calcula máximo de unidades possíveis por gargalo de matéria-prima
3. Atualiza estoque virtual
4. Retorna plano de produção + valor total

## Como Executar o Backend

1. Criar o banco de dados PostgreSQL:
   CREATE DATABASE estoqueMP;

2. Executar a aplicação:
   ./mvnw spring-boot:run

API disponível em: http://localhost:8080

## Como Executar o Frontend

npm install  
npm run dev

Frontend disponível em: http://localhost:5173

## Estrutura do Projeto

Backend/
  controller/
  service/
  repository/
  domain/
  dto/
  config/
  exception/

Frontend/
  src/
    api/
    views/
      ProdutosView.vue
      MateriasPrimasView.vue
      ProducaoView.vue
    router/
    main.js

## Autor

Jeferson Pinheiro de Jesus

LinkedIn: https://www.linkedin.com/in/eijeffpinheiro/
