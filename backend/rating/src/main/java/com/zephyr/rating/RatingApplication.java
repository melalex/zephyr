package com.zephyr.rating;

import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.data.dto.SearchResultDto;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.services.bus.RatingBus;
import org.intellij.lang.annotations.Language;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mongodb.outbound.MongoDbStoringMessageHandler;
import org.springframework.integration.redis.outbound.RedisPublishingMessageHandler;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.MessageHandler;

@SpringBootApplication
@EnableBinding(Sink.class)
public class RatingApplication {
    private static final String RATING_COLLECTION_NAME = "rating";
    private static final String RATING_TOPIC = "rating";
    private static final String LISTENER_METHOD = "onRatingUpdated";

    @Language("SpEL")
    private static final String NEW_SEARCH_RESULT_MESSAGE = "'Received new Search result: ' + payload.id";

    @Language("SpEL")
    private static final String PUBLISH_RATING_UPDATE_MESSAGE = "'Publish update for Rating: ' + payload.id";

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
    @Autowired
    public IntegrationFlow updateRatingFlow(GenericTransformer<SearchResultDto, Iterable<Rating>> searchResultTransformer,
                                            MessageHandler mongoOutboundAdapter,
                                            MessageHandler redisOutboundAdapter) {
        return IntegrationFlows.from(Sink.INPUT)
                .log(new LiteralExpression(NEW_SEARCH_RESULT_MESSAGE))
                .transform(searchResultTransformer)
                .handle(mongoOutboundAdapter)
                .split()
                .handle(redisOutboundAdapter)
                .log(new LiteralExpression(PUBLISH_RATING_UPDATE_MESSAGE))
                .get();
    }

    @Bean
    @Autowired
    public MessageHandler mongoOutboundAdapter(MongoDbFactory mongo) {
        MongoDbStoringMessageHandler mongoHandler = new MongoDbStoringMessageHandler(mongo);
        mongoHandler.setCollectionNameExpression(new LiteralExpression(RATING_COLLECTION_NAME));
        return mongoHandler;
    }

    @Bean
    @Autowired
    public MessageHandler redisOutboundAdapter(RedisConnectionFactory redis) {
        RedisPublishingMessageHandler redisHandler = new RedisPublishingMessageHandler(redis);
        redisHandler.setTopic(RATING_TOPIC);
        return redisHandler;
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
        container.addMessageListener(ratingListenerAdapter, new PatternTopic(RATING_TOPIC));

        return container;
    }
}
