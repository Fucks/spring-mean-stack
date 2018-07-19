package br.projectmanagersoftware.config.data;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 29/10/2015
 */
@Configuration
@EnableMongoAuditing
@ComponentScan(basePackages = {"br.projectmanagersoftware.entity", "br.projectmanagersoftware.service"})
@PropertySource("classpath:application.properties")
@EnableMongoRepositories(basePackages = {"br.projectmanagersoftware.repository"})
public class PersistenceConfig extends AbstractMongoConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public Bootstrap bootstrap() {
        return new Bootstrap();
    }

    @Override
    public MongoClient mongoClient() {
//        return new MongoClient(
//                Arrays.asList(new ServerAddress(environment.getProperty("db.mongo.host"), 27017)),
//                Arrays.asList(MongoCredential.createCredential(
//                        environment.getProperty("db.mongo.username"),
//                        environment.getProperty("db.mongo.database"),
//                        environment.getProperty("db.mongo.password").toCharArray())));

        return new MongoClient(environment.getProperty("db.mongo.host"), 27017);
    }

    @Override
    protected String getDatabaseName() {
        return environment.getProperty("db.mongo.database");
    }

    @Bean
    public org.springframework.data.domain.AuditorAware<String> auditorProvider() {
        return new AuditorAware();
    }
}
