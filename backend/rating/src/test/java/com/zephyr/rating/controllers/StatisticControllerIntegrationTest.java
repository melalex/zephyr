package com.zephyr.rating.controllers;

import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.data.protocol.dto.StatisticsDto;
import com.zephyr.rating.RatingTestConfiguration;
import com.zephyr.rating.data.TestDataLoader;
import com.zephyr.test.CommonTestData;
import com.zephyr.test.Tasks;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@Import(RatingTestConfiguration.class)
public class StatisticControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TestDataLoader testDataLoader;

    @Autowired
    private Sink sink;

    private StatisticsDto bingFirstAppearance;
    private StatisticsDto bingSecondAppearance;
    private StatisticsDto google;
    private StatisticsDto yahoo;

    @Before
    public void setUp() {
        bingFirstAppearance = CommonTestData.statistic().bingFirstAppearance();
        bingSecondAppearance = CommonTestData.statistic().bingSecondAppearance();
        google = CommonTestData.statistic().google();
        yahoo = CommonTestData.statistic().yahoo();
    }

    @Test
    @WithMockUser(Tasks.SIMPLE_USER_ID)
    public void shouldFindStatistic() {
        testDataLoader.load();

        webTestClient.get()
                .uri(u -> u.path("/v1/statistic").queryParam("taskId", Tasks.SIMPLE_ID).build())
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectBodyList(StatisticsDto.class)
                .contains(bingFirstAppearance, bingSecondAppearance, google, yahoo);

        testDataLoader.clean();
    }

    @Test
    @WithMockUser(Tasks.SIMPLE_USER_ID)
    public void shouldSubscribeForTask() {
        GenericMessage<SearchResultDto> bingSearchResult =
                new GenericMessage<>(CommonTestData.searchResults().bing());
        GenericMessage<SearchResultDto> googleSearchResult =
                new GenericMessage<>(CommonTestData.searchResults().google());
        GenericMessage<SearchResultDto> yahooSearchResult =
                new GenericMessage<>(CommonTestData.searchResults().yahoo());

        Flux<StatisticsDto> result = webTestClient.get()
                .uri(u -> u.path("/v1/statistic").queryParam("taskId", Tasks.SIMPLE_ID).build())
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .returnResult(StatisticsDto.class)
                .getResponseBody();

        sink.input().send(bingSearchResult);
        sink.input().send(googleSearchResult);
        sink.input().send(yahooSearchResult);

        StepVerifier.create(result)
                .expectNext(bingFirstAppearance)
                .expectNext(bingSecondAppearance)
                .expectNext(google)
                .expectNext(yahoo)
                .thenCancel()
                .log()
                .verify();
    }
}