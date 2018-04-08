package com.zephyr.task.business.gateways;

import com.zephyr.data.internal.dto.QueryDto;
import org.springframework.integration.annotation.MessagingGateway;
import reactor.core.publisher.Mono;

@MessagingGateway
public interface NewCriteriaGateway {

    Mono<Void> send(QueryDto query);
}
