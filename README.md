# PARQUIMETRO

- Este documento demonstra como configurar e testar uma aplicação em Java com o MongoDB Local e Kafka utilizando Docker.
- Antes de iniciar utilize o docker-compose para subir o ambiente, KafkaDrop para ver os tópicos, ZooKeeper, MongoDB e Redis. Todos dentro do container parquimetro.
- O Kafka Drop utilizamos para visualizar os tópicos pelo navegador. (http://localhost:9000/)
- URL para utilizar no postman e startar a aplicação (POST e o BODY): http://localhost:8080/
- As variáveis de ambiente estão na pasta env.
- As Collections de todas as requisições estão na pasta collections.
- Swagger: http://localhost:8080/swagger-ui.html

# DICAS

### URI MongoDB: <br>
    mongodb://parquimetrousr:Parquimetro2024@localhost:27017

### URI Kafka Drop: <br>
    http://localhost:9000/

# ERRO DE AUTENTICAÇÃO
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

