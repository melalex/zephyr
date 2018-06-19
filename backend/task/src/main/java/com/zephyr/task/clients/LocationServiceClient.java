package com.zephyr.task.clients;

import com.zephyr.commons.support.Profiles;
import com.zephyr.data.protocol.dto.PlaceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import java.util.List;

@Profile(Profiles.NOT_TEST)
@FeignClient("location")
public interface LocationServiceClient {

    @GetMapping
    List<PlaceDto> findByCountryIsoAndNameContains(@RequestParam("iso") String iso, @RequestParam("name") String name);

    // TODO: Feign doesn't support Reactor
    default Flux<PlaceDto> findByCountryIsoAndNameContainsAsync(String iso, String name) {
        return Flux.fromIterable(findByCountryIsoAndNameContains(iso, name));
    }
}
