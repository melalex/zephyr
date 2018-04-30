package com.zephyr.scraper;

import com.zephyr.commons.interfaces.UidProvider;
import com.zephyr.scraper.mocks.WebClientMock;
import com.zephyr.test.mocks.ClockMock;
import com.zephyr.test.mocks.UidProviderMock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Clock;

@TestConfiguration
public class ScraperTestConfiguration {

    @Bean
    @Primary
    public UidProvider uidProvider() {
        return UidProviderMock.of();
    }

    @Bean
    @Primary
    public Clock clock() {
        return ClockMock.of();
    }

    @Bean
    @Lazy
    @Primary
    public WebClient webClient() {
        return WebClientMock.of();
    }
}
