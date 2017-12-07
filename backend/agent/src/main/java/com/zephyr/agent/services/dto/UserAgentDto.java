package com.zephyr.agent.services.dto;

import lombok.Data;

@Data
public class UserAgentDto {
    private String httpHeader;
    private String hardWareType;
    private String osName;
    private String osVersion;
    private String browserName;
    private String browserVersion;
    private String engineName;
    private String engineVersion;
}
