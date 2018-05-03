package com.zephyr.test;

import com.zephyr.data.protocol.dto.TaskDto;
import com.zephyr.data.protocol.enums.SearchEngine;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class Tasks {

    public static final String SIMPLE_ID = "df32d581-75d7-4110-a9d0-03782e604eb5";
    public static final String SIMPLE_USER_ID = "fe874e7c-fc0e-4755-b964-082554b6e10a";
    public static final String SIMPLE_URL = "https://www.getzephyr.com/";
    public static final String SIMPLE_NAME = "Zephyr";

    public static final boolean SIMPLE_IS_SHARED = true;
    public static final Set<SearchEngine> SIMPLE_ENGINES =
            Set.of(SearchEngine.BING, SearchEngine.GOOGLE, SearchEngine.YAHOO);

    private Criteria criteria;

    public TaskDto simple() {
        return simple(SIMPLE_ID);
    }

    public TaskDto simple(String id) {
        TaskDto result = new TaskDto();
        result.setId(id);
        result.setUserId(SIMPLE_USER_ID);
        result.setUrl(SIMPLE_URL);
        result.setShared(SIMPLE_IS_SHARED);
        result.setName(SIMPLE_NAME);
        result.setEngines(SIMPLE_ENGINES);
        result.setSearchCriteria(List.of(criteria.simple()));

        return result;
    }

    public TaskDto withNewCriteria() {
        return withNewCriteria(SIMPLE_ID);
    }

    public TaskDto withNewCriteria(String id) {
        TaskDto simple = simple(id);
        simple.setSearchCriteria(List.of(criteria.newCriteria()));

        return simple;
    }

    public TaskDto withInvalidPlace() {
        TaskDto simple = simple();
        simple.setSearchCriteria(List.of(criteria.withInvalidPlace()));

        return simple;
    }
}
