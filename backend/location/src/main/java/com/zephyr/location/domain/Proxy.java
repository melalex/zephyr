package com.zephyr.location.domain;

import com.zephyr.data.Protocol;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Proxy {

    @Id
    private String id;

    private String countryIso;
    private String ip;
    private int port;
    private Protocol protocol;
}