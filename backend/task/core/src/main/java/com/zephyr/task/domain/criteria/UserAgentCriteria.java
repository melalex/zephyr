package com.zephyr.task.domain.criteria;

import lombok.Data;

@Data
public class UserAgentCriteria {
    private String osName;
    private String osVersion;
    private String browserName;
    private String browserVersion;
}
