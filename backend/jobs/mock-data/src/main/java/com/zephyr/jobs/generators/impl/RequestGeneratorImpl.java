package com.zephyr.jobs.generators.impl;

import com.zephyr.commons.StreamUtils;
import com.zephyr.jobs.extensions.ExtendedFaker;
import com.zephyr.jobs.generators.RequestGenerator;
import com.zephyr.rating.domain.Query;
import com.zephyr.rating.domain.Request;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.domain.SearchEngine;
import com.zephyr.task.domain.Task;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@StepScope
public class RequestGeneratorImpl implements RequestGenerator {

    @Setter(onMethod = @__(@Value("#{jobParameters[T(com.zephyr.jobs.batch.BatchConfiguration).REQUEST_PER_CRITERIA_JOB_PARAM]}")))
    private int requestPerCriteria;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedFaker faker;

    @Setter(onMethod = @__(@Autowired))
    private ModelMapper mapper;

    @Override
    public List<Request> generate(Task task) {
        return task.getSearchCriteria()
                .stream()
                .flatMap(toRequest(task))
                .collect(Collectors.toList());
    }

    private Function<SearchCriteria, Stream<? extends Request>> toRequest(Task task) {
        return c -> task.getEngines()
                .stream()
                .flatMap(generateRequest(task, c));
    }

    private Function<SearchEngine, Stream<? extends Request>> generateRequest(Task task, SearchCriteria c) {
        return e -> StreamUtils.counter(requestPerCriteria)
                .mapToObj(i -> createRequest(e, c, faker.time().dateTime(task.getFrom(), task.getTo())));
    }

    private Request createRequest(SearchEngine provider, SearchCriteria criteria, LocalDateTime timestamp) {
        Request request = new Request();

        request.setProvider(provider.name());
        request.setTimestamp(timestamp);
        request.setQuery(mapper.map(criteria, Query.class));

        return request;
    }
}
