package com.zephyr.data.protocol.vo;

import lombok.Value;

@Value(staticConstructor = "of")
public class KeywordVo {

    private String query;
    private String requestedUrl;
}
