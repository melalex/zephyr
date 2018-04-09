package com.zephyr.scraper;

import com.zephyr.commons.interfaces.UidProvider;
import com.zephyr.test.mocks.ClockMock;
import com.zephyr.test.mocks.UidProviderMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Clock;

@Configuration
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
}
