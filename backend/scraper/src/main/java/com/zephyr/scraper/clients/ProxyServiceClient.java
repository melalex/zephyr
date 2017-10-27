package com.zephyr.scraper.clients;

import com.zephyr.data.dto.ProxyDto;
import com.zephyr.data.enums.SearchEngine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import reactor.core.publisher.Mono;

@FeignClient(name = "proxy-service")
public interface ProxyServiceClient {

    @PutMapping(path = "/reserve", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Mono<ProxyDto> reserve(SearchEngine engine);

    @PutMapping(path = "/report")
    Mono<Void> report(String id, SearchEngine engine);
}