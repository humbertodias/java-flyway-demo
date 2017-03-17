package app;

import auth.permission.SecurityPermission;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.File;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;


import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


/**
 * Classe que configura os beans para persistencia
 * @generated
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "app-EntityManagerFactory",
        transactionManagerRef = "app-TransactionManager"
)
class AppConfiguration {


    @Primary

    @Bean(name="app-EntityManagerFactory")
    public LocalEntityManagerFactoryBean entityManagerFactory() {
        LocalEntityManagerFactoryBean factoryBean = new LocalEntityManagerFactoryBean();
        factoryBean.setPersistenceUnitName("app");
        return factoryBean;
    }

    @Bean(name = "app-TransactionManager")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }
    @Bean
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {
  
    //Criando dinamicamente os dados do App

    Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
    URL url = this.getClass().getClassLoader().getResource("app//populate.json");

    String strJSON = "[]";
    if (url != null) {
      File file = new File(url.getFile());

      try {
        Scanner scanner = new Scanner(file);
        strJSON = scanner.useDelimiter("\\A").next();
        scanner.close();
        strJSON = strJSON.replaceAll(Pattern.quote("{{ROLE_ADMIN_NAME}}"), SecurityPermission.ROLE_ADMIN_NAME);
    
      } catch (Exception e) {
      }
    }
    
    Resource sourceData = new InputStreamResource(new java.io.ByteArrayInputStream(strJSON.getBytes()));
    factory.setResources(new Resource[] { sourceData });

    return factory;
  
    }

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;

    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource());
        flyway.setBaselineOnMigrate(true);
        return flyway;
    }
  
}