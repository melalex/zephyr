package com.zephyr.scraper.loader.context.model;

import com.zephyr.data.dto.ProxyDto;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.PageRequest;
import com.zephyr.scraper.domain.Task;
import lombok.Data;

@Data
public class RequestContext {
    private Task task;
    private SearchEngine provider;
    private ProxyDto proxy;
    private String baseUrl;
    private String uri;
    private PageRequest page;
}