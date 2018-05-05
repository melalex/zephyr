package com.zephyr.task;

import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.commons.interfaces.UidProvider;
import com.zephyr.commons.support.DefaultUidProvider;
import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.task.integration.gateways.NewCriteriaGateway;
import com.zephyr.task.services.ConfigurationService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.HeaderEnricherSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableBinding(Source.class)
public class TaskApplication {

    private static final String PRIORITY_HEADER = "priority";
    private static final String NEW_CRITERIA_PRIORITY = "9";
    private static final String UPDATE_RATING_PRIORITY = "1";

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
    public UidProvider uidProvider() {
        return new DefaultUidProvider();
    }

    @Bean
    @RefreshScope
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
        return IntegrationFlows.from(NewCriteriaGateway.class)
                .enrichHeaders(setPriority(NEW_CRITERIA_PRIORITY))
                .channel(Source.OUTPUT)
                .get();
    }

    private Consumer<HeaderEnricherSpec> setPriority(String priority) {
        return h -> h.header(PRIORITY_HEADER, priority);
    }
}
