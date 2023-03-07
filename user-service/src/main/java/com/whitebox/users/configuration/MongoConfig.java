package com.whitebox.users.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfig {


    private MongoDatabaseFactory mongoDatabaseFactory;
    private MongoMappingContext mongoMappingContext;

   public MongoConfig(MongoDatabaseFactory mongoDatabaseFactory, MongoMappingContext mongoMappingContext) {
       this.mongoDatabaseFactory = mongoDatabaseFactory;
       this.mongoMappingContext = mongoMappingContext;
   }

   @Bean
    public MappingMongoConverter mappingMongoConverter() {
       DefaultDbRefResolver defaultDbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
       MappingMongoConverter mappingMongoConverter = new MappingMongoConverter(defaultDbRefResolver, mongoMappingContext);
       mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
       return mappingMongoConverter;
   }
}
