package com.zephyr.rating;

import com.zephyr.data.dto.SearchResultDto;
import com.zephyr.rating.services.RatingService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableBinding(Sink.class)
@RemoteApplicationEventScan
public class RatingApplication {

    @Setter(onMethod = @__(@Autowired))
    private RatingService ratingService;

    public static void main(String[] args) {
        SpringApplication.run(RatingApplication.class, args);
    }

    @StreamListener
    private Mono<Void> receiveSearchResult(@Input(Sink.INPUT) Flux<SearchResultDto> result) {
        return ratingService.handleSearchResult(result);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
