package com.zephyr.scraper.loader.connector.impl;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.config.ConfigurationManager;
import com.zephyr.scraper.loader.connector.ConnectorFactory;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import reactor.ipc.netty.http.client.HttpClientOptions;

@Component
public class ConnectorFactoryImpl implements ConnectorFactory {
    private final ClientHttpConnector directConnector = new ReactorClientHttpConnector();

    @Setter(onMethod = @__(@Autowired))
    private ConfigurationManager configurationManager;

    @Override
    public ClientHttpConnector create(SearchEngine engine) {
        if (configurationManager.configFor(engine).isUseProxy()) {
            return new ReactorClientHttpConnector(this::configConnector);
        }

        return directConnector;
    }

    private void configConnector(HttpClientOptions.Builder builder) {
//        Proxy proxy = proxySource.get(iso).block();
//        builder.httpProxy(a -> a.address());
    }
}