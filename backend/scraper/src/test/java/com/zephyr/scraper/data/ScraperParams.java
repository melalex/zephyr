package com.zephyr.scraper.data;

import static java.util.List.of;
import static java.util.Map.entry;

import com.zephyr.commons.MapUtils;
import com.zephyr.scraper.domain.Query;
import com.zephyr.scraper.data.util.DataUtils;

import java.util.List;
import java.util.Map;

public final class ScraperParams {

    public static final int BING_PAGE_SIZE = 10;
    public static final int BING_FIRST_PAGE_OFFSET = 1;
    public static final int BING_SECOND_PAGE_OFFSET = BING_FIRST_PAGE_OFFSET + BING_PAGE_SIZE;

    public static final int DUCKDUCKGO_PAGE_SIZE = 100;
    public static final int DUCKDUCKGO_FIRST_PAGE_OFFSET = 0;
    public static final int DUCKDUCKGO_SECOND_PAGE_OFFSET = DUCKDUCKGO_FIRST_PAGE_OFFSET + DUCKDUCKGO_PAGE_SIZE;

    public static final int GOOGLE_PAGE_SIZE = 100;
    public static final int GOOGLE_FIRST_PAGE_OFFSET = 0;
    public static final int GOOGLE_SECOND_PAGE_OFFSET = GOOGLE_FIRST_PAGE_OFFSET + GOOGLE_PAGE_SIZE;

    public static final int YAHOO_PAGE_SIZE = 10;
    public static final int YAHOO_FIRST_PAGE_OFFSET = 1;
    public static final int YAHOO_SECOND_PAGE_OFFSET = YAHOO_FIRST_PAGE_OFFSET + YAHOO_PAGE_SIZE;

    public static final int YANDEX_PAGE_SIZE = 100;
    public static final int YANDEX_FIRST_PAGE_OFFSET = 0;
    public static final int YANDEX_SECOND_PAGE_OFFSET = YAHOO_FIRST_PAGE_OFFSET + YANDEX_PAGE_SIZE;

    private static final String BING_QUERY = "q";
    private static final String BING_QUERY_FORMAT = "%s language:%s";
    private static final String BING_FIRST = "first";
    private static final String BING_COUNT = "count";

    private static final String DUCKDUCKGO_QUERY = "q";
    private static final String DUCKDUCKGO_SAFE = "kp";
    private static final String DUCKDUCKGO_NOT_SAFE = "-2";
    private static final String DUCKDUCKGO_AUTO_LOAD = "kc";
    private static final String DUCKDUCKGO_NO_AUTO_LOAD = "1";

    private static final String GOOGLE_LANGUAGE = "lr";
    private static final String GOOGLE_INTERFACE = "hl";
    private static final String GOOGLE_AD_TEST = "adtest";
    private static final String GOOGLE_ON = "on";
    private static final String GOOGLE_LOCATION = "uule";
    private static final String GOOGLE_GLP = "glp";
    private static final String GOOGLE_ONE = "1";
    private static final String GOOGLE_PARENT = "tci";
    private static final String GOOGLE_SAFE = "safe";
    private static final String GOOGLE_IMAGE = "image";
    private static final String GOOGLE_QUERY = "q";
    private static final String GOOGLE_START = "start";
    private static final String GOOGLE_NUMBER = "num";
    private static final String GOOGLE_LANGUAGE_TEMPLATE = "lang_%s";
    private static final String GOOGLE_PARENT_TEMPLATE = "p:%s";

    private static final String YAHOO_QUERY = "p";
    private static final String YAHOO_ENCODING = "ei";
    private static final String YAHOO_START = "b";
    private static final String YAHOO_COUNT = "n";
    private static final String YAHOO_UTF8 = "UTF-8";

    private static final String YANDEX_QUERY = "text";
    private static final String YANDEX_START = "b";
    private static final String YANDEX_COUNT = "n";

    public Map<String, List<String>> bingNotLocalizedFirstPage(Query query) {
        return Map.ofEntries(
                entry(BING_QUERY, of(query.getQuery())),
                entry(BING_COUNT, DataUtils.value(BING_PAGE_SIZE))
        );
    }

    public Map<String, List<String>> bingFirstPage(Query query) {
        return Map.ofEntries(
                entry(BING_QUERY, of(String.format(BING_QUERY_FORMAT, query.getQuery(), query.getLanguageIso()))),
                entry(BING_COUNT, DataUtils.value(BING_PAGE_SIZE))
        );
    }

    public Map<String, List<String>> bingSecondPage(Query query) {
        return MapUtils.put(bingFirstPage(query), BING_FIRST, DataUtils.value(BING_SECOND_PAGE_OFFSET));
    }

    public Map<String, List<String>> googleNotLocalizedFirstPage(Query query) {
        return Map.ofEntries(
                entry(GOOGLE_SAFE, of(GOOGLE_IMAGE)),
                entry(GOOGLE_AD_TEST, of(GOOGLE_ON)),
                entry(GOOGLE_GLP, of(GOOGLE_ONE)),
                entry(GOOGLE_QUERY, DataUtils.value(query.getQuery())),
                entry(GOOGLE_NUMBER, DataUtils.value(GOOGLE_PAGE_SIZE)),
                entry(GOOGLE_PARENT, of(String.format(GOOGLE_PARENT_TEMPLATE, query.getPlace().getParent()))),
                entry(GOOGLE_LOCATION, DataUtils.value(query.getPlace().getUule()))
        );
    }

    public Map<String, List<String>> googleFirstPage(Query query) {
        Map<String, List<String>> localizationParams = Map.ofEntries(
                entry(GOOGLE_INTERFACE, DataUtils.value(query.getLanguageIso())),
                entry(GOOGLE_LANGUAGE, of(String.format(GOOGLE_LANGUAGE_TEMPLATE, query.getLanguageIso())))
        );
        return MapUtils.merge(googleNotLocalizedFirstPage(query), localizationParams);
    }

    public Map<String, List<String>> googleSecondPage(Query query) {
        return MapUtils.put(googleFirstPage(query), GOOGLE_START, DataUtils.value(GOOGLE_SECOND_PAGE_OFFSET));
    }

    public Map<String, List<String>> duckDuckGoFirstPage(Query query) {
        return Map.ofEntries(
                entry(DUCKDUCKGO_QUERY, of(query.getQuery())),
                entry(DUCKDUCKGO_SAFE, of(DUCKDUCKGO_NOT_SAFE)),
                entry(DUCKDUCKGO_AUTO_LOAD, of(DUCKDUCKGO_NO_AUTO_LOAD))
        );
    }

    public Map<String, List<String>> duckDuckGoSecondPage(Query query) {
        return duckDuckGoFirstPage(query);
    }

    public Map<String, List<String>> yahooFirstPage(Query query) {
        return Map.ofEntries(
                entry(YAHOO_QUERY, of(query.getQuery())),
                entry(YAHOO_ENCODING, of(YAHOO_UTF8)),
                entry(YAHOO_COUNT, DataUtils.value(YAHOO_PAGE_SIZE))
        );
    }

    public Map<String, List<String>> yahooSecondPage(Query query) {
        return MapUtils.put(yahooFirstPage(query), YAHOO_START, DataUtils.value(YAHOO_SECOND_PAGE_OFFSET));
    }

    public Map<String, List<String>> yandexFirstPage(Query query) {
        return Map.ofEntries(
                entry(YANDEX_QUERY, of(query.getQuery())),
                entry(YANDEX_COUNT, DataUtils.value(YANDEX_PAGE_SIZE))
        );
    }

    public Map<String, List<String>> yandexSecondPage(Query query) {
        return MapUtils.put(yandexFirstPage(query), YANDEX_START, DataUtils.value(YANDEX_SECOND_PAGE_OFFSET));
    }
}
