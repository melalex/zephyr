package com.zephyr.dictionary.integration.gateways;

import com.zephyr.commons.data.Keyword;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface UpdateRatingGateway {

    void send(Keyword keyword);
}
