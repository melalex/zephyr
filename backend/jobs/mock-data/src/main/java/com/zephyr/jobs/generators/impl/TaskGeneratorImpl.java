package com.zephyr.jobs.generators.impl;

import com.github.javafaker.Faker;
import com.zephyr.jobs.generators.DateTimeGenerator;
import com.zephyr.jobs.generators.TaskGenerator;
import com.zephyr.jobs.properties.MockDataProperties;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.domain.SearchEngine;
import com.zephyr.task.domain.Task;
import com.zephyr.task.domain.criteria.PlaceCriteria;
import com.zephyr.task.domain.criteria.UserAgentCriteria;
import lombok.Setter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@StepScope
public class TaskGeneratorImpl implements TaskGenerator {

    @Setter(onMethod = @__(@Value("#{jobParameters[T(com.zephyr.jobs.batch.BatchConfiguration).CRITERIA_PER_TASK_JOB_PARAM]}")))
    private int criteriaPerTask;

    @Setter(onMethod = @__(@Autowired))
    private MockDataProperties properties;

    @Setter(onMethod = @__(@Autowired))
    private DateTimeGenerator dateTimeGenerator;

    @Setter(onMethod = @__(@Autowired))
    private Faker faker;

    @Override
    public Task generate() {
        Task result = new Task();
        result.setUserId(faker.internet().emailAddress());
        result.setEngines(Set.of(SearchEngine.values()));
        result.setName(faker.book().title());
        result.setUrl(faker.internet().url());
        result.setShared(faker.random().nextBoolean());
        result.setTo(dateTimeGenerator.generateDate());
        result.setFrom(dateTimeGenerator.generateDate(result.getTo()));
        result.setSearchCriteria(fakeCriteria());

        return result;
    }

    private List<SearchCriteria> fakeCriteria() {
        return IntStream.range(0, criteriaPerTask)
                .mapToObj(i -> new SearchCriteria())
                .peek(s -> s.setLanguageIso(properties.getFaker().getLang()))
                .peek(s -> s.setQuery(faker.hipster().word()))
                .peek(s -> s.setPlace(fakePlace()))
                .peek(s -> s.setUserAgent(fakeAgent()))
                .collect(Collectors.toList());
    }

    private PlaceCriteria fakePlace() {
        PlaceCriteria result = new PlaceCriteria();
        result.setCountry(faker.address().country());
        result.setPlaceName(faker.address().country());

        return result;
    }

    private UserAgentCriteria fakeAgent() {
//        UserAgentCriteria result = new UserAgentCriteria();
//        result.setBrowserName();
//        result.setBrowserVersion();
//        result.setOsName();
//        result.setOsVersion();
//
//        return result;
        return null;
    }
}
