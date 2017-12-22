package com.zephyr.task.services.source;

import com.zephyr.task.domain.MeteredSearchCriteria;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.properties.TaskServiceProperties;
import com.zephyr.task.repositories.MeteredSearchCriteriaRepository;
import com.zephyr.task.services.sort.OrderProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class SearchCriteriaSource implements MessageSource<Flux<SearchCriteria>> {
    private static final int FIRST_PAGE = 0;
    private static final int PRIORITY = 1;

    @Setter(onMethod = @__(@Autowired))
    private MeteredSearchCriteriaRepository searchCriteriaRepository;

    @Setter(onMethod = @__(@Autowired))
    private TaskServiceProperties properties;

    @Setter(onMethod = @__(@Autowired))
    private OrderProvider orderProvider;

    @Override
    public Message<Flux<SearchCriteria>> receive() {
        return MessageBuilder.withPayload(receivePublisher())
                .setPriority(PRIORITY)
                .build();
    }

    private Flux<SearchCriteria> receivePublisher() {
        return searchCriteriaRepository
                .findAllForUpdate(properties.getRelevancePeriod(), createPageRequest())
                .map(MeteredSearchCriteria::getSearchCriteria);
    }

    private Pageable createPageRequest() {
        return PageRequest.of(FIRST_PAGE, properties.getBatchSize(), orderProvider.provide());
    }
}
