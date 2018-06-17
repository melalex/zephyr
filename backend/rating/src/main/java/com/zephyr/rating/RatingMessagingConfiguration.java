package com.zephyr.rating;

import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.rating.domain.vo.SearchResultVo;
import com.zephyr.rating.services.RatingService;
import org.intellij.lang.annotations.Language;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

@Configuration
@EnableBinding(Sink.class)
public class RatingMessagingConfiguration {

    @Language("SpEL")
    private static final String NEW_SEARCH_RESULT_MESSAGE = "'Received new Search result: ' + payload.id";

    @Bean
    public IntegrationFlow updateRatingFlow(Converter<SearchResultDto, SearchResultVo> searchResultConverter,
                                            RatingService ratingService, SpelExpressionParser spelExpressionParser) {
        return IntegrationFlows.from(Sink.INPUT)
                .log(spelExpressionParser.parseExpression(NEW_SEARCH_RESULT_MESSAGE))
                .transform(searchResultConverter::convert)
                .handle(ratingService)
                .get();
    }
}
