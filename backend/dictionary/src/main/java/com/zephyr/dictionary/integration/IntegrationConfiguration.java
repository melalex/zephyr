package com.zephyr.dictionary.integration;

import com.zephyr.dictionary.integration.gateways.NewKeywordGateway;
import com.zephyr.dictionary.integration.gateways.UpdateRatingGateway;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;

@Configuration
@EnableIntegration
public class IntegrationConfiguration {
    private static final String PRIORITY_HEADER = "priority";
    private static final Integer UPDATE_PRIORITY = 1;
    private static final Integer REQUEST_PRIORITY = 9;

    @Bean
    @RefreshScope
    public IntegrationFlow updateRatingFlow() {
        return IntegrationFlows.from(UpdateRatingGateway.class)
                .transform(Transformers.toJson())
                .enrichHeaders(h -> h.header(PRIORITY_HEADER, UPDATE_PRIORITY))
//                .handle(Amqp.outboundAdapter(rabbitConfig.worksRabbitTemplate()))
                .get();
    }

    @Bean
    public IntegrationFlow requestRatingFlow() {
        return IntegrationFlows.from(NewKeywordGateway.class)
                .transform(Transformers.toJson())
                .enrichHeaders(h -> h.header(PRIORITY_HEADER, REQUEST_PRIORITY))
//                .handle(Amqp.outboundAdapter(rabbitConfig.worksRabbitTemplate()))
                .get();
    }
}
