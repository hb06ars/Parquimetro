# PARQUIMETRO

### REGRAS
- Regras de tarifa: no parquímetro, caso o período seja menor ou igual a 30 minutos, então será cobrado apenas a metade da tarifa.
- Após os primeiros 30min, o valor da tarifa é cobrado pela hora.
- Exemplo 1: se a tarifa for igual a R$20,00/hora : E o veículo permanecer por 29 minutos -> será cobrado R$10,00.
- Exemplo 2: se a tarifa for igual a R$20,00/hora : E o veículo permanecer por 31 minutos -> será cobrado R$20,00.
- Exemplo 3: se a tarifa for igual a R$20,00/hora : E o veículo permanecer por 1h01min -> será cobrado R$40,00.

### ARQUITETURA
Linguagem: Java 17
Framework: Spring Boot
Banco de dados: MongoDB e Redis

### FLUXO
- A persistência de dados é feita da seguinte forma:
- Uma requisição POST é enviada com o objeto via RequestBody.
O sistema identifica se no cache há algum veículo salvo com aquela placa, caso não haja nenhum veículo ele irá gerar um  número de processo com o padrão: anomesdiahoraminutosegundoplaca -> Exemplo: 20241109205011ASD1234.
O sistema irá preencher este DTO.
- Após preenchido é enviado uma mensagem pelo Kafka e em seguida será retornado o DTO que foi preenchido para o usuário, fazendo com que o usuário não fique aguardando o registro ser salvo.
Obs: Não é póssível gravar um registro com a mesma placa se houver alguma pendência de pagamento.
- O consumer dessa mensagem é feito e persistido no MongoDB e persistido no cache para facilitar a consulta do mesmo.

- Quando o usuário for buscar seu veículo, basta informar o número da placa.
A requisição é feita por um GET com RequestParamter passando o número da placa, no qual será calculado e retornado o objeto com valor que ele deve pagar atualizado.

- Quando o usuário clicar em Pagar, será chamado uma requisição via POST com RequestParamter passando o número da placa.
Nela é feito o calculo, é atualizado no MongoDB com status = PAGO e o registro é removido do cache.

- Pode ser testado subindo o Docker, em seguida o projeto e acessando no seu navegador pela url: http://localhost:8080/index.html


### INSTALAÇÃO
- Este documento demonstra como configurar e testar uma aplicação em Java com o MongoDB Local e Kafka utilizando Docker.
- Antes de iniciar utilize o docker-compose para subir o ambiente, KafkaDrop para ver os tópicos, ZooKeeper, MongoDB e Redis. Todos dentro do container parquimetro.
- O Kafka Drop utilizamos para visualizar os tópicos pelo navegador. (http://localhost:9000/)
- URL para utilizar no postman e startar a aplicação (POST e o BODY): http://localhost:8080/
- Cache: pode ser utilizado o Redis Insight conectando com a url: 127.0.0.1:6379
- Swagger: http://localhost:8080/swagger-ui.html
- Teste da Aplicação: http://localhost:8080/index.html
- As variáveis de ambiente estão na pasta env.
- As Collections de todas as requisições estão na pasta collections.
- Para testar, pode utilizar o index.html dentro de /resources/static

# DICAS

### URI MongoDB: <br>
    mongodb://parquimetrousr:Parquimetro2024@localhost:27017

### URI Kafka Drop: <br>
    http://localhost:9000/

### ERRO DE AUTENTICAÇÃO
    Caso haja algum erro de persistência no Mongo, pode ocorrer devido a permissão de salvar:
    Abra o terminal do Mongo, para testar: mongo --version
    Execute os comandos:
    mongo --host localhost:27017
    
    - Com autenticação:
        use admin
        db.createUser({
        user: "yourUsername",
        pwd: "yourPassword",
        roles: [ { role: "readWrite", db: "yourDatabaseName" } ]
        });

    - Sem autenticação:
        mongod --noauth

