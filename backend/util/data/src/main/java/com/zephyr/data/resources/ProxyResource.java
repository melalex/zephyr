package com.zephyr.data.resources;

import com.zephyr.data.enums.Protocol;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProxyResource extends ResourceSupport {
    private String id;
    private String countryIso;
    private String ip;
    private int port;
    private Protocol protocol;
}
