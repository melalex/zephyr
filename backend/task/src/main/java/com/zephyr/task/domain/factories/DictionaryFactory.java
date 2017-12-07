package com.zephyr.task.domain.factories;

import com.zephyr.data.commons.Keyword;
import com.zephyr.task.domain.Dictionary;
import org.joda.time.DateTime;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

@Component
public class DictionaryFactory {

    public Dictionary newDictionary(Keyword keyword) {
        Dictionary dictionary = new Dictionary();

        dictionary.setKeyword(keyword);
        dictionary.setLastHit(DateTime.now());
        dictionary.setHitsCount(1);

        return dictionary;
    }

    public Example<Dictionary> newDictionaryExample(Keyword keyword) {
        Dictionary dictionary = new Dictionary();

        dictionary.setKeyword(keyword);

        return Example.of(dictionary);
    }
}
