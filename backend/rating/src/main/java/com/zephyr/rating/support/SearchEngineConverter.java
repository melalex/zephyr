package com.zephyr.rating.support;

import com.github.jmnarloch.spring.boot.modelmapper.ConverterConfigurerSupport;
import com.zephyr.data.protocol.enums.SearchEngine;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;

@Component
public class SearchEngineConverter extends ConverterConfigurerSupport<String, SearchEngine> {

    @Override
    protected Converter<String, SearchEngine> converter() {
        return new AbstractConverter<>() {

            @Override
            protected SearchEngine convert(String source) {
                return SearchEngine.valueOf(source);
            }
        };
    }
}
