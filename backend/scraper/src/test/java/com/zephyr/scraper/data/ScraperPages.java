package com.zephyr.scraper.data;

import com.zephyr.commons.support.Page;

public final class ScraperPages {

    public static final int BING_FIRST_ELEMENT = 1;
    public static final int BING_PAGE_SIZE = 10;
    public static final int BING_RESULT_COUNT = 20;

    public static final int DUCK_DUCK_GO_FIRST_ELEMENT = 0;
    public static final int DUCK_DUCK_GO_PAGE_SIZE = 100;
    public static final int DUCK_DUCK_GO_RESULT_COUNT = 200;

    public static final int GOOGLE_FIRST_ELEMENT = 0;
    public static final int GOOGLE_PAGE_SIZE = 100;
    public static final int GOOGLE_RESULT_COUNT = 200;

    public static final int YAHOO_FIRST_ELEMENT = 1;
    public static final int YAHOO_PAGE_SIZE = 10;
    public static final int YAHOO_RESULT_COUNT = 20;

    public static final int YANDEX_FIRST_ELEMENT = 1;
    public static final int YANDEX_PAGE_SIZE = 100;
    public static final int YANDEX_RESULT_COUNT = 200;

    private static final int FIRST_PAGE = 0;

    public Page bingFirstPage() {
        return Page.builder()
                .page(FIRST_PAGE)
                .first(BING_FIRST_ELEMENT)
                .pageSize(BING_PAGE_SIZE)
                .count(BING_RESULT_COUNT)
                .build();
    }

    public Page bingSecondPage() {
        return bingFirstPage().getNext();
    }

    public Page duckDuckGoFirstPage() {
        return Page.builder()
                .page(FIRST_PAGE)
                .first(DUCK_DUCK_GO_FIRST_ELEMENT)
                .pageSize(DUCK_DUCK_GO_PAGE_SIZE)
                .count(DUCK_DUCK_GO_RESULT_COUNT)
                .build();
    }

    public Page duckDuckGoSecondPage() {
        return duckDuckGoFirstPage().getNext();
    }

    public Page googleFirstPage() {
        return Page.builder()
                .page(FIRST_PAGE)
                .first(GOOGLE_FIRST_ELEMENT)
                .pageSize(GOOGLE_PAGE_SIZE)
                .count(GOOGLE_RESULT_COUNT)
                .build();
    }

    public Page googleSecondPage() {
        return googleFirstPage().getNext();
    }

    public Page yahooFirstPage() {
        return Page.builder()
                .page(FIRST_PAGE)
                .first(YAHOO_FIRST_ELEMENT)
                .pageSize(YAHOO_PAGE_SIZE)
                .count(YAHOO_RESULT_COUNT)
                .build();
    }

    public Page yahooSecondPage() {
        return yahooFirstPage().getNext();
    }

    public Page yandexFirstPage() {
        return Page.builder()
                .page(FIRST_PAGE)
                .first(YANDEX_FIRST_ELEMENT)
                .pageSize(YANDEX_PAGE_SIZE)
                .count(YANDEX_RESULT_COUNT)
                .build();
    }

    public Page yandexSecondPage() {
        return yandexFirstPage().getNext();
    }
}
