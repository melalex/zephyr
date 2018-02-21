package com.zephyr.commons.support;

import com.zephyr.commons.MapUtils;
import com.zephyr.commons.interfaces.Manager;
import com.zephyr.commons.interfaces.Provider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultManager<K, P extends Provider<K>> implements Manager<K, P> {

    private Map<K, P> providers;

    public static <K, P extends Provider<K>> DefaultManager<K, P> of(Collection<P> providers) {
        Map<K, P> map = new HashSet<>(providers).stream()
                .flatMap(p -> p.supports().stream().map(k -> Map.entry(k, p)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new DefaultManager<>(map);
    }

    @Override
    public P manage(K selector) {
        return MapUtils.getOrThrow(providers, selector);
    }
}
