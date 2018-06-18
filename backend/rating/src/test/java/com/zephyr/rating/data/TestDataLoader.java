package com.zephyr.rating.data;

import static com.zephyr.rating.data.RatingTestData.ratings;

import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.repository.RatingRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataLoader {

    @NonNull
    private RatingRepository ratingRepository;

    private List<Rating> yahooRating;
    private List<Rating> googleRating;
    private List<Rating> bingRating;

    public List<Rating> getYahooRating() {
        return yahooRating;
    }

    private void setYahooRating(List<Rating> yahooRating) {
        this.yahooRating = yahooRating;
    }

    public List<Rating> getGoogleRating() {
        return googleRating;
    }

    private void setGoogleRating(List<Rating> googleRating) {
        this.googleRating = googleRating;
    }

    public List<Rating> getBingRating() {
        return bingRating;
    }

    private void setBingRating(List<Rating> bingRating) {
        this.bingRating = bingRating;
    }

    public void load() {
        var bingRating = ratings().bing();
        var googleRating = ratings().google();
        var yahooRating = ratings().yahoo();

//        @formatter:off
        ratingRepository.saveAll(bingRating)
                    .collectList()
                    .doOnNext(this::setBingRating)
                .thenMany(ratingRepository.saveAll(googleRating))
                    .collectList()
                    .doOnNext(this::setGoogleRating)
                .thenMany(ratingRepository.saveAll(yahooRating))
                    .collectList()
                    .doOnNext(this::setYahooRating)
                .block();
//        @formatter:on
    }

    public void clean() {
        ratingRepository.deleteAll().block();
    }
}
