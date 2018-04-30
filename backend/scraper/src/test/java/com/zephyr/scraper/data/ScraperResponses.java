package com.zephyr.scraper.data;

import com.zephyr.commons.ResourceUtils;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.test.mocks.UidProviderMock;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class ScraperResponses {

    private static final String BING_RESPONSE_FILE_NAME = "bing-response.html";
    private static final String DUCKDUCKGO_RESPONSE_FILE_NAME = "duckduckgo-response.json";
    private static final String GOOGLE_RESPONSE_FILE_NAME = "google-response.html";
    private static final String GOOGLE_FRAUD_RESPONSE_FILE_NAME = "google-fraud.html";
    private static final String YAHOO_RESPONSE_FILE_NAME = "yahoo-response.html";
    private static final String YANDEX_RESPONSE_FILE_NAME = "yandex-response.json";

    @Getter(lazy = true)
    private final String bingResponseBody = creteBingResponseBody();

    @Getter(lazy = true)
    private final String duckDuckGoResponseBody = creteDuckDuckGoResponseBody();

    @Getter(lazy = true)
    private final String googleResponseBody = creteGoogleResponseBody();

    @Getter(lazy = true)
    private final String googleFraudResponseBody = creteGoogleFraudResponseBody();

    @Getter(lazy = true)
    private final String yahooResponseBody = creteYahooResponseBody();

    @Getter(lazy = true)
    private final String yandexResponseBody = creteYandexResponseBody();


    public EngineResponse bing() {
        return EngineResponse.builder()
                .status(HttpStatus.OK.value())
                .id(UidProviderMock.DEFAULT_ID)
                .body(bingResponseBody())
                .provider(SearchEngine.BING)
                .headers(Map.of())
                .build();
    }

    public EngineResponse duckDuckGo() {
        return EngineResponse.builder()
                .status(HttpStatus.OK.value())
                .id(UidProviderMock.DEFAULT_ID)
                .body(duckDuckGoResponseBody())
                .provider(SearchEngine.DUCKDUCKGO)
                .headers(Map.of())
                .build();
    }

    public EngineResponse google() {
        return EngineResponse.builder()
                .status(HttpStatus.OK.value())
                .id(UidProviderMock.DEFAULT_ID)
                .body(googleResponseBody())
                .provider(SearchEngine.GOOGLE)
                .headers(Map.of())
                .build();
    }

    public EngineResponse googleFraud() {
        return EngineResponse.builder()
                .status(HttpStatus.OK.value())
                .id(UidProviderMock.DEFAULT_ID)
                .body(googleFraudResponseBody())
                .provider(SearchEngine.GOOGLE)
                .headers(Map.of())
                .build();
    }

    public EngineResponse yahoo() {
        return EngineResponse.builder()
                .status(HttpStatus.OK.value())
                .id(UidProviderMock.DEFAULT_ID)
                .body(yahooResponseBody())
                .provider(SearchEngine.YAHOO)
                .headers(Map.of())
                .build();
    }

    public EngineResponse yandex() {
        return EngineResponse.builder()
                .status(HttpStatus.OK.value())
                .id(UidProviderMock.DEFAULT_ID)
                .body(yandexResponseBody())
                .provider(SearchEngine.YANDEX)
                .headers(Map.of())
                .build();
    }

    public Throwable failed() {
        return new Throwable();
    }

    private String creteBingResponseBody() {
        return ResourceUtils.toString(BING_RESPONSE_FILE_NAME);
    }

    private String creteDuckDuckGoResponseBody() {
        return ResourceUtils.toString(DUCKDUCKGO_RESPONSE_FILE_NAME);
    }

    private String creteGoogleResponseBody() {
        return ResourceUtils.toString(GOOGLE_RESPONSE_FILE_NAME);
    }

    private String creteGoogleFraudResponseBody() {
        return ResourceUtils.toString(GOOGLE_FRAUD_RESPONSE_FILE_NAME);
    }

    private String creteYahooResponseBody() {
        return ResourceUtils.toString(YAHOO_RESPONSE_FILE_NAME);
    }

    private String creteYandexResponseBody() {
        return ResourceUtils.toString(YANDEX_RESPONSE_FILE_NAME);
    }
}
