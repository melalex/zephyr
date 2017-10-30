package com.zephyr.scraper;

import com.zephyr.data.Keyword;
import com.zephyr.data.dto.SearchResultDto;
import com.zephyr.scraper.flow.ScrapingFlow;
import com.zephyr.scraper.flow.impl.ScrapingFlowImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@Slf4j
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(Processor.class)
public class ScraperApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScraperApplication.class, args);
    }

    @StreamListener
    @Output(Processor.OUTPUT)
    public Flux<SearchResultDto> receive(@Input(Processor.INPUT) Flux<Keyword> input) {
        return flow().handle(input);
    }

    @Bean
    @RefreshScope
    public ScrapingFlow flow() {
        return new ScrapingFlowImpl();
    }
}