package com.zephyr.scraper.data;

import static java.util.List.*;
import static java.util.Map.entry;

import com.zephyr.commons.MapUtils;
import com.zephyr.test.Queries;
import com.zephyr.test.UserAgents;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;

public final class ScraperHeaders {

    private static final String DO_NOT_TRACK = "DNT";
    private static final String ENCODING = "gzip, deflate, br";
    private static final String KEEP_ALIVE = "keep-alive";
    private static final String TRUE = "1";
    private static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";
    private static final String ACCEPT_HTML = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static final String ACCEPT_AJAX = "*/*";

    public Map<String, List<String>> htmlHeadersFull(String baseUrl) {
        return MapUtils.merge(defaultHeaders(), htmlHeaders(baseUrl));
    }

    public Map<String, List<String>> ajaxHeadersFull(String baseUrl) {
        return MapUtils.merge(defaultHeaders(), ajaxHeaders(baseUrl));
    }

    public Map<String, List<String>> htmlHeaders(String baseUrl) {
        return Map.ofEntries(
                entry(HttpHeaders.REFERER, of(ACCEPT_HTML)),
                entry(HttpHeaders.ACCEPT, of(baseUrl)),
                entry(UPGRADE_INSECURE_REQUESTS, of(TRUE))
        );
    }

    public Map<String, List<String>> ajaxHeaders(String baseUrl) {
        return Map.ofEntries(
                entry(HttpHeaders.ACCEPT, of(ACCEPT_AJAX)),
                entry(HttpHeaders.HOST, of(baseUrl)),
                entry(HttpHeaders.ORIGIN, of(baseUrl))
        );
    }

    public Map<String, List<String>> defaultHeaders() {
        return Map.ofEntries(
                entry(HttpHeaders.USER_AGENT, of(UserAgents.WINDOWS_FIREFOX_HEADER)),
                entry(HttpHeaders.ACCEPT_LANGUAGE, of(Queries.SIMPLE_LANGUAGE_ISO)),
                entry(HttpHeaders.ACCEPT_ENCODING, of(ENCODING)),
                entry(HttpHeaders.CONNECTION, of(KEEP_ALIVE)),
                entry(DO_NOT_TRACK, of(TRUE))
        );
    }
}
