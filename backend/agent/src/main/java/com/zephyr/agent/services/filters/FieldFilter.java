package com.zephyr.agent.services.filters;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface FieldFilter<T> {

    Map<String, Object> filter(T entity, List<String> fields);
}
