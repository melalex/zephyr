package com.zephyr.data.protocol.request;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class KeywordRequest {

    @URL
    @NotNull
    private String url;

    @Min(0)
    private int page = 0;

    @Min(1)
    private int pageSize = 20;
}
