package com.zephyr.mapping;

import com.zephyr.mapping.mappers.ExtendedMapper;
import com.zephyr.mapping.support.MappingConfigurationSupport;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class MappingConfiguration {

    @Setter(onMethod = @__(@Autowired(required = false)))
    private List<MappingConfigurationSupport> configurations = Collections.emptyList();

    @Bean
    @ConditionalOnMissingBean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        configurations.forEach(c -> c.setUp(modelMapper));

        return modelMapper;
    }

    @Bean
    public ExtendedMapper extendedMapper() {
        return new ExtendedMapper(modelMapper());
    }
}