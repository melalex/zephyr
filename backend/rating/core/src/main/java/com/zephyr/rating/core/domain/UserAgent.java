package com.zephyr.rating.core.domain;

import lombok.Data;

@Data
public class UserAgent {

    private String osName;
    private String osVersion;
    private String browserName;
    private String browserVersion;
}
