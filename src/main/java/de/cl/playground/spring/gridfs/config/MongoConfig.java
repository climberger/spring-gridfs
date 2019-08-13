package de.cl.playground.spring.gridfs.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

    @Value( "${mongodb-uri}" )
    private String mongoDbUri;

    @Override
    public MongoClient mongoClient() {
        return new MongoClient(new MongoClientURI(mongoDbUri));
    }

    @Override
    protected String getDatabaseName() {
        return "file-store";
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }

}