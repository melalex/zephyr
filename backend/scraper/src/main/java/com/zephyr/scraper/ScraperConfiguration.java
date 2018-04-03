package com.zephyr.scraper;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public AsyncHttpClient asyncHttpClient() {
        return new DefaultAsyncHttpClient();
    }
}
