package com.zephyr.task.stream;

import com.zephyr.task.stream.gateways.PlaceTaskGateway;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.reactive.StreamEmitter;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlows;
import reactor.core.publisher.Flux;

@EnableBinding(Source.class)
public class StreamConfiguration {

    @Bean
    @StreamEmitter
    @Output(Source.OUTPUT)
    public Flux<String> taskFlow() {
        return IntegrationFlows.from(PlaceTaskGateway.class)
                .toReactivePublisher();
    }
}
