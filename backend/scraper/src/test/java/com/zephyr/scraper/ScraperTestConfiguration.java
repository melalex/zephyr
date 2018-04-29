package com.zephyr.scraper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.zephyr.commons.interfaces.UidProvider;
import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.test.mocks.ClockMock;
import com.zephyr.test.mocks.UidProviderMock;
import org.mockito.Answers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Clock;

@TestConfiguration
public class ScraperTestConfiguration {

    @Bean
    @Primary
    public UidProvider uidProvider() {
        return UidProviderMock.of();
    }

    @Bean
    @Primary
    public Clock clock() {
        return ClockMock.of();
    }

    @Bean
    @Lazy
    @Primary
    public WebClient webClient() {
        EngineRequest bingRequest = ScraperTestData.requests().bing().firstPage();
        EngineRequest duckDuckGoRequest = ScraperTestData.requests().duckDuckGo().firstPage();
        EngineRequest googleRequest = ScraperTestData.requests().google().firstPage();
        EngineRequest yahooRequest = ScraperTestData.requests().yahoo().firstPage();
        EngineRequest yandexRequest = ScraperTestData.requests().yandex().firstPage();
        EngineRequest failedRequest = ScraperTestData.requests().failed();

        WebClient mock = mock(WebClient.class, Answers.RETURNS_DEEP_STUBS);

        configure(mock, bingRequest, ScraperTestData.responses().bingResponseBody());
        configure(mock, googleRequest, ScraperTestData.responses().googleResponseBody());
        configure(mock, duckDuckGoRequest, ScraperTestData.responses().duckDuckGoResponseBody());
        configure(mock, yahooRequest, ScraperTestData.responses().yahooResponseBody());
        configure(mock, yandexRequest, ScraperTestData.responses().yandexResponseBody());
        configure(mock, failedRequest, Mono.error(ScraperTestData.responses().failed()));

        return mock;
    }

    private void configure(WebClient mock, EngineRequest request, String response) {
        configure(mock, request, Mono.just(response));
    }

    private void configure(WebClient mock, EngineRequest request, Mono<String> response) {
        ClientResponse clientResponse = mock(ClientResponse.class, Answers.RETURNS_DEEP_STUBS);
        when(clientResponse.statusCode()).thenReturn(HttpStatus.OK);
        when(clientResponse.headers().asHttpHeaders()).thenReturn(new HttpHeaders());
        when(clientResponse.bodyToMono(String.class)).thenReturn(response);

        Mono<ClientResponse> exchange = mock.get()
                .uri(eq(request.getFullPath()), eq(request.getHeaders()))
                .header(any())
                .exchange();

        when(exchange).thenReturn(Mono.just(clientResponse));
    }
}
