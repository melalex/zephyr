package com.zephyr.rating.services.impl;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.zephyr.commons.interfaces.Assembler;
import com.zephyr.data.protocol.dto.StatisticsDto;
import com.zephyr.data.protocol.request.StatisticRequest;
import com.zephyr.rating.data.RatingTestData;
import com.zephyr.rating.domain.Request;
import com.zephyr.rating.domain.RequestCriteria;
import com.zephyr.rating.repository.RatingRepository;
import com.zephyr.rating.services.SubscriptionService;
import com.zephyr.rating.support.StatisticsDtoFactory;
import com.zephyr.test.CommonTestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class StatisticServiceImplTest {

    @Mock
    private SubscriptionService subscriptionService;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private StatisticsDtoFactory statisticsDtoFactory;

    @Mock
    private Assembler<StatisticRequest, List<RequestCriteria>> requestCriteriaAssembler;

    @InjectMocks
    private StatisticServiceImpl testInstance;

    private StatisticRequest statisticRequest;

    @Before
    public void setUp() {
        statisticRequest = CommonTestData.statistic().simpleRequest();

        RequestCriteria requestCriteria = RatingTestData.requestCriteria().simple();
        StatisticsDto statistics = CommonTestData.statistic().google();
        Request request = RatingTestData.requests().google();

        when(requestCriteriaAssembler.assemble(statisticRequest)).thenReturn(Mono.just(List.of(requestCriteria)));
        when(ratingRepository.findByCriteria(requestCriteria)).thenReturn(Flux.empty());
        when(statisticsDtoFactory.create(eq(requestCriteria), anyList())).thenReturn(statistics);
        when(subscriptionService.subscribeOn(requestCriteria)).thenReturn(Flux.just(request));
    }

    @Test
    public void shouldNotSubscribeIfToDateSet() {
        StepVerifier.create(testInstance.findStatisticsAndSubscribeForTask(statisticRequest))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }
}