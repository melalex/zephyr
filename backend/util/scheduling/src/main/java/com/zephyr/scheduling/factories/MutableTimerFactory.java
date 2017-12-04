package com.zephyr.scheduling.factories;

import com.zephyr.scheduling.domain.MutableTimer;
import reactor.core.publisher.MonoSink;

import java.time.LocalDateTime;

public interface MutableTimerFactory {

    MutableTimer create(MonoSink<Void> sink, LocalDateTime dateTime);
}
