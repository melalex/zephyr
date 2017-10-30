package com.zephyr.scraper.loader.content;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.PageResponse;
import org.springframework.http.MediaType;

public interface ResponseExtractor {

    PageResponse extract(MediaType type, String content, SearchEngine engine, int number);
}
