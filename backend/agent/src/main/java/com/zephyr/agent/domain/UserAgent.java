package com.zephyr.agent.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserAgent {

    @Id
    private String httpHeader;
    private String hardWareType;
    private String osName;
    private String osVersion;
    private String browserName;
    private String browserVersion;
    private String engineName;
    private String engineVersion;
}
