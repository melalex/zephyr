package com.zephyr.rating.domain;

import lombok.Data;

@Data
public class UserAgent {

    private String device;
    private String osName;
    private String browserName;
}
