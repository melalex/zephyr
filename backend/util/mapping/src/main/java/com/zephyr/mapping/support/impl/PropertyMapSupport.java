package com.zephyr.mapping.support.impl;

import com.zephyr.mapping.support.MappingConfigurationSupport;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public abstract class PropertyMapSupport<S, D> implements MappingConfigurationSupport {

    @Override
    public final void setUp(ModelMapper modelMapper) {
        modelMapper.addMappings(mapping());
        modelMapper.addMappings(reverseMapping());
    }

    protected abstract PropertyMap<S, D> mapping();

    protected abstract PropertyMap<D, S> reverseMapping();
}
