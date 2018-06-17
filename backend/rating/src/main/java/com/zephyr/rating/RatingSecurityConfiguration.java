package com.zephyr.rating;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class RatingSecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        @formatter:off
        return http
                .authorizeExchange()
                    .pathMatchers("/v1/rating").permitAll()
                    .anyExchange().permitAll()
                    .and()
                .csrf().disable()
                .build();
 //        @formatter:on
    }
}
