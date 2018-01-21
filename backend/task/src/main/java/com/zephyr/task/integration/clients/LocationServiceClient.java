package com.zephyr.task.integration.clients;

import com.zephyr.data.dto.PlaceDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@FeignClient("location-service")
public interface LocationServiceClient {

    @GetMapping("/{id}")
    Mono<PlaceDto> findById(@PathVariable("id") long id);
}
