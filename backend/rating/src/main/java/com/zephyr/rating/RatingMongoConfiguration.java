package com.zephyr.rating;

import org.bson.BsonTimestamp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableReactiveMongoRepositories
public class RatingMongoConfiguration {

    @Bean
    public MongoCustomConversions customConversions(Converter<BsonTimestamp, LocalDateTime> converter) {
        return new MongoCustomConversions(List.of(converter));
    }
}
