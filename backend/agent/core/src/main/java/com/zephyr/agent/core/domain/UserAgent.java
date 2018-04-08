package com.zephyr.agent.core.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class UserAgent {

    @Id
    private String id;
    private String header;
    private String device;
    private String osName;
    private String osVersion;
    private String browserName;
    private String browserVersion;
}
