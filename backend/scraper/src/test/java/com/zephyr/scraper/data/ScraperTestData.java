package com.zephyr.scraper.data;

import com.zephyr.test.CommonTestData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScraperTestData {

    private static final ScraperTestData INSTANCE = createInstance();

    private ScraperCountries countries;
    private ScraperUserAgents userAgents;
    private ScraperPlaces places;
    private ScraperQueries queries;
    private ScraperHeaders headers;
    private ScraperParams params;
    private ScraperRequests requests;
    private ScraperPages pages;
    private ScraperResponses responses;

    private static ScraperTestData createInstance() {
        var instance = new ScraperTestData();
        var modelMapper = new ModelMapper();

        instance.countries = new ScraperCountries(CommonTestData.countries(), modelMapper);
        instance.userAgents = new ScraperUserAgents(CommonTestData.userAgents(), modelMapper);
        instance.places = new ScraperPlaces(CommonTestData.places(), modelMapper);
        instance.queries = new ScraperQueries(CommonTestData.queries(), modelMapper);
        instance.headers = new ScraperHeaders(instance.queries);
        instance.params = new ScraperParams();
        instance.requests = new ScraperRequests(instance.queries, instance.headers, instance.params);
        instance.pages = new ScraperPages();
        instance.responses = new ScraperResponses();

        return instance;
    }

    public static ScraperCountries countries() {
        return INSTANCE.countries;
    }

    public static ScraperUserAgents userAgents() {
        return INSTANCE.userAgents;
    }

    public static ScraperPlaces places() {
        return INSTANCE.places;
    }

    public static ScraperQueries queries() {
        return INSTANCE.queries;
    }

    public static ScraperHeaders headers() {
        return INSTANCE.headers;
    }

    public static ScraperParams params() {
        return INSTANCE.params;
    }

    public static ScraperRequests requests() {
        return INSTANCE.requests;
    }

    public static ScraperPages pages() {
        return INSTANCE.pages;
    }

    public static ScraperResponses responses() {
        return INSTANCE.responses;
    }
}
