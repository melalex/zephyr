package com.zephyr.task.integration;

import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.data.dto.QueryDto;
import com.zephyr.task.integration.source.SearchCriteriaSource;
import com.zephyr.task.properties.TaskServiceProperties;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;

@EnableBinding(Source.class)
public class IntegrationConfiguration {
    public static final String SEARCH_CRITERIA_OUTPUT = "SEARCH_CRITERIA_OUTPUT";

    @Setter(onMethod = @__(@Autowired))
    private SearchCriteriaSource searchCriteriaSource;

    @Setter(onMethod = @__(@Autowired))
    private TaskServiceProperties properties;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Bean
    public IntegrationFlow updateRatingFlow() {
        return IntegrationFlows.from(searchCriteriaSource, s -> s.poller(Pollers.cron(properties.getCron())))
                .split()
                .transform(mapper.mapperFor(QueryDto.class))
                .channel(SEARCH_CRITERIA_OUTPUT)
                .get();
    }

    @Bean
    public IntegrationFlow searchCriteriaOutputFlow() {
        return IntegrationFlows.from(SEARCH_CRITERIA_OUTPUT)
                .transform(mapper.mapperFor(QueryDto.class))
                .channel(Source.OUTPUT)
                .get();
    }
}