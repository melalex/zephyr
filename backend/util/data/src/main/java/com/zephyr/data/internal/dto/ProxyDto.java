package com.zephyr.data.internal.dto;

import com.zephyr.data.internal.enums.Protocol;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProxyDto {

    private String id;
    private String username;
    private String password;
    private String ip;
    private int port;
    private Protocol protocol;
    private LocalDateTime schedule;
}