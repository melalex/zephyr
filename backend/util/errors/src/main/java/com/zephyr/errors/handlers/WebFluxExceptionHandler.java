package com.zephyr.errors.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zephyr.errors.converters.ProblemConverter;
import com.zephyr.errors.exceptions.ParameterizedException;
import com.zephyr.errors.messages.Problem;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import javax.validation.ConstraintViolationException;

// TODO: Replace with controller advice
@Slf4j
@Component
public class WebFluxExceptionHandler implements WebExceptionHandler {

    private static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";
    private static final String CONTENT_TYPE_HEADER_VALUE = "application/json";
    private static final String DEFAULT_ERROR_MESSAGE = "Unexpected error";
    private static final String HANDLE_EXCEPTION_MESSAGE = "Handle error: {}";

    private final ObjectMapper mapper = new ObjectMapper();

    @Setter(onMethod = @__(@Autowired))
    private ProblemConverter<ParameterizedException> parameterizedExceptionConverter;

    @Setter(onMethod = @__(@Autowired))
    private ProblemConverter<ConstraintViolationException> validationExceptionConverter;

    @Setter(onMethod = @__(@Autowired))
    private ProblemConverter<Throwable> unhandledExceptionConverter;

    @NonNull
    @Override
    public Mono<Void> handle(@NonNull ServerWebExchange exchange, @NonNull Throwable ex) {
        var locale = LocaleContextHolder.getLocale();

        Problem problem;

        if (ex instanceof ParameterizedException) {
            problem = parameterizedExceptionConverter.convert(((ParameterizedException) ex), locale);
            log.debug(HANDLE_EXCEPTION_MESSAGE, problem);
        } else if (ex instanceof ConstraintViolationException) {
            problem = validationExceptionConverter.convert(((ConstraintViolationException) ex), locale);
            log.debug(HANDLE_EXCEPTION_MESSAGE, problem);
        } else {
            problem = unhandledExceptionConverter.convert(ex, locale);
            log.error(DEFAULT_ERROR_MESSAGE, ex);
        }

        exchange.getResponse().setStatusCode(HttpStatus.valueOf(problem.getStatus()));
        exchange.getResponse().getHeaders().add(CONTENT_TYPE_HEADER_NAME, CONTENT_TYPE_HEADER_VALUE);

        return exchange.getResponse().writeWith(toDataBuffer(exchange, problem));
    }

    private Mono<DataBuffer> toDataBuffer(ServerWebExchange exchange, Problem problem) {
        return Mono.fromSupplier(() -> {
            try {
                return mapper.writeValueAsBytes(problem);
            } catch (JsonProcessingException e) {
                return Optional.ofNullable(problem.getDetail())
                        .orElse(DEFAULT_ERROR_MESSAGE)
                        .getBytes(StandardCharsets.UTF_8);
            }
        }).map(s -> exchange.getResponse().bufferFactory().wrap(s));
    }
}
