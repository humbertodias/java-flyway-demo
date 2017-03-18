# FlyWay com Oracle

Aplicação Web com SpringBoot que através do FlyWay.

Que migra os scripts de **/db/migration** para o banco de dados Oracle.


* Altere a conexão com o banco em 
	
	**src/main/resources/application.properties** 	**src/main/java/META-INF/persistence.xml**


## Prérequisitos

1. Maven 3+
2. Java 8+
3. Oracle DB e credencial de usuário 
 
## Como rodar

1. Baixe seu projeto 
2. Descompacte
3. Entre na pasta
4. Execute 

```
mvn spring-boot:run -Dserver.port=8080

```
5. Por fim, acesse pelo navegador 

[http://localhost:8080](http://localhost:8080)

6. Confira no banco de dados se a tabela **PERSON** foi criada.

## Demonstração

[Documentação](doc/ide-template-flyway.pdf), [Demo MP4](doc/demo.mp4)


![Demostração](doc/demo.gif)



## Referências

[Instância Oracle com Docker](https://github.com/wnameless/docker-oracle-xe-11g)

[Spring Boot Reference Guide](http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/)

[SpringBoot Initializer](https://start.spring.io/)

[FlyWay Migration](https://flywaydb.org/documentation/migration/sql)
