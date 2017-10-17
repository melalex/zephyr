package com.zephyr.scraper.domain;

import com.zephyr.data.enums.Protocol;
import lombok.Data;

@Data
public class Proxy {
    private String id;
    private String username;
    private String password;
    private String countryIso;
    private String ip;
    private int port;
    private Protocol protocol;
    private int failsCount;
}
