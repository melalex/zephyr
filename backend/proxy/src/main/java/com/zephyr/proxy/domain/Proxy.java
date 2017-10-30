package com.zephyr.proxy.domain;

import com.zephyr.data.enums.Protocol;
import com.zephyr.data.enums.SearchEngine;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Document
public class Proxy {

    @Version
    private long version;

    @Id
    private String id;
    private String username;
    private String password;
    private String countryIso;
    private String ip;
    private int port;
    private Protocol protocol;
    private Map<SearchEngine, Integer> failsCount;
    private Map<SearchEngine, LocalDateTime> scheduledUsage;
}
