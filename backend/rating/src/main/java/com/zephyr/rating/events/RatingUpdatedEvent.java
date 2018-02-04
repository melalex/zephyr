package com.zephyr.rating.events;

import com.zephyr.rating.domain.Query;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

@Value(staticConstructor = "of")
@EqualsAndHashCode(callSuper = true)
public class RatingUpdatedEvent extends RemoteApplicationEvent {
    private static final long serialVersionUID = 117345151296824826L;

    private Query query;
}
