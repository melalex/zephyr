package com.zephyr.rating.domain.vo;

import com.zephyr.commons.support.Indexed;
import com.zephyr.rating.domain.Request;
import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class SearchResultVo {

    private Request request;
    private List<Indexed<String>> links;
}
