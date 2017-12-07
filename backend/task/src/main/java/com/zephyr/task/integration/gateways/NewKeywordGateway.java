package com.zephyr.task.integration.gateways;

import com.zephyr.data.commons.Keyword;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface NewKeywordGateway {

    void send(Keyword keyword);
}
