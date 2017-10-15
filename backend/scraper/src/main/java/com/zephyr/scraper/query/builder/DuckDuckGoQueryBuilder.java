package com.zephyr.scraper.query.builder;

import com.zephyr.scraper.query.util.QueryUtil;
import lombok.NoArgsConstructor;

@NoArgsConstructor(staticName = "defaultEngine")
public class DuckDuckGoQueryBuilder {
    private String query;
    private int safeSearch = -2;
    private int autoLoad = 1;

    public DuckDuckGoQueryBuilder query(String query) {
        this.query = query;
        return this;
    }

    public DuckDuckGoQueryBuilder safeSearch(int safeSearch) {
        this.safeSearch = safeSearch;
        return this;
    }

    public DuckDuckGoQueryBuilder autoLoad(int autoLoad) {
        this.autoLoad = autoLoad;
        return this;
    }

    public String build() {
        return "https://duckduckgo.com/" +
                "?q=" + QueryUtil.encode(query) +
                "&kp=" + safeSearch +
                "&kc=" + autoLoad +
                "&t=h_" +
                "&ia=web";
    }
}
