# ■ Estoque Fábrica – Otimização de Produção
Sistema para gerenciamento de matérias-primas e produtos industriais com foco em otimização de p## ■ Objetivo
Dado um conjunto de matérias-primas disponíveis em estoque, o sistema analisa os produtos cadast## ■ Stack Tecnológica
- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- JUnit 5
- Mockito
## ■ Modelo de Domínio
Matéria-Prima:
- id (UUID)
- nome
- quantidadeEmEstoque
Produto:
- id (UUID)
- nome
- valor
- composição (lista de matérias-primas + quantidade necessária)
Relacionamento:
Produto 1:N ProdutoMateriaPrima
Matéria-Prima 1:N ProdutoMateriaPrima
## ■■ Funcionalidades
CRUD Matéria-Prima:
- POST /materias-primas
- GET /materias-primas
- PUT /materias-primas/{id}
- DELETE /materias-primas/{id}
  
CRUD Produto:
- POST /produtos
- GET /produtos
- DELETE /produtos/{id}
## ■ Otimização de Produção
Endpoint:
GET /producao/otimizar

Estratégia:
1. Ordena produtos por maior valor
2. Calcula o máximo de unidades possíveis por gargalo de matéria-prima
3. Atualiza estoque virtual
4. Retorna plano de produção + valor total
Exemplo de resposta:
{
 "produtos": {
 "Produto A": 10
 },
 "valorTotal": 1000.0
}
## ■ Testes
- Teste unitário da lógica de otimização
- Cenário com disputa de matéria-prima
- Validação de priorização por maior valor
- Uso de JUnit 5 + Mockito
## ■■ Como Executar
1. Criar banco:
CREATE DATABASE estoqueMP;
2. Configurar application.yml
3. Executar:
./mvnw spring-boot:run
## ■ Estrutura do Projeto
controller
service
repository
entity
dto
## ■■■ Autor
Jeferson Pinheiro de Jesus
