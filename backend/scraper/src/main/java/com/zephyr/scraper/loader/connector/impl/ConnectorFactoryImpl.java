package com.zephyr.scraper.loader.connector.impl;

import com.zephyr.scraper.loader.connector.ConnectorFactory;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import reactor.ipc.netty.http.client.HttpClientOptions;

import java.net.Proxy;

@Component
public class ConnectorFactoryImpl implements ConnectorFactory {
    private final ClientHttpConnector directConnector = new ReactorClientHttpConnector();

    @Override
    public ClientHttpConnector create(String iso, boolean useProxy) {
        if (useProxy) {
            return new ReactorClientHttpConnector(b -> configConnector(b, iso));
        }

        return directConnector;
    }

    private void configConnector(HttpClientOptions.Builder builder, String iso) {
//        Proxy proxy = proxySource.get(iso).block();
//        builder.httpProxy(a -> a.address());
    }
}
