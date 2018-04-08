package com.zephyr.commons.support;

import com.zephyr.commons.interfaces.UidProvider;

import java.util.UUID;

public final class DefaultUidProvider implements UidProvider {

    @Override
    public String provide() {
        return UUID.randomUUID().toString();
    }
}
