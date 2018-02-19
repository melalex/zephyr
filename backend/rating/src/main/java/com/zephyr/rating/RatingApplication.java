package com.zephyr.rating;

import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.commons.interfaces.Transformer;
import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.services.RatingSavingService;
import org.intellij.lang.annotations.Language;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

import java.util.List;

@SpringBootApplication
@EnableBinding(Sink.class)
@RemoteApplicationEventScan
public class RatingApplication {

    @Language("SpEL")
    private static final String NEW_SEARCH_RESULT_MESSAGE = "'Received new Search result: ' + payload.id";

    public static void main(String[] args) {
        SpringApplication.run(RatingApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ExtendedMapper extendedMapper() {
        return new ExtendedMapper(modelMapper());
    }

    @Bean
    public IntegrationFlow updateRatingFlow(Transformer<SearchResultDto, List<Rating>> searchResultTransformer,
                                            RatingSavingService ratingSavingService) {
        return IntegrationFlows.from(Sink.INPUT)
                .log(new LiteralExpression(NEW_SEARCH_RESULT_MESSAGE))
                .transform(searchResultTransformer::transform)
                .handle(ratingSavingService)
                .get();
    }
}
