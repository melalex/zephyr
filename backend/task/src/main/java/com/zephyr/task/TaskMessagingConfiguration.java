package com.zephyr.task;

import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.task.services.ConfigurationService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.HeaderEnricherSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.channel.MessageChannels;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Configuration
@EnableBinding(Source.class)
public class TaskMessagingConfiguration {

    private static final String PRIORITY_HEADER = "priority";
    private static final int NEW_CRITERIA_PRIORITY = 9;
    private static final int UPDATE_RATING_PRIORITY = 1;

    @Bean
    @ConditionalOnProperty(value = "task.enableUpdates", havingValue = "true")
    public IntegrationFlow updateRatingFlow(MessageSource<Flux<QueryDto>> querySource,
                                            ConfigurationService configurationService) {
        return IntegrationFlows.from(querySource, s -> s.poller(Pollers.cron(configurationService.getCron())))
                .split()
                .enrichHeaders(setPriority(UPDATE_RATING_PRIORITY))
                .channel(Source.OUTPUT)
                .get();
    }

    @Bean
    public IntegrationFlow newSearchCriteriaFlow() {
        return IntegrationFlows.from(MessageChannels.direct("newSearchCriteriaFlow.input"))
                .enrichHeaders(setPriority(NEW_CRITERIA_PRIORITY))
                .channel(Source.OUTPUT)
                .get();
    }

    private Consumer<HeaderEnricherSpec> setPriority(int priority) {
        return h -> h.header(PRIORITY_HEADER, priority);
    }

}
