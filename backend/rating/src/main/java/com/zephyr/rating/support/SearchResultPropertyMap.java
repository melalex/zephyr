package com.zephyr.rating.support;

import com.github.jmnarloch.spring.boot.modelmapper.PropertyMapConfigurerSupport;
import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.rating.domain.Request;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class SearchResultPropertyMap extends PropertyMapConfigurerSupport<SearchResultDto, Request> {

    @Override
    public PropertyMap<SearchResultDto, Request> mapping() {
        return new PropertyMap<>() {

            @Override
            protected void configure() {
                map().getQuery().getPlace().setCountry(source.getQuery().getPlace().getCountry().getIso());
            }
        };
    }
}
