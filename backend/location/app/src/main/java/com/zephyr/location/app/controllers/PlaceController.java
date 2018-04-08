package com.zephyr.location.app.controllers;

import com.zephyr.data.protocol.dto.PlaceDto;
import com.zephyr.location.business.services.PlaceService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/v1/places")
public class PlaceController {

    @Setter(onMethod = @__(@Autowired))
    private PlaceService placeService;

    @GetMapping("/{id}")
    @Cacheable("PLACE_BY_ID")
    public PlaceDto findById(@PathVariable("id") long id) {
        return placeService.findById(id);
    }

    @GetMapping("/{iso}/{name}")
    @Cacheable("PLACE_BY_COUNTRY_AND_NAME")
    public Set<PlaceDto> findByCountryIsoAndNameStartsWith(@PathVariable("iso") String iso,
                                                           @PathVariable("name") String name) {
        return placeService.findByCountryIsoAndNameStartsWith(iso, name);
    }
}