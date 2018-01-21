package com.zephyr.task.integration.source;

import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.services.SearchCriteriaService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class SearchCriteriaSource implements MessageSource<Flux<SearchCriteria>> {
    private static final int PRIORITY = 1;

    @Setter(onMethod = @__(@Autowired))
    private SearchCriteriaService searchCriteriaService;

    @Override
    public Message<Flux<SearchCriteria>> receive() {
        return MessageBuilder.withPayload(searchCriteriaService.findAllForUpdate())
                .setPriority(PRIORITY)
                .build();
    }
}
