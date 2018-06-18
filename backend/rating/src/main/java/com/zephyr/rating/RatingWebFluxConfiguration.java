package com.zephyr.rating;

import com.zephyr.commons.support.OAuth2Principal;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class RatingWebFluxConfiguration implements WebFluxConfigurer {

    private static final String HEADER_TEMPLATE = "^Bearer (.+)$";
    private static final Pattern HEADER_PATTERN = Pattern.compile(HEADER_TEMPLATE);

    @Override
    @SuppressWarnings("Duplicates")
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
        configurer.addCustomResolver(new HandlerMethodArgumentResolver() {

            @Override
            public boolean supportsParameter(@NotNull MethodParameter methodParameter) {
                return OAuth2Principal.class.isAssignableFrom(methodParameter.getParameterType());
            }

            @NotNull
            @Override
            public Mono<Object> resolveArgument(@NotNull MethodParameter methodParameter,
                                                @NotNull BindingContext bindingContext,
                                                @NotNull ServerWebExchange serverWebExchange) {
                var authorization = serverWebExchange.getRequest()
                        .getHeaders()
                        .getOrDefault(HttpHeaders.AUTHORIZATION, List.of())
                        .stream()
                        .findFirst()
                        .map(HEADER_PATTERN::matcher)
                        .filter(Matcher::find)
                        .map(m -> m.group(1))
                        .orElseThrow();

                var decoded = Base64.getMimeDecoder().decode(authorization);
                var principal = new String(decoded);

                return Mono.just(new OAuth2Principal(principal));
            }
        });
    }
}
