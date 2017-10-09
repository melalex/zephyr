package com.zephyr.dictionary.integration;

import com.zephyr.commons.data.Keyword;
import com.zephyr.dictionary.integration.gateways.NewKeywordGateway;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import reactor.core.publisher.Flux;

@Configuration
@EnableIntegration
public class IntegrationConfiguration {

    @Bean
    @RefreshScope
    public IntegrationFlow updateRatingFlow(IntegrationProperties properties, MessageSource<Flux<Keyword>> source) {
        return IntegrationFlows.from(source, s -> s.poller(p -> p.cron(properties.getCron())))
                .get();
    }

    @Bean
    public IntegrationFlow requestRatingFlow() {
        return IntegrationFlows.from(NewKeywordGateway.class)
                .get();
    }
}
