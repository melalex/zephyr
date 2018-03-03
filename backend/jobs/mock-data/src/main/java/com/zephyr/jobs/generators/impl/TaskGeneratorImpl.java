package com.zephyr.jobs.generators.impl;

import com.zephyr.jobs.extensions.ExtendedFaker;
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

import java.time.Clock;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@StepScope
public class TaskGeneratorImpl implements TaskGenerator {

    @Setter(onMethod = @__(@Value("#{jobParameters[T(com.zephyr.jobs.batch.BatchConfiguration).CRITERIA_PER_TASK_JOB_PARAM]}")))
    private int criteriaPerTask;

    @Setter(onMethod = @__(@Value("#{jobParameters[T(com.zephyr.jobs.batch.BatchConfiguration).PROVIDERS_PER_TASK_JOB_PARAM]}")))
    private int providersPerTask;

    @Setter(onMethod = @__(@Autowired))
    private MockDataProperties properties;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedFaker faker;

    @Setter(onMethod = @__(@Autowired))
    private Clock clock;

    @Override
    public Task generate() {
        Task result = new Task();
        LocalDate now = LocalDate.now(clock);
        LocalDate launchDate = properties.getLaunchDate(now);

        result.setUserId(faker.internet().emailAddress());
        result.setEngines(fakeEngines());
        result.setName(faker.book().title());
        result.setUrl(faker.internet().url());
        result.setShared(faker.random().nextBoolean());
        result.setTo(faker.time().date(now, launchDate));
        result.setFrom(faker.time().date(now, result.getTo()));
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

    private Set<SearchEngine> fakeEngines() {
        List<SearchEngine> engines = new ArrayList<>(List.of(SearchEngine.values()));
        Collections.shuffle(engines);

        return new HashSet<>(engines.subList(0, providersPerTask));
    }

    private PlaceCriteria fakePlace() {
        PlaceCriteria result = new PlaceCriteria();
        result.setCountry(faker.address().country());
        result.setPlaceName(faker.address().country());

        return result;
    }

    private UserAgentCriteria fakeAgent() {
//        TODO: Populate userAgent
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
