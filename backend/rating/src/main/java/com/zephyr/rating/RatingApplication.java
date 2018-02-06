package com.zephyr.rating;

import com.zephyr.data.dto.SearchResultDto;
import com.zephyr.rating.services.RatingService;
import com.zephyr.rating.services.bus.RatingBus;
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
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableBinding(Sink.class)
@RemoteApplicationEventScan
public class RatingApplication {
    private static final String QUERIES_TOPIC = "queries";
    private static final String LISTENER_METHOD = "onRatingUpdated";

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

    @Bean
    public Topic queriesTopic() {
        return new PatternTopic(QUERIES_TOPIC);
    }

    @Bean
    public MessageListenerAdapter ratingListenerAdapter(RatingBus ratingBus) {
        return new MessageListenerAdapter(ratingBus, LISTENER_METHOD);
    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter ratingListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(ratingListenerAdapter, queriesTopic());

        return container;
    }
}
