package com.zephyr.rating.controllers;

import static com.zephyr.rating.data.RatingTestData.ratings;
import static com.zephyr.rating.data.RatingTestData.requests;

import com.zephyr.data.protocol.dto.RatingDto;
import com.zephyr.rating.RatingTestConfiguration;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.Request;
import com.zephyr.rating.repository.RatingRepository;
import com.zephyr.rating.repository.RequestRepository;
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
    private RatingRepository ratingRepository;

    @Autowired
    private RequestRepository requestRepository;

    private List<Rating> bingRating;
    private List<Rating> googleRating;
    private List<Rating> yahooRating;

    @Before
    public void setUp() {
        Request bingRequest = requests().bing();
        Request googleRequest = requests().google();
        Request yahooRequest = requests().yahoo();

        List<Rating> bingRating = ratings().bing(bingRequest);
        List<Rating> googleRating = ratings().google(googleRequest);
        List<Rating> yahooRating = ratings().yahoo(yahooRequest);

        requestRepository.save(bingRequest)
                .then(requestRepository.save(googleRequest))
                .then(requestRepository.save(yahooRequest))
                .thenMany(ratingRepository.saveAll(bingRating).collectList().doOnNext(this::setBingRating))
                .thenMany(ratingRepository.saveAll(googleRating).collectList().doOnNext(this::setGoogleRating))
                .thenMany(ratingRepository.saveAll(yahooRating).collectList().doOnNext(this::setYahooRating))
                .subscribe();
    }

    @After
    public void tearDown() {
        ratingRepository.deleteAll().subscribe();
    }

    @Test
    public void shouldFindRating() {
        RatingDto[] objects = Stream.of(bingRating, googleRating, yahooRating)
                .flatMap(Collection::stream)
                .filter(r -> r.getUrl().contains(Tasks.SIMPLE_URL))
                .map(ratings()::toDto)
                .toArray(RatingDto[]::new);

        webTestClient.get()
                .uri(u -> u.path("/v1/rating").queryParam("url", Tasks.SIMPLE_URL).build())
                .exchange()
                .expectBodyList(RatingDto.class)
                .contains(objects)
                .hasSize(objects.length);
    }

    private void setBingRating(List<Rating> bingRating) {
        this.bingRating = bingRating;
    }

    private void setGoogleRating(List<Rating> googleRating) {
        this.googleRating = googleRating;
    }

    private void setYahooRating(List<Rating> yahooRating) {
        this.yahooRating = yahooRating;
    }
}