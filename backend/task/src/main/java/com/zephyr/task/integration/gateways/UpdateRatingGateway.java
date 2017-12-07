package com.zephyr.task.integration.gateways;

import com.zephyr.data.commons.Keyword;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface UpdateRatingGateway {

    void send(Keyword keyword);
}
