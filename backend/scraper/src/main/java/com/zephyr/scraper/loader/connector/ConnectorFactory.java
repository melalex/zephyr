package com.zephyr.scraper.loader.connector;

import org.springframework.http.client.reactive.ClientHttpConnector;

@FunctionalInterface
public interface ConnectorFactory {

    ClientHttpConnector create(String iso, boolean useProxy);
}
