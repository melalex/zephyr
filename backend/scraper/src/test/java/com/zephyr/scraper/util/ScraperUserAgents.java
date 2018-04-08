package com.zephyr.scraper.util;

import com.zephyr.scraper.domain.Query;
import com.zephyr.test.UserAgents;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class ScraperUserAgents {

    private UserAgents userAgents;
    private ModelMapper modelMapper;

    public Query.UserAgent windowsFirefox() {
        return modelMapper.map(userAgents.windowsFirefox(), Query.UserAgent.class);
    }
}
