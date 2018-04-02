package com.zephyr.scraper;

import com.zephyr.commons.LoggingUtils;
import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.scraper.browser.Browser;
import com.zephyr.scraper.request.RequestConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import reactor.core.publisher.Flux;

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


    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .web(WebApplicationType.NONE)
                .main(ScraperApplication.class)
                .run(args);
    }

    @StreamListener
    @Output(Processor.OUTPUT)
    public Flux<SearchResultDto> receive(@Input(Processor.INPUT) Flux<QueryDto> input) {
        return input.doOnNext(LoggingUtils.info(log, NEW_QUERY_MSG))
                .flatMap(requestConstructor::construct)
                .parallel()
                .flatMap(browser::get)
                .sequential()
                .doOnNext(LoggingUtils.debug(log, RECEIVED_SEARCH_RESULT_MSG))
                .doOnError(LoggingUtils.error(log, UNEXPECTED_EXCEPTION_MSG));
    }
}