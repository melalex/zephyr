package com.zephyr.location.services.dto;

import com.zephyr.data.enums.Protocol;
import lombok.Data;
import org.springframework.hateoas.Identifiable;

@Data
public class ProxyDto implements Identifiable<String> {
    private String id;
    private String countryIso;
    private String ip;
    private int port;
    private Protocol protocol;
}