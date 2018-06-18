package com.zephyr.task.data;

import com.zephyr.task.domain.SearchEngine;
import com.zephyr.task.domain.Task;
import com.zephyr.test.Tasks;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class TaskEntities {

    public static final Set<SearchEngine> SIMPLE_ENGINES =
            Set.of(SearchEngine.BING, SearchEngine.GOOGLE, SearchEngine.YAHOO);

    private CriteriaEntities criteria;

    public Task simple() {
        var result = new Task();
        result.setId(Tasks.SIMPLE_ID);
        result.setUserId(Tasks.SIMPLE_USER_ID);
        result.setUrl(Tasks.SIMPLE_URL);
        result.setShared(Tasks.SIMPLE_IS_SHARED);
        result.setName(Tasks.SIMPLE_NAME);
        result.setEngines(SIMPLE_ENGINES);
        result.setSearchCriteria(List.of(criteria.simple()));

        return result;
    }

    public Task withNewCriteria() {
        var simple = simple();
        simple.setSearchCriteria(List.of(criteria.newCriteria()));

        return simple;
    }

    public Task withInvalidPlace() {
        var simple = simple();
        simple.setSearchCriteria(List.of(criteria.withInvalidPlace()));

        return simple;
    }
}
