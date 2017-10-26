package com.zephyr.proxy.services.converters;

import com.zephyr.mapping.support.impl.ConverterSupport;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class LocalDateTimeConverter extends ConverterSupport<Long, LocalDateTime> {

    @Override
    public LocalDateTime convert(Long source) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(source), ZoneId.systemDefault());
    }
}