package com.zephyr.keyword.protocol;

import lombok.Data;

@Data
public class KeywordResponse {
    private String query;
    private String requestedUrl;
}
