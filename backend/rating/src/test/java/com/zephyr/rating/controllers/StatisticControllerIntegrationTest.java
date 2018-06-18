package com.zephyr.rating.controllers;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

import com.zephyr.commons.support.Profiles;
import com.zephyr.data.protocol.dto.StatisticsDto;
import com.zephyr.rating.RatingApplication;
import com.zephyr.rating.RatingTestConfiguration;
import com.zephyr.rating.data.TestDataLoader;
import com.zephyr.test.CommonTestData;
import com.zephyr.test.Tasks;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@ActiveProfiles(Profiles.TEST)
@ContextConfiguration(classes = {RatingApplication.class, RatingTestConfiguration.class})
public class StatisticControllerIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TestDataLoader testDataLoader;

    private WebTestClient webTestClient;
    private StatisticsDto bingFirstAppearance;
    private StatisticsDto google;
    private StatisticsDto yahoo;

    @Before
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext)
                .apply(springSecurity())
                .configureClient()
                .filter(basicAuthentication())
                .responseTimeout(Duration.ofDays(1))
                .build();

        bingFirstAppearance = CommonTestData.statistic().bingFirstAppearance();
        google = CommonTestData.statistic().google();
        yahoo = CommonTestData.statistic().yahoo();
    }

    @Test
    @WithMockUser(Tasks.SIMPLE_USER_ID)
    public void shouldFindStatistic() {
        testDataLoader.load();

        var result = webTestClient.get()
                .uri(u -> u.path("/v1/statistic").queryParam("taskId", Tasks.SIMPLE_ID).build())
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .returnResult(StatisticsDto.class)
                .getResponseBody()
                .toStream()
                .collect(Collectors.toSet());

        assertEquals(result, Set.of(bingFirstAppearance, google, yahoo));

        testDataLoader.clean();
    }
}