package com.zephyr.task.clients;

import com.zephyr.data.protocol.dto.PlaceDto;
import com.zephyr.task.utils.Profiles;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import java.util.List;

@Profile(Profiles.NOT_TEST)
@FeignClient("location-service")
public interface LocationServiceClient {

    @GetMapping
    List<PlaceDto> findByCountryIsoAndNameContains(@RequestParam("iso") String iso, @RequestParam("name") String name);

    // TODO: Feign doesn't support Reactor
    default Flux<PlaceDto> findByCountryIsoAndNameContainsAsync(String iso, String name) {
        return Flux.fromIterable(findByCountryIsoAndNameContains(iso, name));
    }
}
