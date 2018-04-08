package com.zephyr.rating.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.zephyr.rating")
public class RatingApplication {

    public static void main(String[] args) {
        SpringApplication.run(RatingApplication.class, args);
    }
}
