package com.zephyr.data;

import lombok.Data;

@Data
public class ProxyDto {
    private String id;
    private String countryIso;
    private String ip;
    private int port;
    private Protocol protocol;
}
