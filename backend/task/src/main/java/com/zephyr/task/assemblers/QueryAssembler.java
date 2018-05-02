package com.zephyr.task.assemblers;

import com.zephyr.commons.LoggingUtils;
import com.zephyr.commons.ReactorUtils;
import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.commons.interfaces.Assembler;
import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.errors.domain.Path;
import com.zephyr.errors.domain.Reasons;
import com.zephyr.errors.domain.Subject;
import com.zephyr.errors.dsl.Exceptions;
import com.zephyr.errors.dsl.Problems;
import com.zephyr.errors.dsl.SubjectSpec;
import com.zephyr.errors.utils.ErrorUtil;
import com.zephyr.errors.utils.ExceptionUtils;
import com.zephyr.task.clients.AgentServiceClient;
import com.zephyr.task.clients.LocationServiceClient;
import com.zephyr.task.domain.Place;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.domain.UserAgent;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Component
public class QueryAssembler implements Assembler<SearchCriteria, QueryDto> {

    private static final String BEGIN_ASSEMBLE_MESSAGE = "Start assemble QueryDto for SearchCriteria: {}";
    private static final String FINISH_ASSEMBLE_MESSAGE = "Finish assemble QueryDto: {}";
    private static final String ERROR_MESSAGE = "Can't convert SearchCriteria -> QueryDto";
    private static final String PLACE_FIELD = "place";
    private static final String AGENT_FIELD = "agent";

    @Setter(onMethod = @__(@Autowired))
    private AgentServiceClient agentServiceClient;

    @Setter(onMethod = @__(@Autowired))
    private LocationServiceClient locationServiceClient;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Override
    public Mono<QueryDto> assemble(SearchCriteria source) {
        log.info(BEGIN_ASSEMBLE_MESSAGE, source);

        Collection<Subject> errors = new ConcurrentLinkedQueue<>();

        return Mono.just(source)
                .map(mapper.mapperFor(QueryDto.class))
                .transform(ReactorUtils.doOnNextAsync(q -> populatePlace(source, q, errors)))
//                TODO: Uncomment when userAgent service will be able to handle this request
//                .transform(ReactorUtils.doOnNextAsync(q -> populateAgent(source, q, errors)))
                .doOnNext(q -> ExceptionUtils.assertErrors(Exceptions.inconsistent(ERROR_MESSAGE), errors))
                .doOnNext(LoggingUtils.info(log, FINISH_ASSEMBLE_MESSAGE));
    }

    private Publisher<QueryDto> populatePlace(SearchCriteria source, QueryDto query, Collection<Subject> errors) {
        Place place = source.getPlace();

        return locationServiceClient.findByCountryIsoAndNameStartsWith(place.getCountry(), place.getPlaceName())
                .doOnNext(query::setPlace)
                .map(p -> query)
                .switchIfEmpty(Mono.just(query).doOnNext(q -> errors.add(newError(place))));
    }

    private Publisher<QueryDto> populateAgent(SearchCriteria source, QueryDto query, Collection<Subject> errors) {
        UserAgent agent = source.getUserAgent();

        return agentServiceClient.findByOneExample(agent.getDevice(), agent.getOsName(), agent.getBrowserName())
                .doOnNext(query::setUserAgent)
                .map(p -> query)
                .switchIfEmpty(Mono.just(query).doOnNext(q -> errors.add(newError(agent))));
    }

    private Subject newError(Place place) {
        // @formatter:off
        return newSubjectSpec(PLACE_FIELD)
                .payload()
                    .with(place.getPlaceName())
                    .with(place.getCountry())
                    .completePayload()
                .completeSubject();
        // @formatter:on
    }

    private Subject newError(UserAgent agent) {
        // @formatter:off
        return newSubjectSpec(AGENT_FIELD)
                .payload()
                    .with(agent.getOsName())
                    .with(agent.getBrowserName())
                    .with(agent.getDevice())
                    .completePayload()
                .completeSubject();
        // @formatter:on
    }

    private SubjectSpec<Subject> newSubjectSpec(String field) {
        // @formatter:off
        return Problems.subject()
                .path(Path.of(ErrorUtil.identifier(QueryDto.class)).to(field))
                .reason(Reasons.NOT_FOUND);
        // @formatter:on
    }
}
