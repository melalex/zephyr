package com.zephyr.task.integration.gateways;

import com.zephyr.data.dto.QueryDto;
import com.zephyr.task.TaskApplication;
import com.zephyr.task.domain.SearchCriteria;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;
import reactor.core.publisher.Mono;

@MessagingGateway
public interface NewCriteriaGateway {

    Mono<Void> send(QueryDto query);
}
