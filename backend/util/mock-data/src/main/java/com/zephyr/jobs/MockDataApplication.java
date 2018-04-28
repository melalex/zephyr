package com.zephyr.jobs;

import com.github.javafaker.Faker;
import com.zephyr.jobs.properties.MockDataProperties;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@EnableTask
@EnableBatchProcessing
@SpringBootApplication
public class MockDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockDataApplication.class, args);
    }

    @Bean
    public Faker faker(MockDataProperties properties) {
        return Faker.instance(properties.getLocale());
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
