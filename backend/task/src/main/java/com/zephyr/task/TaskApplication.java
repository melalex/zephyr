package com.zephyr.task;

import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.commons.interfaces.UidProvider;
import com.zephyr.commons.support.DefaultUidProvider;
import org.modelmapper.ModelMapper;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class TaskApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .main(TaskApplication.class)
                .web(WebApplicationType.REACTIVE)
                .build()
                .run(args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ExtendedMapper extendedMapper() {
        return new ExtendedMapper(modelMapper());
    }

    @Bean
    public UidProvider uidProvider() {
        return new DefaultUidProvider();
    }
}
