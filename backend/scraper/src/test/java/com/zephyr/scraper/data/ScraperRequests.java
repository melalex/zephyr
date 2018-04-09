package com.zephyr.scraper.data;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.Query;
import com.zephyr.test.Countries;
import com.zephyr.test.mocks.MockUidProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class ScraperRequests {

    public static final String BING_URL = "https://www.bing.com";
    public static final String BING_URI = "/search";
    public static final String DUCKDUCKGO_URL = "https://duckduckgo.com";
    public static final String DUCKDUCKGO_URI = "";
    public static final String GOOGLE_URL = Countries.UA_LOCALE_GOOGLE;
    public static final String GOOGLE_URI = "/search";
    public static final String YAHOO_URL = "https://search.yahoo.com";
    public static final String YAHOO_URI = "/search";
    public static final String YANDEX_URL = Countries.UA_LOCALE_YANDEX;
    public static final String YANDEX_URI = "";

    private ScraperQueries queries;
    private ScraperHeaders headers;
    private ScraperParams params;

    public PageRequests bing() {
        return PageRequests.builder()
                .url(BING_URL)
                .uri(BING_URI)
                .firstPageOffset(ScraperParams.BING_FIRST_PAGE_OFFSET)
                .firstPageOffset(ScraperParams.BING_SECOND_PAGE_OFFSET)
                .queries(queries)
                .provider(SearchEngine.BING)
                .headers(headers::htmlHeadersFull)
                .firstPageParams(params::bingFirstPage)
                .secondPageParams(params::bingSecondPage)
                .build();
    }

    public PageRequests duckDuckGo() {
        return PageRequests.builder()
                .url(DUCKDUCKGO_URL)
                .uri(DUCKDUCKGO_URI)
                .firstPageOffset(ScraperParams.DUCKDUCKGO_FIRST_PAGE_OFFSET)
                .firstPageOffset(ScraperParams.DUCKDUCKGO_SECOND_PAGE_OFFSET)
                .queries(queries)
                .provider(SearchEngine.DUCKDUCKGO)
                .headers(headers::ajaxHeadersFull)
                .firstPageParams(params::duckDuckGoFirstPage)
                .secondPageParams(params::duckDuckGoSecondPage)
                .build();
    }

    public PageRequests google() {
        return PageRequests.builder()
                .url(GOOGLE_URL)
                .uri(GOOGLE_URI)
                .firstPageOffset(ScraperParams.GOOGLE_FIRST_PAGE_OFFSET)
                .firstPageOffset(ScraperParams.GOOGLE_SECOND_PAGE_OFFSET)
                .queries(queries)
                .provider(SearchEngine.GOOGLE)
                .headers(headers::htmlHeadersFull)
                .firstPageParams(params::googleFirstPage)
                .secondPageParams(params::googleSecondPage)
                .build();
    }

    public PageRequests yahoo() {
        return PageRequests.builder()
                .url(YAHOO_URL)
                .uri(YAHOO_URI)
                .firstPageOffset(ScraperParams.YAHOO_FIRST_PAGE_OFFSET)
                .firstPageOffset(ScraperParams.YAHOO_SECOND_PAGE_OFFSET)
                .queries(queries)
                .provider(SearchEngine.YAHOO)
                .headers(headers::htmlHeadersFull)
                .firstPageParams(params::yahooFirstPage)
                .secondPageParams(params::yahooSecondPage)
                .build();
    }

    public PageRequests yandex() {
        return PageRequests.builder()
                .url(YANDEX_URL)
                .uri(YANDEX_URI)
                .firstPageOffset(ScraperParams.YANDEX_FIRST_PAGE_OFFSET)
                .firstPageOffset(ScraperParams.YANDEX_SECOND_PAGE_OFFSET)
                .queries(queries)
                .provider(SearchEngine.YANDEX)
                .headers(headers::ajaxHeadersFull)
                .firstPageParams(params::yandexFirstPage)
                .secondPageParams(params::yandexSecondPage)
                .build();
    }

    @Builder
    public static class PageRequests {

        private String url;
        private String uri;

        private int firstPageOffset;
        private int secondPageOffset;

        private ScraperQueries queries;
        private SearchEngine provider;

        private BiFunction<String, String, Map<String, List<String>>> headers;
        private Function<Query, Map<String, List<String>>> firstPageParams;
        private Function<Query, Map<String, List<String>>> secondPageParams;

        public EngineRequest firstPage() {
            return firstPage(queries.simple());
        }

        public EngineRequest secondPage() {
            return secondPage(queries.simple());
        }

        public EngineRequest firstPage(Query query) {
            return base(query).offset(firstPageOffset)
                    .params(firstPageParams.apply(query))
                    .build();
        }

        public EngineRequest secondPage(Query query) {
            return base(query).offset(secondPageOffset)
                    .params(secondPageParams.apply(query))
                    .build();
        }

        private EngineRequest.EngineRequestBuilder base(Query query) {
            return EngineRequest.builder()
                    .id(MockUidProvider.DEFAULT_ID)
                    .query(query)
                    .provider(provider)
                    .url(url)
                    .uri(uri)
                    .headers(headers.apply(url, query.getUserAgent().getHeader()));
        }
    }
}
