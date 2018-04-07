package com.zephyr.data.protocol.dto;

import lombok.Data;

@Data
public class UserAgentDto {

    private String id;
    private String header;
    private String device;
    private String osName;
    private String osVersion;
    private String browserName;
    private String browserVersion;
}
