package com.zephyr.scraper.util;

import com.zephyr.test.CommonTestData;
import org.modelmapper.ModelMapper;

public final class ScraperTestData {

    private static final ScraperTestData INSTANCE = createInstance();

    private ScraperCountries countries;
    private ScraperUserAgents userAgents;
    private ScraperPlaces places;
    private ScraperQueries queries;

    private static ScraperTestData createInstance() {
        ScraperTestData instance = new ScraperTestData();
        ModelMapper modelMapper = new ModelMapper();

        instance.countries = new ScraperCountries(CommonTestData.countries(), modelMapper);
        instance.userAgents = new ScraperUserAgents(CommonTestData.userAgents(), modelMapper);
        instance.places = new ScraperPlaces(CommonTestData.places(), modelMapper);
        instance.queries = new ScraperQueries(CommonTestData.queries(), modelMapper);

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
}
