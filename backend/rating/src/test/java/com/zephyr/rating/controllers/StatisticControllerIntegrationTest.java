package com.zephyr.rating.controllers;

import com.zephyr.data.protocol.dto.StatisticsDto;
import com.zephyr.rating.RatingTestConfiguration;
import com.zephyr.rating.data.TestDataLoader;
import com.zephyr.test.CommonTestData;
import com.zephyr.test.Tasks;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@Import(RatingTestConfiguration.class)
public class StatisticControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TestDataLoader testDataLoader;

    @Test
    @WithMockUser(Tasks.SIMPLE_USER_ID)
    public void shouldFindStatistic() {
        testDataLoader.load();

        StatisticsDto bingFirstAppearance = CommonTestData.statistic().bingFirstAppearance();
        StatisticsDto bingSecondAppearance = CommonTestData.statistic().bingSecondAppearance();
        StatisticsDto google = CommonTestData.statistic().google();
        StatisticsDto yahoo = CommonTestData.statistic().yahoo();

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

    }
}