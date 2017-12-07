package com.zephyr.agent.services.filters.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zephyr.agent.services.filters.FieldFilter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FieldFilterImpl<T> implements FieldFilter<T> {

    @Setter(onMethod = @__(@Autowired))
    private ObjectMapper mapper;

    @Override
    public Map<String, Object> filter(T object, List<String> fields) {
        // @formatter:off
        return mapper.<Map<String, Object>>convertValue(object, new TypeReference<Map<String, Object>>() {})
                .entrySet()
                .stream()
                .filter(e -> CollectionUtils.isEmpty(fields) || fields.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        // @formatter:on
    }
}
