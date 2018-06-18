package com.zephyr.location;

import com.zephyr.commons.extensions.ExtendedMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@EnableCaching
@SpringBootApplication
@EnableNeo4jRepositories
public class LocationApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocationApplication.class, args);
    }

    @Bean
    public ExtendedMapper mapper(ModelMapper modelMapper) {
        return new ExtendedMapper(modelMapper);
    }
}
