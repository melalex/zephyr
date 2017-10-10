package com.zephyr.errors;

import com.zephyr.errors.converters.ProblemConverter;
import com.zephyr.errors.handlers.WebFluxExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebExceptionHandler;

@Configuration
@ComponentScan(basePackageClasses = ProblemConverter.class)
public class ErrorConfiguration {

    @Bean
    public WebExceptionHandler webExceptionHandler() {
        return new WebFluxExceptionHandler();
    }
}
