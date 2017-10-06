package com.zephyr.dictionary.services.dto;

import com.zephyr.commons.data.Keyword;
import lombok.Data;
import org.joda.time.DateTime;

@Data
public class DictionaryDto {
    private String id;
    private Keyword keyword;
    private DateTime lastHit;
    private long hitsCount;
}
