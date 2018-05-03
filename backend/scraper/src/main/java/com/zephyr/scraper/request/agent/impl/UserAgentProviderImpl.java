package com.zephyr.scraper.request.agent.impl;

import com.zephyr.commons.ListUtils;
import com.zephyr.scraper.domain.Query;
import com.zephyr.scraper.request.agent.UserAgentProvider;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserAgentProviderImpl implements UserAgentProvider {

    private List<Query.UserAgent> agentList;

    @Override
    public Query.UserAgent provide() {
        return ListUtils.random(agentList);
    }
}
