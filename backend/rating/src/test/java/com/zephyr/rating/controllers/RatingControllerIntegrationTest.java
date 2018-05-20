package com.zephyr.rating.controllers;

import static com.zephyr.rating.data.RatingTestData.ratings;

import com.zephyr.data.protocol.dto.RatingDto;
import com.zephyr.rating.RatingTestConfiguration;
import com.zephyr.rating.data.TestDataLoader;
import com.zephyr.rating.domain.Rating;
import com.zephyr.test.Tasks;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@Import(RatingTestConfiguration.class)
public class RatingControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TestDataLoader testDataLoader;

    @Before
    public void setUp() {
        testDataLoader.load();
    }

    @After
    public void tearDown() {
        testDataLoader.clean();
    }

    @Test
    public void shouldFindRating() {
        RatingDto[] expected = Stream.<List<Rating>>builder()
                .add(testDataLoader.getBingRating())
                .add(testDataLoader.getGoogleRating())
                .add(testDataLoader.getYahooRating())
                .build()
                .flatMap(Collection::stream)
                .filter(r -> r.getUrl().contains(Tasks.SIMPLE_URL))
                .map(ratings()::toDto)
                .toArray(RatingDto[]::new);

        webTestClient.get()
                .uri(u -> u.path("/v1/rating").queryParam("url", Tasks.SIMPLE_URL).build())
                .exchange()
                .expectBodyList(RatingDto.class)
                .contains(expected)
                .hasSize(expected.length);
    }
}