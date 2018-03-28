package com.zephyr.task.domain.criteria;

import lombok.Data;

@Data
public class UserAgentCriteria {
    private String device;
    private String osName;
    private String browserName;
}
