package com.zephyr.dictionary.services;

import com.zephyr.commons.data.Keyword;
import com.zephyr.dictionary.services.dto.DictionaryDto;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;

public interface DictionaryService {

    Flux<DictionaryDto> update(Flux<Keyword> keywords);

    Flux<Keyword> findAllForUpdate(Sort sort);
}
