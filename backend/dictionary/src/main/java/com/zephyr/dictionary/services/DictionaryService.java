package com.zephyr.dictionary.services;

import com.zephyr.commons.data.Keyword;
import com.zephyr.dictionary.services.dto.DictionaryDto;
import reactor.core.publisher.Flux;

public interface DictionaryService {

    Flux<DictionaryDto> update(Flux<Keyword> keywords);
}
