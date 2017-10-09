package com.zephyr.dictionary.integration.source;

import com.zephyr.commons.data.Keyword;
import com.zephyr.dictionary.domain.Dictionary;
import com.zephyr.dictionary.integration.IntegrationProperties;
import com.zephyr.dictionary.repositories.DictionaryRepository;
import com.zephyr.dictionary.services.sort.OrderProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RefreshScope
public class KeywordsSource implements MessageSource<Flux<Keyword>> {
    private static final int FIRST_PAGE = 0;

    @Setter(onMethod = @__(@Autowired))
    private DictionaryRepository dictionaryRepository;

    @Setter(onMethod = @__(@Autowired))
    private IntegrationProperties properties;

    @Setter(onMethod = @__(@Autowired))
    private OrderProvider orderProvider;

    @Override
    public Message<Flux<Keyword>> receive() {
        return new GenericMessage<>(findAllForUpdate());
    }

    private Flux<Keyword> findAllForUpdate() {
        return dictionaryRepository
                .findAllForUpdate(properties.getRelevancePeriod(), createPageRequest())
                .map(Dictionary::getKeyword);
    }

    private Pageable createPageRequest() {
        return PageRequest.of(FIRST_PAGE, properties.getBatchSize(), orderProvider.provide());
    }
}
