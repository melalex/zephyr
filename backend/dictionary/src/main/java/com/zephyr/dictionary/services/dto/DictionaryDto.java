package com.zephyr.dictionary.services.dto;

import com.zephyr.data.Keyword;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.hateoas.Identifiable;

@Data
public class DictionaryDto implements Identifiable<String> {
    private String id;
    private Keyword keyword;
    private DateTime lastHit;
    private long hitsCount;
}
