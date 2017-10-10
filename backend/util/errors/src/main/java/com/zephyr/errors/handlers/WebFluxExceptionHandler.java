package com.zephyr.errors.handlers;

import com.zephyr.errors.converters.ProblemConverter;
import com.zephyr.errors.exceptions.ParameterizedException;
import com.zephyr.errors.messages.Problem;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;
import reactor.util.lang.NonNullApi;

import javax.validation.ConstraintViolationException;

@NonNullApi
public class WebFluxExceptionHandler implements WebExceptionHandler {

    @Setter(onMethod = @__(@Autowired))
    private ProblemConverter<ParameterizedException> parameterizedExceptionConverter;

    @Setter(onMethod = @__(@Autowired))
    private ProblemConverter<ConstraintViolationException> validationExceptionConverter;

    @Override
    public Mono<Void> handle(final ServerWebExchange exchange, final Throwable ex) {
        if (ex instanceof ParameterizedException) {
            final Problem problem = parameterizedExceptionConverter.convert(((ParameterizedException) ex));
            exchange.getResponse().setStatusCode(HttpStatus.valueOf(problem.getStatus()));

            return Mono.empty();
        } else if (ex instanceof ConstraintViolationException) {
            final Problem problem = validationExceptionConverter.convert(((ConstraintViolationException) ex));
            exchange.getResponse().setStatusCode(HttpStatus.valueOf(problem.getStatus()));

            return Mono.empty();
        }

        return Mono.error(ex);
    }
}
