package com.zephyr.dictionary.integration;

import com.zephyr.data.commons.Keyword;
import com.zephyr.dictionary.integration.gateways.NewKeywordGateway;
import com.zephyr.dictionary.integration.gateways.UpdateRatingGateway;
import org.reactivestreams.Publisher;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.reactive.StreamEmitter;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.Message;

@EnableBinding(Source.class)
public class IntegrationConfiguration {
    private static final String PRIORITY_HEADER = "priority";
    private static final Integer UPDATE_PRIORITY = 1;
    private static final Integer REQUEST_PRIORITY = 9;

    @Bean
    @StreamEmitter
    @Output(Source.OUTPUT)
    public Publisher<Message<Keyword>> updateRatingFlow() {
        return IntegrationFlows.from(UpdateRatingGateway.class)
                .enrichHeaders(h -> h.header(PRIORITY_HEADER, UPDATE_PRIORITY))
                .toReactivePublisher();
    }

    @Bean
    @StreamEmitter
    @Output(Source.OUTPUT)
    public Publisher<Message<Keyword>> requestRatingFlow() {
        return IntegrationFlows.from(NewKeywordGateway.class)
                .enrichHeaders(h -> h.header(PRIORITY_HEADER, REQUEST_PRIORITY))
                .toReactivePublisher();
    }
}