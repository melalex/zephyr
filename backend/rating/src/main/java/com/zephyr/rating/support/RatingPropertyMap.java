package com.zephyr.rating.support;

import com.github.jmnarloch.spring.boot.modelmapper.PropertyMapConfigurerSupport;
import com.zephyr.data.protocol.dto.RatingDto;
import com.zephyr.rating.domain.Rating;
import lombok.AllArgsConstructor;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RatingPropertyMap extends PropertyMapConfigurerSupport<Rating, RatingDto> {

    @Override
    public PropertyMap<Rating, RatingDto> mapping() {
        return new PropertyMap<>() {

            @Override
            protected void configure() {
                map(source.getRequest().getProvider()).setProvider(null);
                map(source.getRequest().getQuery()).setQueryCriteria(null);
                map().setTimestamp(source.getRequest().getTimestamp());
            }
        };
    }
}
