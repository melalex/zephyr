package com.zephyr.rating.services;

import static com.zephyr.test.CommonTestData.searchResults;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.zephyr.commons.support.Profiles;
import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.data.protocol.dto.StatisticsDto;
import com.zephyr.data.protocol.request.StatisticRequest;
import com.zephyr.rating.RatingTestConfiguration;
import com.zephyr.rating.data.RatingTestData;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.repository.RatingRepository;
import com.zephyr.rating.services.impl.StatisticServiceImpl;
import com.zephyr.test.CommonTestData;
import com.zephyr.test.Tasks;
import com.zephyr.test.mocks.PrincipalMock;
import org.apache.commons.lang3.BooleanUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Example;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(Profiles.TEST)
public class StatisticServiceIntegrationTest {

    @Autowired
    private Sink sink;

    @Autowired
    private StatisticServiceImpl testInstance;

    @Autowired
    private RatingRepository ratingRepository;

    private StatisticsDto bingFirstAppearance;
    private StatisticsDto google;
    private StatisticsDto yahoo;

    @Before
    public void setUp() {
        bingFirstAppearance = CommonTestData.statistic().bingFirstAppearance();
        google = CommonTestData.statistic().google();
        yahoo = CommonTestData.statistic().yahoo();
    }

    @Test
    public void shouldSubscribeForTask() {
        StatisticRequest request = new StatisticRequest(Tasks.SIMPLE_ID, PrincipalMock.of(Tasks.SIMPLE_USER_ID));
        Flux<StatisticsDto> flux = testInstance.findStatisticsAndSubscribeForTask(request);

        Executors.newSingleThreadExecutor().submit(() -> {
            sink.input().send(new GenericMessage<>(searchResults().bing()));
            sink.input().send(new GenericMessage<>(searchResults().google()));
            sink.input().send(new GenericMessage<>(searchResults().yahoo()));
        });

        Set<StatisticsDto> actual = flux.toStream().collect(Collectors.toSet());
        Set<StatisticsDto> expected = Set.of(bingFirstAppearance, google, yahoo);

        assertEquals(expected, actual);

        assertTrue(expectedRating(RatingTestData.ratings().bing()));
        assertTrue(expectedRating(RatingTestData.ratings().google()));
        assertTrue(expectedRating(RatingTestData.ratings().yahoo()));
    }

    private Boolean expectedRating(List<Rating> ratings) {
        return Flux.fromIterable(ratings)
                .map(Example::of)
                .flatMap(ratingRepository::exists)
                .all(BooleanUtils::isTrue)
                .blockOptional()
                .orElse(false);
    }

    @TestConfiguration
    @Import(RatingTestConfiguration.class)
    public static class AbstractStatisticServiceIntegrationTestConfiguration {

    }
}