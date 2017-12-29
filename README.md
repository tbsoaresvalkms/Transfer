# API

Para verificar os resources da API, olhe aqui: [Index](doc/index.md)

##Requisitos
* Java 8+
* Lombok (necessário plugin em IDEs)
* Maven
* port 8080  (port utilizada no teste de integração)


##Componentes da Aplicação
 
* Spring Boot
* Spring Rest
* ModelMapper
* Mockito
* JUnit
* Lombok
* Maven
 
## Patterns utilizados

* Command
* Injeção de Dependência
* Chain of Responsibility
* Template Method


## Comentários

* Como existem muitas regras para definir o valor da taxa, cada uma foi abstraída para uma classe, totalizando 7 classes (Operação tipo C foi desmembrada, cada condição gerou uma nova classe). Assim foi escolhido utilizar o padrão Chain of Responsability e para determinar qual seria a próxima regra, foi utilizado o padrão Injeção de Dependência. Como todas regras apresentam um padrão na implementação - condição (se deve executar ou chamar próxima regra) e ação (como deve ser feito o cálculo) -  foi utilizado o padrão Template Method, onde obriga as regras implementarem os métodos conditional e calculate, assim somente a classe pai 'RateRule' fica responsável por executar a lógica. 

* Utilizado padrão command, onde o controller recebe as requisições e cada método chama seu próprio command (TransferCreate e TransferFindAll), assim as responsabilidades ficam isoladas.

* Lombok foi escolhido com intuito de deixar o código menos verboso.

* ModelMapper foi utilizado para conversões entre DTOs e Entidades

* Foi criado tests unitários e de integração, utilizando Mockito, JUnit e TestRestTemplate

