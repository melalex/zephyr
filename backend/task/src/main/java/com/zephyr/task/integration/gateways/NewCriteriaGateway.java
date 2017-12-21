package com.zephyr.task.integration.gateways;

import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.integration.IntegrationConfiguration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface NewCriteriaGateway {
    String PRIORITY_HEADER = "priority";
    String PRIORITY = "9";

    @Gateway(
            requestChannel = IntegrationConfiguration.SEARCH_CRITERIA_OUTPUT,
            headers = @GatewayHeader(name = PRIORITY_HEADER, value = PRIORITY)
    )
    void send(SearchCriteria searchCriteria);
}
