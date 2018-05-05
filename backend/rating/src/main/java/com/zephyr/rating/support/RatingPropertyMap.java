package com.zephyr.rating.support;

import com.github.jmnarloch.spring.boot.modelmapper.PropertyMapConfigurerSupport;
import com.zephyr.data.protocol.dto.RatingDto;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.rating.domain.Rating;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RatingPropertyMap extends PropertyMapConfigurerSupport<Rating, RatingDto> {

    private Converter<String, SearchEngine> searchEngineConverter;

    @Override
    public PropertyMap<Rating, RatingDto> mapping() {
        return new PropertyMap<>() {

            @Override
            protected void configure() {
                using(searchEngineConverter).map(source.getRequest().getProvider()).setProvider(null);
                map(source.getRequest().getQuery()).setQuery(null);
                map().setTimestamp(source.getRequest().getTimestamp());
            }
        };
    }
}
