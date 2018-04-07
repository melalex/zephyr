package com.zephyr.keyword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class KeywordApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeywordApplication.class, args);
    }
}