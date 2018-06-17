package com.zephyr.rating.controllers;

import com.zephyr.commons.support.Profiles;
import com.zephyr.data.protocol.dto.KeywordDto;
import com.zephyr.rating.RatingApplication;
import com.zephyr.rating.RatingTestConfiguration;
import com.zephyr.rating.data.TestDataLoader;
import com.zephyr.rating.domain.QueryCriteria;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.Request;
import com.zephyr.test.Tasks;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@ActiveProfiles(Profiles.TEST)
@ContextConfiguration(classes = {RatingApplication.class, RatingTestConfiguration.class})
public class RatingControllerIntegrationTest {

    @Autowired
    private TestDataLoader testDataLoader;

    @Autowired
    private ApplicationContext applicationContext;

    private WebTestClient webTestClient;

    @Before
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext)
                .configureClient()
                .responseTimeout(Duration.ofDays(1))
                .build();

        testDataLoader.load();
    }

    @After
    public void tearDown() {
        testDataLoader.clean();
    }

    @Test
    public void shouldFindRating() {
        KeywordDto[] expected = Stream.<List<Rating>>builder()
                .add(testDataLoader.getBingRating())
                .add(testDataLoader.getGoogleRating())
                .add(testDataLoader.getYahooRating())
                .build()
                .flatMap(Collection::stream)
                .filter(r -> r.getUrl().contains(Tasks.SIMPLE_URL))
                .map(Rating::getRequest)
                .map(Request::getQuery)
                .map(QueryCriteria::getQuery)
                .distinct()
                .map(KeywordDto::new)
                .toArray(KeywordDto[]::new);

        webTestClient.get()
                .uri(u -> u.path("/v1/rating").queryParam("url", Tasks.SIMPLE_URL).build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(KeywordDto.class)
                .contains(expected)
                .hasSize(expected.length);
    }
}