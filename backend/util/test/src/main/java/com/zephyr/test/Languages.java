package com.zephyr.test;

import com.zephyr.data.protocol.dto.LanguageDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class Languages {

    public static final String UKRAINIAN_ISO = "uk";
    public static final String UKRAINIAN_NAME = "Ukrainian";
    public static final String RUSSIAN_ISO = "ru";
    public static final String RUSSIAN_NAME = "Russian";
    public static final String ENGLISH_ISO = "en";
    public static final String ENGLISH_NAME = "English";

    public LanguageDto ukrainian() {
        return new LanguageDto(UKRAINIAN_ISO, UKRAINIAN_NAME);
    }

    public LanguageDto russian() {
        return new LanguageDto(RUSSIAN_ISO, RUSSIAN_NAME);
    }

    public LanguageDto english() {
        return new LanguageDto(ENGLISH_ISO, ENGLISH_NAME);
    }
}
