package com.zephyr.scraper.data;

import static java.util.List.of;
import static java.util.Map.entry;

import com.zephyr.commons.MapUtils;
import com.zephyr.test.Criteria;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public final class ScraperHeaders {

    private static final String DO_NOT_TRACK = "DNT";
    private static final String ENCODING = "gzip, deflate, br";
    private static final String KEEP_ALIVE = "keep-alive";
    private static final String TRUE = "1";
    private static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";
    private static final String ACCEPT_HTML = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static final String ACCEPT_AJAX = "*/*";

    private ScraperQueries queries;

    public Map<String, List<String>> htmlHeadersFull(String baseUrl, String userAgent) {
        return MapUtils.merge(defaultHeaders(userAgent), htmlHeaders(baseUrl));
    }

    public Map<String, List<String>> ajaxHeadersFull(String baseUrl, String userAgent) {
        return MapUtils.merge(defaultHeaders(userAgent), ajaxHeaders(baseUrl));
    }

    public Map<String, List<String>> htmlHeaders(String baseUrl) {
        return Map.ofEntries(
                entry(HttpHeaders.REFERER, of(baseUrl)),
                entry(HttpHeaders.ACCEPT, of(ACCEPT_HTML)),
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
        return defaultHeaders(queries.simple().getUserAgent().getHeader());
    }

    public Map<String, List<String>> defaultHeaders(String userAgent) {
        return Map.ofEntries(
                entry(HttpHeaders.USER_AGENT, of(userAgent)),
                entry(HttpHeaders.ACCEPT_LANGUAGE, of(Criteria.SIMPLE_LANGUAGE_ISO)),
                entry(HttpHeaders.ACCEPT_ENCODING, of(ENCODING)),
                entry(HttpHeaders.CONNECTION, of(KEEP_ALIVE)),
                entry(DO_NOT_TRACK, of(TRUE))
        );
    }
}
