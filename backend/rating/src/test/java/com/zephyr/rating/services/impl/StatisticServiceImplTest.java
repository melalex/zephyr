package com.zephyr.rating.services.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.zephyr.commons.interfaces.Assembler;
import com.zephyr.data.protocol.dto.StatisticsDto;
import com.zephyr.data.protocol.request.StatisticRequest;
import com.zephyr.rating.data.RatingTestData;
import com.zephyr.rating.domain.RequestCriteria;
import com.zephyr.rating.repository.RatingRepository;
import com.zephyr.rating.services.SubscriptionService;
import com.zephyr.rating.support.StatisticsDtoFactory;
import com.zephyr.test.CommonTestData;
import com.zephyr.test.mocks.TimeMachine;
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
import java.util.Map;

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
    private StatisticsDto statistics;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        statisticRequest = CommonTestData.statistic().simpleRequest();
        statistics = CommonTestData.statistic().google();
        statistics.setPosition(Map.of());

        var requestCriteria = RatingTestData.requestCriteria().simple();
        requestCriteria.setTo(TimeMachine.canonicalNow().toLocalDate());

        when(requestCriteriaAssembler.assemble(statisticRequest)).thenReturn(Mono.just(List.of(requestCriteria)));
        when(ratingRepository.findByCriteria(any())).thenReturn(Flux.empty());
        when(statisticsDtoFactory.create(eq(requestCriteria), anyList())).thenReturn(statistics);
    }

    @Test
    public void shouldNotSubscribeIfToDateSet() {
        StepVerifier.create(testInstance.findStatisticsAndSubscribeForTask(statisticRequest))
                .expectNext(statistics)
                .expectComplete()
                .verify();
    }
}