package com.zephyr.location.support;

import com.github.jmnarloch.spring.boot.modelmapper.PropertyMapConfigurerSupport;
import com.zephyr.data.protocol.dto.PlaceDto;
import com.zephyr.location.domain.Place;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class PlacePropertyMap extends PropertyMapConfigurerSupport<Place, PlaceDto> {

    @Override
    public PropertyMap<Place, PlaceDto> mapping() {
        return new PropertyMap<>() {

            @Override
            protected void configure() {
                map().setParent(source.getParent().getId());
            }
        };
    }
}
