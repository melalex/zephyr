package com.zephyr.scraper;

import com.zephyr.data.Keyword;
import com.zephyr.data.SearchResult;
import com.zephyr.scraper.crawler.DocumentCrawler;
import com.zephyr.scraper.loader.PageLoader;
import com.zephyr.scraper.query.QueryConstructor;
import com.zephyr.scraper.task.TaskConverter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import reactor.core.publisher.Flux;

@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(Processor.class)
public class ScraperApplication {

    @Setter(onMethod = @__(@Autowired))
    private TaskConverter taskConverter;

    @Setter(onMethod = @__(@Autowired))
    private QueryConstructor queryConstructor;

    @Setter(onMethod = @__(@Autowired))
    private PageLoader pageLoader;

    @Setter(onMethod = @__(@Autowired))
    private DocumentCrawler documentCrawler;

    public static void main(String[] args) {
        SpringApplication.run(ScraperApplication.class, args);
    }

    @StreamListener
    @Output(Processor.OUTPUT)
    public Flux<SearchResult> receive(@Input(Processor.INPUT) Flux<Keyword> input) {
        return input
                .flatMap(k -> taskConverter.convert(k))
                .flatMap(k -> queryConstructor.construct(k))
                .parallel()
                .flatMap(r -> pageLoader.load(r))
                .map(d -> documentCrawler.crawl(d))
                .sequential();
    }
}