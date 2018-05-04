package com.zephyr.task.clients;

import com.zephyr.data.protocol.dto.PlaceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

import java.util.List;

@FeignClient("location-service")
public interface LocationServiceClient {

    @GetMapping
    List<PlaceDto> findByCountryIsoAndNameStartsWith(String iso, String name);

    // TODO: Feign not supported Reactor yet
    default Flux<PlaceDto> findByCountryIsoAndNameStartsWithAsync(String iso, String name) {
        return Flux.fromIterable(findByCountryIsoAndNameStartsWith(iso, name));
    }
}
