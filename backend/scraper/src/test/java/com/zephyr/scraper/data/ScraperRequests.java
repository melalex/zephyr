package com.zephyr.scraper.data;

import com.zephyr.commons.FunctionUtils;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.Query;
import com.zephyr.test.mocks.UidProviderMock;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class ScraperRequests {

    public static final String BING_URL = "https://www.bing.com";
    public static final String BING_URI = "/search";
    public static final String DUCKDUCKGO_URL = "https://duckduckgo.com";
    public static final String DUCKDUCKGO_URI = "";
    public static final String GOOGLE_URI = "/search";
    public static final String YAHOO_URL = "https://search.yahoo.com";
    public static final String YAHOO_URI = "/search";
    public static final String YANDEX_URI = "";
    public static final String FAILED_REQUEST_ID = "FAILED_REQUEST_ID";
    public static final String FAILED_REQUEST_URL = "FAILED_REQUEST_URL";
    public static final String FRAUD_REQUEST_ID = "FAILED_REQUEST_ID";
    public static final String FRAUD_REQUEST_URL = "FAILED_REQUEST_URL";

    private ScraperQueries queries;
    private ScraperHeaders headers;
    private ScraperParams params;

    @Getter(lazy = true)
    private final PageRequests bing = createBing();

    @Getter(lazy = true)
    private final PageRequests duckDuckGo = createDuckDuckGo();

    @Getter(lazy = true)
    private final PageRequests google = createGoogle();

    @Getter(lazy = true)
    private final PageRequests yahoo = createYahoo();

    @Getter(lazy = true)
    private final PageRequests yandex = createYandex();

    public EngineRequest failed() {
        return EngineRequest.builder()
                .id(FAILED_REQUEST_ID)
                .provider(SearchEngine.GOOGLE)
                .url(FAILED_REQUEST_URL)
                .build();
    }

    public EngineRequest googleFraud() {
        return EngineRequest.builder()
                .id(FRAUD_REQUEST_ID)
                .provider(SearchEngine.GOOGLE)
                .url(FRAUD_REQUEST_URL)
                .build();
    }

    private PageRequests createBing() {
        return PageRequests.builder()
                .urls(FunctionUtils.constant(BING_URL))
                .uri(BING_URI)
                .firstPageOffset(ScraperParams.BING_FIRST_PAGE_OFFSET)
                .secondPageOffset(ScraperParams.BING_SECOND_PAGE_OFFSET)
                .queries(queries)
                .provider(SearchEngine.BING)
                .headers(headers::htmlHeadersFull)
                .firstPageParams(params::bingFirstPage)
                .secondPageParams(params::bingSecondPage)
                .build();
    }

    private PageRequests createDuckDuckGo() {
        return PageRequests.builder()
                .urls(FunctionUtils.constant(DUCKDUCKGO_URL))
                .uri(DUCKDUCKGO_URI)
                .firstPageOffset(ScraperParams.DUCKDUCKGO_FIRST_PAGE_OFFSET)
                .secondPageOffset(ScraperParams.DUCKDUCKGO_SECOND_PAGE_OFFSET)
                .queries(queries)
                .provider(SearchEngine.DUCKDUCKGO)
                .headers(headers::ajaxHeadersFull)
                .firstPageParams(params::duckDuckGoFirstPage)
                .secondPageParams(params::duckDuckGoSecondPage)
                .build();
    }

    private PageRequests createGoogle() {
        return PageRequests.builder()
                .urls(q -> q.getPlace().getCountry().getLocaleGoogle())
                .uri(GOOGLE_URI)
                .firstPageOffset(ScraperParams.GOOGLE_FIRST_PAGE_OFFSET)
                .secondPageOffset(ScraperParams.GOOGLE_SECOND_PAGE_OFFSET)
                .queries(queries)
                .provider(SearchEngine.GOOGLE)
                .headers(headers::htmlHeadersFull)
                .firstPageParams(params::googleFirstPage)
                .secondPageParams(params::googleSecondPage)
                .build();
    }

    private PageRequests createYahoo() {
        return PageRequests.builder()
                .urls(FunctionUtils.constant(YAHOO_URL))
                .uri(YAHOO_URI)
                .firstPageOffset(ScraperParams.YAHOO_FIRST_PAGE_OFFSET)
                .secondPageOffset(ScraperParams.YAHOO_SECOND_PAGE_OFFSET)
                .queries(queries)
                .provider(SearchEngine.YAHOO)
                .headers(headers::htmlHeadersFull)
                .firstPageParams(params::yahooFirstPage)
                .secondPageParams(params::yahooSecondPage)
                .build();
    }

    private PageRequests createYandex() {
        return PageRequests.builder()
                .urls(q -> q.getPlace().getCountry().getLocaleYandex())
                .uri(YANDEX_URI)
                .firstPageOffset(ScraperParams.YANDEX_FIRST_PAGE_OFFSET)
                .secondPageOffset(ScraperParams.YANDEX_SECOND_PAGE_OFFSET)
                .queries(queries)
                .provider(SearchEngine.YANDEX)
                .headers(headers::ajaxHeadersFull)
                .firstPageParams(params::yandexFirstPage)
                .secondPageParams(params::yandexSecondPage)
                .build();
    }

    @Builder
    public static class PageRequests {

        private String uri;

        private int firstPageOffset;
        private int secondPageOffset;

        private ScraperQueries queries;
        private SearchEngine provider;

        private BiFunction<String, String, Map<String, List<String>>> headers;
        private Function<Query, String> urls;
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
            var url = urls.apply(query);
            return EngineRequest.builder()
                    .id(UidProviderMock.DEFAULT_ID)
                    .query(query)
                    .provider(provider)
                    .url(url)
                    .uri(uri)
                    .headers(headers.apply(url, query.getUserAgent().getHeader()));
        }
    }
}
