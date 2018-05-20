package com.zephyr.rating.data;

import static com.zephyr.rating.data.RatingTestData.ratings;
import static com.zephyr.rating.data.RatingTestData.requests;

import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.Request;
import com.zephyr.rating.repository.RatingRepository;
import com.zephyr.rating.repository.RequestRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataLoader {

    @NonNull
    private RequestRepository requestRepository;

    @NonNull
    private RatingRepository ratingRepository;

    private List<Rating> yahooRating;
    private List<Rating> googleRating;
    private List<Rating> bingRating;

    public List<Rating> getYahooRating() {
        return yahooRating;
    }

    public List<Rating> getGoogleRating() {
        return googleRating;
    }

    public List<Rating> getBingRating() {
        return bingRating;
    }

    public void load() {
        Request bingRequest = requests().bing();
        Request googleRequest = requests().google();
        Request yahooRequest = requests().yahoo();

        List<Rating> bingRating = ratings().bing(bingRequest);
        List<Rating> googleRating = ratings().google(googleRequest);
        List<Rating> yahooRating = ratings().yahoo(yahooRequest);

//        @formatter:off
        requestRepository.save(bingRequest)
                .then(requestRepository.save(googleRequest))
                .then(requestRepository.save(yahooRequest))
                .thenMany(ratingRepository.saveAll(bingRating))
                    .collectList()
                    .doOnNext(this::setBingRating)
                .thenMany(ratingRepository.saveAll(googleRating))
                    .collectList()
                    .doOnNext(this::setGoogleRating)
                .thenMany(ratingRepository.saveAll(yahooRating))
                    .collectList()
                    .doOnNext(this::setYahooRating)
                .subscribe();
//        @formatter:on
    }

    public void clean() {
        requestRepository.deleteAll()
                .then(ratingRepository.deleteAll())
                .subscribe();
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
