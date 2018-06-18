package com.zephyr.scraper.mocks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.zephyr.scraper.browser.support.WebClientWrapper;
import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.domain.EngineRequest;
import lombok.experimental.UtilityClass;
import org.mockito.Answers;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

@UtilityClass
public class WebClientMock {

    public WebClientWrapper of() {
        var bingRequest = ScraperTestData.requests().bing().firstPage();
        var duckDuckGoRequest = ScraperTestData.requests().duckDuckGo().firstPage();
        var googleRequest = ScraperTestData.requests().google().firstPage();
        var googleFraudRequest = ScraperTestData.requests().googleFraud();
        var yahooRequest = ScraperTestData.requests().yahoo().firstPage();
        var yandexRequest = ScraperTestData.requests().yandex().firstPage();
        var failedRequest = ScraperTestData.requests().failed();

        var mock = mock(WebClientWrapper.class);

        configure(mock, bingRequest, ScraperTestData.responses().bingResponseBody());
        configure(mock, googleRequest, ScraperTestData.responses().googleResponseBody());
        configure(mock, googleFraudRequest, ScraperTestData.responses().googleFraudResponseBody());
        configure(mock, duckDuckGoRequest, ScraperTestData.responses().duckDuckGoResponseBody());
        configure(mock, yahooRequest, ScraperTestData.responses().yahooResponseBody());
        configure(mock, yandexRequest, ScraperTestData.responses().yandexResponseBody());
        configure(mock, failedRequest, Mono.error(ScraperTestData.responses().failed()));

        return mock;
    }

    private void configure(WebClientWrapper mock, EngineRequest request, String response) {
        configure(mock, request, Mono.just(response));
    }

    private void configure(WebClientWrapper mock, EngineRequest request, Mono<String> response) {
        var clientResponse = mock(ClientResponse.class, Answers.RETURNS_DEEP_STUBS);

        when(clientResponse.statusCode()).thenReturn(HttpStatus.OK);
        when(clientResponse.headers().asHttpHeaders()).thenReturn(new HttpHeaders());
        when(clientResponse.bodyToMono(String.class)).thenReturn(response);

        var exchange = mock.get(request.getFullPath(), request.getParams(), request.getHeaders());

        when(exchange).thenReturn(Mono.just(clientResponse));
    }
}
