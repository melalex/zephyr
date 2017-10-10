package com.zephyr.dictionary.controllers.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;
import reactor.util.lang.NonNullApi;

@Slf4j
@Component
@NonNullApi
public class ParameterizedExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof ParameterizedException) {
            return null;
        }

        log.error("Unhandled exception", ex);

        return Mono.error(ex);
    }
}
