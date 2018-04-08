package com.zephyr.test.mocks;

import com.zephyr.commons.interfaces.UidProvider;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MockUidProvider implements UidProvider {

    public static final String DEFAULT_ID = UUID.randomUUID().toString();

    @NonNull
    private List<String> ids;
    private AtomicInteger index = new AtomicInteger(0);

    public static MockUidProvider of(List<String> ids) {
        return new MockUidProvider(new ArrayList<>(ids));
    }

    public static MockUidProvider of(String id) {
        return of(List.of(id));
    }

    public static MockUidProvider of() {
        return of(DEFAULT_ID);
    }

    @Override
    public String provide() {
        return ids.get(index.incrementAndGet() % ids.size());
    }
}
