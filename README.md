# desafio-sicredi

## Respostas sobre as tarefas

#### Bônus 1
A aplicação não se encontra mais disponível

#### Bônus 2
Implementado

#### Bônus 3
Testes de carga não executados

#### Bônus 4
O versionamento funcionaria via endpoint, ou seja, toda feature adicionada nasceria com o prefixo /v1/ e conforme houvesse necessidade de modificar os recursos o endpoint com a nova versão seria disponibilizado na próxima versão e após um tempo para migração de versão do endpoint, os endpoints antigos seriam desligados.

#### Explicação breve das escolhas tomadas durante o desenvolvimento
- Optei por utilizar o postgres por ser um banco gratuito, simples de se trabalhar e bastante familiar para mim
- Sobre a arquitetura optei pela separação de camadas controller/service/mapper/repository para facilitar o desenvolvimento dos métodos e possibilitar um fluxo mais fluido como por exemplo um fluxo de criação onde não é necessário verificar se o usuário está tentando salvar uma entidade já existente, pois o fluxo passa pelo mapper e ignora o id
- Para a conclusão da sessão de votação e envio do evento conslusão de sessão de votação, optei por utilizar um Scheduler pois é uma solução simples e não complexa, as outras possibilidades em que pensei criariam muito mais código e trariam uma complexidade desnecessária
- Para mensageria optei por utilizar o Kafka que é uma solução altamente escalável e muito simples de implementar
- Em relação aos testes automatizados optei por fazer somente em algumas partes de negócio do projeto e somente testes unitários, porém, seria uma boa melhoria adicionar testes de integração tanto para os endpoints quanto para a produção e consumo da mensageria

## Como rodar a aplicação local

### Pré-requisitos
- Docker
- Java 17

### Subir os containeres de dependências
`docker compose up`

### Rodar a aplicação
`./gradlew bootRun`