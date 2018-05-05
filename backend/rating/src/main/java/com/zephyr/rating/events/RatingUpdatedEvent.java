package com.zephyr.rating.events;

import com.zephyr.rating.domain.Request;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

@NoArgsConstructor
public class RatingUpdatedEvent extends RemoteApplicationEvent {

    private static final long serialVersionUID = 2378814134190132016L;

    @Getter
    @Setter
    private Request request;

    public RatingUpdatedEvent(Object source, String originService, String destinationService) {
        super(source, originService, destinationService);
    }

    public RatingUpdatedEvent(Object source, String originService) {
        super(source, originService);
    }
}
