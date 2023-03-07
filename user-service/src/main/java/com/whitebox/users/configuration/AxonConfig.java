package com.whitebox.users.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoFactory;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Value("${spring.data.mongodb.host:127.0.0.1}")
    private String host;
    @Value("${spring.data.mongodb.port:27017}")
    private String port;

    @Value("${spring.data.mongodb.database:users}")
    private String database;


    @Bean
    public MongoClient mongoClient() {
        var mongoFactory = new MongoFactory();
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(new ConnectionString("mongodb://" + host + ":" + port)).build();
        mongoFactory.setMongoClientSettings(mongoClientSettings);
        return mongoFactory.createMongo();
    }

    public MongoTemplate mongoTemplate() {
        return DefaultMongoTemplate.builder().mongoDatabase(mongoClient(), database).build();
    }

    @Bean
    public TokenStore tokenStore(Serializer serializer) {
        return MongoTokenStore.builder().mongoTemplate(mongoTemplate()).serializer(serializer).build();
    }

    @Bean
    public EventStorageEngine eventStorageEngine(MongoClient mongoClient) {
        return MongoEventStorageEngine.builder().mongoTemplate(DefaultMongoTemplate.builder().mongoDatabase(mongoClient).build()).build();
    }

    @Bean
    public EmbeddedEventStore embeddedEventStore(EventStorageEngine storageEngine) {
        return EmbeddedEventStore.builder().storageEngine(storageEngine).build();
    }
}
