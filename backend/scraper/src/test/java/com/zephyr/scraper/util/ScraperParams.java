package com.zephyr.scraper.util;

import static java.util.List.of;
import static java.util.Map.entry;

import com.zephyr.commons.MapUtils;
import com.zephyr.scraper.domain.Query;

import java.util.List;
import java.util.Map;

public final class ScraperParams {

    public static final String PAGE_SIZE = "100";

    private static final String LANGUAGE = "lr";
    private static final String INTERFACE = "hl";
    private static final String AD_TEST = "adtest";
    private static final String ON = "on";
    private static final String LOCATION = "uule";
    private static final String GLP = "glp";
    private static final String ONE = "1";
    private static final String PARENT = "tci";
    private static final String SAFE = "safe";
    private static final String IMAGE = "image";
    private static final String QUERY = "q";
    private static final String START = "start";
    private static final String NUMBER = "num";
    private static final String SECOND_PAGE = "2";
    private static final String LANGUAGE_TEMPLATE = "lang_%s";
    private static final String PARENT_TEMPLATE = "p:%s";

    public Map<String, List<String>> googleFirstPage(Query query) {
        return googleBase(query);
    }

    public Map<String, List<String>> googleSecondPage(Query query) {
        return MapUtils.put(googleBase(query), START, of(SECOND_PAGE));
    }

    private Map<String, List<String>> googleBase(Query query) {
        return Map.ofEntries(
                entry(SAFE, of(IMAGE)),
                entry(AD_TEST, of(ON)),
                entry(GLP, of(ONE)),
                entry(QUERY, DataUtils.value(query.getQuery())),
                entry(NUMBER, of(PAGE_SIZE)),
                entry(PARENT, of(String.format(PARENT_TEMPLATE, query.getPlace().getParent()))),
                entry(LOCATION, DataUtils.value(query.getPlace().getUule())),
                entry(INTERFACE, DataUtils.value(query.getLanguageIso())),
                entry(LANGUAGE, of(String.format(LANGUAGE_TEMPLATE, query.getLanguageIso())))
        );
    }
}
