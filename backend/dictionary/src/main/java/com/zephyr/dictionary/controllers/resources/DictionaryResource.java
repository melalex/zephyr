package com.zephyr.dictionary.controllers.resources;

import com.zephyr.data.Keyword;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.time.DateTime;
import org.springframework.hateoas.ResourceSupport;

@Data
@EqualsAndHashCode(callSuper = true)
public class DictionaryResource extends ResourceSupport {
    private String id;
    private Keyword keyword;
    private DateTime lastHit;
    private long hitsCount;
}