package com.zephyr.scraper.clients;

import com.zephyr.data.criteria.ProxyCriteria;
import com.zephyr.data.resources.CountryResource;
import com.zephyr.data.resources.ProxyResource;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@FeignClient(name = "location-service")
public interface LocationServiceClient {

    // TODO: Rewrite when Feign starts support reactor

    @GetMapping(value = "/v1/proxy/search", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Mono<PagedResources<ProxyResource>> findProxyByCriteria(ProxyCriteria criteria, Pageable pageable);

    @GetMapping(value = "/v1/country/{iso}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Mono<CountryResource> findCountryByIso(@PathVariable("iso") String iso);
}
