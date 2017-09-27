package com.zephyr.mapping.context;

import lombok.Builder;
import lombok.Data;
import org.modelmapper.builder.ConverterExpression;
import org.modelmapper.builder.MapExpression;
import org.modelmapper.builder.ProviderExpression;

@Data
@Builder
public class PropertyMapContext<S, D> {
    private S source;
    private D destination;
    private MapExpression<D> map;
    private ProviderExpression<S, D> provider;
    private ConverterExpression<S, D> converter;
}