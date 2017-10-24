package com.zephyr.scraper.clients;

import com.zephyr.data.dto.CountryDto;
import com.zephyr.data.dto.PlaceDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@FeignClient(name = "location-service")
public interface LocationServiceClient {

    @GetMapping(path = "/v1/country", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Flux<CountryDto> findAll();

    @GetMapping(path = "/v1/place/find", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Mono<PlaceDto> findByCountryIsoAndName(String iso, String name);
}
