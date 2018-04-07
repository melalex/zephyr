package com.zephyr.scraper;

import static java.util.Map.entry;

import com.zephyr.commons.interfaces.Manager;
import com.zephyr.commons.support.DefaultManager;
import com.zephyr.scraper.browser.provider.BrowsingProvider;
import com.zephyr.scraper.browser.provider.impl.DirectBrowsingProvider;
import com.zephyr.scraper.browser.provider.impl.ProxiedBrowsingProvider;
import com.zephyr.scraper.browser.provider.impl.TorBrowsingProvider;
import com.zephyr.scraper.browser.provider.impl.VpnBrowsingProvider;
import com.zephyr.scraper.configuration.properties.RequestType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class BrowserConfiguration {

    @Bean
    public Manager<RequestType, BrowsingProvider> browserManager() {
        return DefaultManager.of(browserMapping());
    }

    @Bean
    public Map<RequestType, BrowsingProvider> browserMapping() {
        return Map.ofEntries(
                entry(RequestType.DIRECT, directBrowsingProvider()),
                entry(RequestType.PROXY, proxyBrowsingProvider()),
                entry(RequestType.TOR, torBrowsingProvider()),
                entry(RequestType.VPN, vpnBrowsingProvider())
        );
    }

    @Bean
    public BrowsingProvider directBrowsingProvider() {
        return new DirectBrowsingProvider();
    }

    @Bean
    public BrowsingProvider proxyBrowsingProvider() {
        return new ProxiedBrowsingProvider();
    }

    @Bean
    public BrowsingProvider torBrowsingProvider() {
        return new TorBrowsingProvider();
    }

    @Bean
    public BrowsingProvider vpnBrowsingProvider() {
        return new VpnBrowsingProvider();
    }
}
