package com.zephyr.data.dto;

import lombok.Data;

@Data
public class UserAgentDto {
    private String id;
    private String httpHeader;
    private String hardWareType;
    private String osName;
    private String osVersion;
    private String browserName;
    private String browserVersion;
    private String engineName;
    private String engineVersion;
}
