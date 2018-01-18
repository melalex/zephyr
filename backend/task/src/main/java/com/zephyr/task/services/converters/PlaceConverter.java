package com.zephyr.task.services.converters;

import com.github.jmnarloch.spring.boot.modelmapper.ConverterConfigurerSupport;
import com.zephyr.data.dto.PlaceDto;
import com.zephyr.task.services.dto.SearchCriteriaDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class PlaceConverter extends ConverterConfigurerSupport<SearchCriteriaDto.Place, PlaceDto> {

    @Override
    protected Converter<SearchCriteriaDto.Place, PlaceDto> converter() {
        return new Converter<SearchCriteriaDto.Place, PlaceDto>() {

            @Override
            public PlaceDto convert(MappingContext<SearchCriteriaDto.Place, PlaceDto> context) {
                return null;
            }
        };
    }
}
