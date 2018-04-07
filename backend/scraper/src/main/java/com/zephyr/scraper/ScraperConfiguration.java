package com.zephyr.scraper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Clock;

@Configuration
public class ScraperConfiguration {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public Scheduler scheduler() {
        return Schedulers.elastic();
    }

    @Bean
    public WebClient asyncHttpClient() {
        return WebClient.create();
    }
}
