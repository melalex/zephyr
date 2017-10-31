package com.zephyr.scraper.flow.impl;

import com.zephyr.data.commons.Keyword;
import com.zephyr.data.dto.SearchResultDto;
import com.zephyr.scraper.crawler.DocumentCrawler;
import com.zephyr.scraper.flow.ScrapingFlow;
import com.zephyr.scraper.loader.PageLoader;
import com.zephyr.scraper.query.QueryConstructor;
import com.zephyr.scraper.task.TaskConverter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

@Slf4j
public class ScrapingFlowImpl implements ScrapingFlow {

    @Setter(onMethod = @__(@Autowired))
    private TaskConverter taskConverter;

    @Setter(onMethod = @__(@Autowired))
    private QueryConstructor queryConstructor;

    @Setter(onMethod = @__(@Autowired))
    private PageLoader pageLoader;

    @Setter(onMethod = @__(@Autowired))
    private DocumentCrawler documentCrawler;

    @Override
    public Flux<SearchResultDto> handle(Flux<Keyword> input) {
        return input
                .doOnNext(k -> log.info("Received new Keyword: {}", k))
                .flatMap(k -> taskConverter.convert(k))
                .doOnNext(t -> log.info("Converted Keyword to TaskDto: {}", t))
                .flatMap(t -> queryConstructor.construct(t))
                .doOnNext(r -> log.info("Request constructed: {}", r))
                .parallel()
                .flatMap(r -> pageLoader.load(r))
                .doOnNext(r -> log.info("Finished loading pages for TaskDto {} and Engine {}", r.getTask().getId(), r.getProvider()))
                .map(d -> documentCrawler.crawl(d))
                .doOnNext(r -> log.info("Finished crawling result for Keyword {} and Engine {}", r.getKeyword(), r.getProvider()))
                .sequential()
                .doOnError(t -> log.error("Unhandled exception", t));
    }
}
