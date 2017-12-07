package com.zephyr.location.controllers;

import com.zephyr.data.dto.PlaceDto;
import com.zephyr.personalisation.services.PlaceService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/place")
public class PlaceController {

    @Setter(onMethod = @__(@Autowired))
    private PlaceService placeService;

    @GetMapping("/{id}")
    public Mono<PlaceDto> findById(@PathVariable("id") long id) {
        return placeService.findById(id);
    }

    @GetMapping("/find")
    public Mono<PlaceDto> findByCountryIsoAndName(String iso, String name) {
        return placeService.findByCountryIsoAndName(iso, name);
    }

    @GetMapping("/search")
    public Flux<PlaceDto> findByCountryIsoAndNameStartsWith(String iso, String name) {
        return placeService.findByCountryIsoAndNameStartsWith(iso, name);
    }

    @GetMapping("/country/{iso}")
    public Flux<PlaceDto> findByCountryIso(@PathVariable("iso") String iso, Pageable pageable) {
        return placeService.findByCountryIso(iso, pageable);
    }
}