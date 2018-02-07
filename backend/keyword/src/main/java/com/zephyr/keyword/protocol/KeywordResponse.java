package com.zephyr.keyword.protocol;

import lombok.Value;

@Value(staticConstructor = "of")
public class KeywordResponse {
    private String query;
    private String requestedUrl;
}
