package com.zephyr.location;

import com.zephyr.commons.extensions.ExtendedMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@EnableCaching
@SpringBootApplication(scanBasePackages = "com.zephyr.location")
public class LocationApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocationApplication.class, args);
    }

    @Bean
    public ExtendedMapper mapper() {
        return new ExtendedMapper(new ModelMapper());
    }
}
