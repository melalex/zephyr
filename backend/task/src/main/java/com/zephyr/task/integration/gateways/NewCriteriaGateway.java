package com.zephyr.task.integration.gateways;

import com.zephyr.task.TaskApplication;
import com.zephyr.task.domain.SearchCriteria;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;
import reactor.core.publisher.Mono;

@MessagingGateway
public interface NewCriteriaGateway {
    String PRIORITY_HEADER = "priority";
    String PRIORITY = "9";

    @Gateway(
            requestChannel = TaskApplication.SEARCH_CRITERIA_OUTPUT,
            headers = @GatewayHeader(name = PRIORITY_HEADER, value = PRIORITY)
    )
    Mono<Void> send(SearchCriteria searchCriteria);
}
