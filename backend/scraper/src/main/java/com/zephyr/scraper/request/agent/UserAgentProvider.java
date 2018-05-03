package com.zephyr.scraper.request.agent;

import com.zephyr.scraper.domain.Query;

public interface UserAgentProvider {

    Query.UserAgent provide();
}
