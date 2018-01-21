package com.zephyr.task;

import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.data.dto.QueryDto;
import com.zephyr.task.integration.source.SearchCriteriaSource;
import com.zephyr.task.properties.TaskServiceProperties;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;

@EnableDiscoveryClient
@SpringBootApplication
@EnableBinding(Source.class)
public class TaskApplication {
    public static final String SEARCH_CRITERIA_OUTPUT = "SEARCH_CRITERIA_OUTPUT";

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ExtendedMapper extendedMapper() {
        return new ExtendedMapper(modelMapper());
    }

    @Bean
    @Autowired
    public IntegrationFlow updateRatingFlow(SearchCriteriaSource searchCriteriaSource,
                                            TaskServiceProperties properties) {
        return IntegrationFlows.from(searchCriteriaSource, s -> s.poller(Pollers.cron(properties.getCron())))
                .split()
                .channel(SEARCH_CRITERIA_OUTPUT)
                .get();
    }

    @Bean
    @Autowired
    public IntegrationFlow searchCriteriaOutputFlow(ExtendedMapper mapper) {
        return IntegrationFlows.from(SEARCH_CRITERIA_OUTPUT)
                .transform(mapper.mapperFor(QueryDto.class))
                .channel(Source.OUTPUT)
                .get();
    }
}
