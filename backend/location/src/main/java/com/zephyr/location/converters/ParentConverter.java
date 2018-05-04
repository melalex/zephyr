package com.zephyr.location.converters;

import com.github.jmnarloch.spring.boot.modelmapper.ConverterConfigurerSupport;
import com.zephyr.location.domain.Place;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;

@Component
public class ParentConverter extends ConverterConfigurerSupport<Place, Long> {

    @Override
    protected Converter<Place, Long> converter() {
        return new AbstractConverter<>() {

            @Override
            protected Long convert(Place source) {
                return source.getId();
            }
        };
    }
}
