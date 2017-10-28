package com.zephyr.scraper.crawler.provider.impl;

import com.zephyr.scraper.domain.Response;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RefreshScope
public class YandexCrawlingProvider extends AbstractCrawlingProvider {

    @Override
    protected List<String> parse(Response response) {
        return null;
    }
}