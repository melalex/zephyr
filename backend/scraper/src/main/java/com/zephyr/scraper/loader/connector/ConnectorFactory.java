package com.zephyr.scraper.loader.connector;

import com.zephyr.data.enums.SearchEngine;
import org.springframework.http.client.reactive.ClientHttpConnector;

@FunctionalInterface
public interface ConnectorFactory {

    ClientHttpConnector create(SearchEngine engine);
}
