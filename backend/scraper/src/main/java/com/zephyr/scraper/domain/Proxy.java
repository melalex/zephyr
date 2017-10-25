package com.zephyr.scraper.domain;

import com.zephyr.data.enums.SearchEngine;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.Map;

@Data
public class Proxy {
    private String id;
    private String username;
    private String password;
    private String countryIso;
    private String ip;
    private int port;
    private Protocol protocol;
    private int clientFailsCount;
    private DateTime lastClientFail;
    private Map<SearchEngine, Integer> failsCount;
    private Map<SearchEngine, DateTime> scheduledUsage;

    public enum Protocol {
        HTTP,
        HTTPS,
        SOCKS5,
        SOCKS4
    }
}