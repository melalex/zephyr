package com.zephyr.task.integration.gateways;

import com.zephyr.data.internal.dto.QueryDto;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface NewCriteriaGateway {

    @Gateway(requestChannel = "newSearchCriteriaFlow.input")
    void send(QueryDto query);
}
