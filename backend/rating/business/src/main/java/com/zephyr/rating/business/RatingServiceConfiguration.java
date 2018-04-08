package com.zephyr.rating.business;

import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.rating.business.services.RatingSavingService;
import com.zephyr.rating.core.domain.Rating;
import org.intellij.lang.annotations.Language;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

import java.util.List;

@Configuration
@EnableBinding(Sink.class)
@RemoteApplicationEventScan
public class RatingServiceConfiguration {

    @Language("SpEL")
    private static final String NEW_SEARCH_RESULT_MESSAGE = "'Received new Search result: ' + payload.id";

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ExtendedMapper extendedMapper() {
        return new ExtendedMapper(modelMapper());
    }

    @Bean
    public IntegrationFlow updateRatingFlow(Converter<SearchResultDto, List<Rating>> searchResultConverter,
                                            RatingSavingService ratingSavingService) {
        return IntegrationFlows.from(Sink.INPUT)
                .log(new LiteralExpression(NEW_SEARCH_RESULT_MESSAGE))
                .transform(searchResultConverter::convert)
                .handle(ratingSavingService)
                .get();
    }
}
