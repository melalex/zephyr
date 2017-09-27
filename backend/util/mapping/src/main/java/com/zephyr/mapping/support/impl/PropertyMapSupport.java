package com.zephyr.mapping.support.impl;

import com.zephyr.mapping.context.PropertyMapContext;
import com.zephyr.mapping.support.MappingConfigurationSupport;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public abstract class PropertyMapSupport<S, D> implements MappingConfigurationSupport {

    @Override
    public final void setUp(ModelMapper modelMapper) {
        modelMapper.addMappings(propertyMap());
        modelMapper.addMappings(reversePropertyMap());
    }

    private PropertyMap<S, D> propertyMap() {
        return new PropertyMap<>() {

            @Override
            protected void configure() {
                PropertyMapContext<S, D> context = PropertyMapContext.<S, D>builder()
                        .source(source)
                        .destination(destination)
//                        .map(using())
//                        .provider()
//                        .converter()
                        .build();

                mapping(context);
            }
        };
    }

    private PropertyMap<D, S> reversePropertyMap() {
        return new PropertyMap<>() {

            @Override
            protected void configure() {
                PropertyMapContext<D, S> context = PropertyMapContext.<D, S>builder()
                        .source(source)
                        .destination(destination)
//                        .map(using())
//                        .provider()
//                        .converter()
                        .build();

                reverseMapping(context);
            }
        };
    }

    protected abstract void mapping(PropertyMapContext<S, D> context);

    protected abstract void reverseMapping(PropertyMapContext<D, S> context);
}
