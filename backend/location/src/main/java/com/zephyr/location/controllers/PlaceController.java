package com.zephyr.location.controllers;

import com.zephyr.data.protocol.dto.PlaceDto;
import com.zephyr.location.services.PlaceService;
import com.zephyr.location.util.Caches;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/places")
public class PlaceController {

    private PlaceService placeService;

    @GetMapping("/{id}")
    @Cacheable(Caches.PLACE_BY_ID)
    public PlaceDto findById(@PathVariable long id) {
        return placeService.findById(id);
    }

    @GetMapping
    @Cacheable(Caches.PLACE_BY_COUNTRY_AND_NAME)
    public Set<PlaceDto> findAllByCountryIsoAndNameContains(@RequestParam String iso, @RequestParam String name) {
        return placeService.findAllByCountryIsoAndNameContains(iso, name);
    }

    @GetMapping("/canonical")
    @Cacheable(Caches.PLACE_CANONICAL_NAME)
    public Set<PlaceDto> findAllByCanonicalNameContains(@RequestParam String name, Pageable pageable) {
        return placeService.findAllByCanonicalNameContains(name, pageable);
    }
}