package com.zephyr.scraper;

import com.zephyr.data.Keyword;
import com.zephyr.data.SearchResult;
import com.zephyr.scraper.crawler.DocumentCrawler;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.domain.Task;
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
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(Processor.class)
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class ScraperApplication {

    @Setter(onMethod = @__(@Autowired))
    private TaskConverter taskConverter;

    @Setter(onMethod = @__(@Autowired))
    private QueryConstructor constructor;

    @Setter(onMethod = @__(@Autowired))
    private PageLoader loader;

    @Setter(onMethod = @__(@Autowired))
    private DocumentCrawler crawler;

    public static void main(String[] args) {
        SpringApplication.run(ScraperApplication.class, args);
    }

    @StreamListener
    @Output(Processor.OUTPUT)
    public Flux<SearchResult> receive(@Input(Processor.INPUT) Flux<Keyword> input) {
        return input
                .flatMap(toTask())
                .flatMap(constructQueries())
                .flatMap(loadPages())
                .map(toSearchResult());
    }

    @Bean
    public Function<Keyword, Mono<Task>> toTask() {
        return k -> taskConverter.convert(k);
    }

    @Bean
    public Function<Task, Flux<Request>> constructQueries() {
        return k -> constructor.construct(k);
    }

    @Bean
    public Function<Request, Mono<Response>> loadPages() {
        return r -> loader.load(r);
    }

    @Bean
    public Function<Response, SearchResult> toSearchResult() {
        return d -> crawler.crawl(d);
    }
}