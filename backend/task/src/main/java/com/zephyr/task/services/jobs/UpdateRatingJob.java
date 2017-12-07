package com.zephyr.task.services.jobs;

import com.zephyr.task.domain.Dictionary;
import com.zephyr.task.integration.IntegrationProperties;
import com.zephyr.task.integration.gateways.UpdateRatingGateway;
import com.zephyr.task.repositories.DictionaryRepository;
import com.zephyr.task.services.sort.OrderProvider;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RefreshScope
public class UpdateRatingJob {
    private static final int FIRST_PAGE = 0;

    @Setter(onMethod = @__(@Autowired))
    private DictionaryRepository dictionaryRepository;

    @Setter(onMethod = @__(@Autowired))
    private IntegrationProperties properties;

    @Setter(onMethod = @__(@Autowired))
    private OrderProvider orderProvider;

    @Setter(onMethod = @__(@Autowired))
    private UpdateRatingGateway updateRatingGateway;

    @Scheduled(cron = "${integration.updateRating.cron}")
    public void perform() {
        log.info("Performing UpdateRatingJob");

        dictionaryRepository
                .findAllForUpdate(properties.getRelevancePeriod(), createPageRequest())
                .map(Dictionary::getKeyword)
                .subscribe(updateRatingGateway::send);
    }

    private Pageable createPageRequest() {
        return PageRequest.of(FIRST_PAGE, properties.getBatchSize(), orderProvider.provide());
    }
}
