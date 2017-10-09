package com.zephyr.dictionary.integration.gateways;

import com.zephyr.commons.data.Keyword;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface NewKeywordGateway {

    void send(Keyword keyword);
}
