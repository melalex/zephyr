package com.zephyr.location.domain;

import lombok.Data;

@Data
public class UserAgent {
    private String software;
    private String userAgent;
    private String version;
    private String operationSystem;
    private String hardWare;
}
