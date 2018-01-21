package com.zephyr.task.services.assemblers;

import com.zephyr.commons.ReactorUtils;
import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.commons.interfaces.Assembler;
import com.zephyr.data.dto.QueryDto;
import com.zephyr.errors.domain.Actual;
import com.zephyr.errors.domain.Field;
import com.zephyr.errors.domain.Reason;
import com.zephyr.errors.domain.SubjectError;
import com.zephyr.errors.dsl.SubjectSpec;
import com.zephyr.errors.exceptions.InconsistentModelException;
import com.zephyr.errors.utils.ErrorUtil;
import com.zephyr.errors.utils.ExceptionUtils;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.domain.criteria.PlaceCriteria;
import com.zephyr.task.domain.criteria.UserAgentCriteria;
import com.zephyr.task.integration.clients.AgentServiceClient;
import com.zephyr.task.integration.clients.LocationServiceClient;
import lombok.Setter;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class QueryAssembler implements Assembler<SearchCriteria, QueryDto> {
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
        Collection<SubjectError> errors = new ConcurrentLinkedQueue<>();

        return Mono.just(source)
                .map(mapper.mapperFor(QueryDto.class))
                .transform(ReactorUtils.doOnNextAsync(q -> populatePlace(source, q, errors)))
                .transform(ReactorUtils.doOnNextAsync(q -> populateAgent(source, q, errors)))
                .doOnNext(q -> ExceptionUtils.assertErrors(new InconsistentModelException(ERROR_MESSAGE), errors));
    }

    private Publisher<QueryDto> populatePlace(SearchCriteria source, QueryDto query, Collection<SubjectError> errors) {
        PlaceCriteria place = source.getPlace();

        return locationServiceClient.findByCountryIsoAndNameStartsWith(place.getCountry(), place.getPlaceName())
                .doOnNext(query::setPlace)
                .map(p -> query)
                .switchIfEmpty(Mono.just(query).doOnNext(q -> errors.add(newError(place))));
    }

    private Publisher<QueryDto> populateAgent(SearchCriteria source, QueryDto query, Collection<SubjectError> errors) {
        UserAgentCriteria agent = source.getUserAgent();

        return agentServiceClient.findByExample(agent)
                .doOnNext(query::setUserAgent)
                .map(p -> query)
                .switchIfEmpty(Mono.just(query).doOnNext(q -> errors.add(newError(agent))));
    }

    private SubjectError newError(PlaceCriteria place) {
        // @formatter:off
        return newSubjectSpec(PLACE_FIELD, place)
                .payload()
                    .with(place.getPlaceName())
                    .with(place.getCountry())
                    .add()
                .create();
        // @formatter:on
    }

    private SubjectError newError(UserAgentCriteria agent) {
        // @formatter:off
        return newSubjectSpec(AGENT_FIELD, agent)
                .payload()
                    .with(agent.getOsName())
                    .with(agent.getOsVersion())
                    .with(agent.getBrowserName())
                    .with(agent.getBrowserVersion())
                    .add()
                .create();
        // @formatter:on
    }

    private SubjectSpec newSubjectSpec(String field, Object model) {
        // @formatter:off
        return SubjectSpec.from()
                .path()
                    .root(ErrorUtil.toCamel(QueryDto.class))
                    .with(field)
                    .with(Reason.NOT_FOUND)
                    .add()
                .actual(Actual.isA(model))
                .field(Field.isA(field));
        // @formatter:on
    }
}
