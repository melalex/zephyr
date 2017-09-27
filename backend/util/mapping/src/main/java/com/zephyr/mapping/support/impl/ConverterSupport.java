package com.zephyr.mapping.support.impl;

import com.zephyr.mapping.support.MappingConfigurationSupport;
import lombok.Getter;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

public abstract class ConverterSupport<S, D> extends AbstractConverter<S, D> implements MappingConfigurationSupport {

    @Getter(lazy = true)
    private final ConverterSupport<D, S> reverse = createReverseConverter();

    @Override
    public final void setUp(ModelMapper modelMapper) {
        modelMapper.addConverter(this);
        modelMapper.addConverter(getReverse());
    }

    @Override
    public abstract D convert(S source);

    protected S reverseConvert(D source) {
        return null;
    }

    private ConverterSupport<D, S> createReverseConverter() {
        return new ConverterSupport<>() {

            @Override
            public ConverterSupport<S, D> getReverse() {
                return ConverterSupport.this;
            }

            @Override
            public S convert(D source) {
                return ConverterSupport.this.reverseConvert(source);
            }
        };
    }
}
