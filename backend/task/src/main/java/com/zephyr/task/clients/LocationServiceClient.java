package com.zephyr.task.clients;

import com.zephyr.data.protocol.dto.PlaceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

import java.util.List;

@FeignClient("location-service")
public interface LocationServiceClient {

    @GetMapping
    List<PlaceDto> findByCountryIsoAndNameContains(String iso, String name);

    // TODO: Feign doesn't supported Reactor
    default Flux<PlaceDto> findByCountryIsoAndNameContainsAsync(String iso, String name) {
        return Flux.fromIterable(findByCountryIsoAndNameContains(iso, name));
    }
}
