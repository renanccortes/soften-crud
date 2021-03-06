# Proposta de Projeto Soften Sistemas

 Criar um CRUD (Create, Read, Update and Delete) utilizando a tecnologia
Java e os frameworks JSF, PrimeFaces e Hibernate. O banco de dados a ser
utilizado pode ser o MySQL ou o MariaDB.
  
# Tecnologias usadas
  - Java 8
  - Primefaces 7.0
  - Hibernate 5.3 
  - MariaDB 10.2
  - Payara 5
  - [AdminFaces Template] 1.1.0 - Utilizado para o Layout
 

### Configuração

Altere os dados de conexão com o seu banco de dados no arquivo: src/META-INF/persistence.xml e compile o war novamente.
 ```sh
<property name="javax.persistence.jdbc.url" value="jdbc:mysql://[SEU HOST]/softenbd"/>  //O Docker cria uma interface bridge com o ip que deve se colocar
<property name="javax.persistence.jdbc.user" value="[SEU USUARIO]"/> 
<property name="javax.persistence.jdbc.password" value="[SUA SENHA]"/>
```
 
### Docker
Fácil execução implantando um container docker.

Primeiro inicie o docker do banco MariaDB:
```sh
docker run -p 3306:3306 --name soften-mariadb -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_DATABASE=softenbd -d mariadb:10.2
```

Faça o clone do repositório

 ```sh
 git clone https://github.com/renanccortes/soften-crud.git  
```

Agora acesse a página root do projeto e crie a imagem do payara com o war do projeto:
 ```sh
 cd soften-crud
docker build -t soften-crud-jsf .  
```

Agora só executar a imagem criada:
 ```sh
 docker run -p 8080:8080 --name soften-crud soften-crud-jsf
 ```

Agora o sistema deve estar acessivel pelo navegador no endereço:
 ```sh
 http://localhost:8080/
 ```
 
 Login: Administrador
 Senha: @Admin2020    

 
   [AdminFaces Template]: <https://github.com/adminfaces/admin-template>
 
