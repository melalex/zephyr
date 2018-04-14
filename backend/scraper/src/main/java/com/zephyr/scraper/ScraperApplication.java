package com.zephyr.scraper;

import com.zephyr.commons.LoggingUtils;
import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.commons.interfaces.UidProvider;
import com.zephyr.commons.support.DefaultUidProvider;
import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.scraper.browser.Browser;
import com.zephyr.scraper.domain.Query;
import com.zephyr.scraper.request.RequestConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Clock;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(Processor.class)
public class ScraperApplication {

    private static final String NEW_QUERY_MSG = "Received new Query: {}";
    private static final String RECEIVED_SEARCH_RESULT_MSG = "Received SearchResult: {}";
    private static final String UNEXPECTED_EXCEPTION_MSG = "Unexpected exception";

    @Setter(onMethod = @__(@Autowired))
    private RequestConstructor requestConstructor;

    @Setter(onMethod = @__(@Autowired))
    private Browser browser;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .web(WebApplicationType.NONE)
                .main(ScraperApplication.class)
                .run(args);
    }

    @SendTo(Processor.OUTPUT)
    @StreamListener(Processor.INPUT)
    public Flux<SearchResultDto> receive(Flux<QueryDto> input) {
        return input.doOnNext(LoggingUtils.info(log, NEW_QUERY_MSG))
                .map(mapper.mapperFor(Query.class))
                .flatMap(requestConstructor::construct)
                .parallel()
                .flatMap(browser::get)
                .sequential()
                .doOnNext(LoggingUtils.debug(log, RECEIVED_SEARCH_RESULT_MSG))
                .doOnError(LoggingUtils.error(log, UNEXPECTED_EXCEPTION_MSG));
    }

    @Bean
    public UidProvider uidProvider() {
        return new DefaultUidProvider();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public Scheduler scheduler() {
        return Schedulers.elastic();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ExtendedMapper extendedMapper() {
        return new ExtendedMapper(modelMapper());
    }
}