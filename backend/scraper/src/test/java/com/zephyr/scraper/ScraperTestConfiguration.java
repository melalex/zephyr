package com.zephyr.scraper;

import com.zephyr.commons.interfaces.UidProvider;
import com.zephyr.scraper.browser.support.WebClientWrapper;
import com.zephyr.scraper.mocks.WebClientMock;
import com.zephyr.test.mocks.TimeMachine;
import com.zephyr.test.mocks.UidProviderMock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import java.time.Clock;

@TestConfiguration
public class ScraperTestConfiguration {

    @Bean
    @Primary
    public UidProvider uidProvider() {
        return UidProviderMock.of();
    }

    @Bean
    public TimeMachine timeMachine() {
        return TimeMachine.create();
    }

    @Bean
    @Primary
    public Clock clock() {
        return timeMachine().clock();
    }

    @Bean
    @Lazy
    @Primary
    public WebClientWrapper webClientWrapper() {
        return WebClientMock.of();
    }
}
