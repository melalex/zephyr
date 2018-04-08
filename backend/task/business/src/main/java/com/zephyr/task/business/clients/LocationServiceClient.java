package com.zephyr.task.business.clients;

import com.zephyr.data.protocol.dto.PlaceDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

@FeignClient("location-service")
public interface LocationServiceClient {

    @GetMapping("/{iso}/{name}")
    Flux<PlaceDto> findByCountryIsoAndNameStartsWith(@PathVariable("iso") String iso,
                                                     @PathVariable("name") String name);
}
