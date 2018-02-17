package com.zephyr.scraper;

import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.scraper.flow.ScrapingFlow;
import lombok.Setter;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
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
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.time.Clock;

@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(Processor.class)
public class ScraperApplication {

    @Setter(onMethod = @__(@Autowired))
    private ScrapingFlow scrapingFlow;

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .web(WebApplicationType.NONE)
                .main(ScraperApplication.class)
                .run(args);
    }

    @StreamListener
    @Output(Processor.OUTPUT)
    public Flux<SearchResultDto> receive(@Input(Processor.INPUT) Flux<QueryDto> input) {
        return scrapingFlow.handle(input);
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public AsyncHttpClient asyncHttpClient() {
        return new DefaultAsyncHttpClient();
    }
}