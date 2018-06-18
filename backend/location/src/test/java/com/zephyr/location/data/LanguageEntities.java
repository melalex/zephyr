package com.zephyr.location.data;

import com.zephyr.location.domain.Language;
import com.zephyr.test.Languages;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class LanguageEntities {

    public Language ukrainian() {
        var result = new Language();
        result.setIso(Languages.UKRAINIAN_ISO);
        result.setName(Languages.UKRAINIAN_NAME);
        return result;
    }

    public Language russian() {
        var result = new Language();
        result.setIso(Languages.RUSSIAN_ISO);
        result.setName(Languages.RUSSIAN_NAME);
        return result;
    }

    public Language english() {
        var result = new Language();
        result.setIso(Languages.ENGLISH_ISO);
        result.setName(Languages.ENGLISH_NAME);
        return result;
    }
}
