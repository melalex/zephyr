package com.zephyr.test;

import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.test.mocks.ClockMock;
import com.zephyr.test.mocks.UidProviderMock;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class SearchResults {

    public static final List<String> BING_LINKS = List.of(
            "https://en.wikipedia.org/wiki/Zephyr",
            "https://www.merriam-webster.com/dictionary/zephyr",
            "https://www.getzephyr.com/",
            "http://zephyronline.com/",
            "https://www.zephyranywhere.com/",
            "http://www.dictionary.com/browse/zephyr",
            "https://www.zephyrproject.org/",
            "https://zephyrre.com/",
            "https://www.getzephyr.com/insights",
            "https://marketplace.atlassian.com/plugins/com.thed.zephyr.je/cloud/overview"
    );

    public static final List<String> GOOGLE_LINKS = List.of(
            "https://www.getzephyr.com/",
            "https://www.zephyrproject.org/",
            "https://marketplace.atlassian.com/plugins/com.thed.zephyr.je/cloud/overview",
            "https://en.wikipedia.org/wiki/Zephyr",
            "https://en.wikipedia.org/wiki/Zephyr_(protocol)",
            "https://www.merriam-webster.com/dictionary/zephyr",
            "www.dictionary.com/browse/zephyr",
            "https://www.crunchbase.com/organization/zephyr",
            "leagueoflegends.wikia.com/wiki/Zephyr"
    );

    public static final List<String> YAHOO_LINKS = List.of(
            "zephyronline.com",
            "www.merriam-webster.com/dictionary/zephyr",
            "en.wikipedia.org/wiki/Zephyr",
            "www.dictionary.com/browse/zephyr",
            "www.getzephyr.com",
            "www.thefreedictionary.com/zephyr",
            "www.zephyrproject.org",
            "www.amtrak.com/california-zephyr-train",
            "zephyrre.com",
            "www.zhats.com"
    );

    private Queries queries;

    public SearchResultDto bing() {
        SearchResultDto result = base();
        result.setProvider(SearchEngine.BING);
        result.setLinks(BING_LINKS);

        return result;
    }

    public SearchResultDto google() {
        SearchResultDto result = base();
        result.setProvider(SearchEngine.GOOGLE);
        result.setLinks(GOOGLE_LINKS);

        return result;
    }

    public SearchResultDto yahoo() {
        SearchResultDto result = base();
        result.setProvider(SearchEngine.YAHOO);
        result.setLinks(YAHOO_LINKS);

        return result;
    }

    private SearchResultDto base() {
        SearchResultDto result = new SearchResultDto();
        result.setId(UidProviderMock.DEFAULT_ID);
        result.setOffset(0);
        result.setTimestamp(ClockMock.now());
        result.setQuery(queries.simple());
        return result;
    }
}