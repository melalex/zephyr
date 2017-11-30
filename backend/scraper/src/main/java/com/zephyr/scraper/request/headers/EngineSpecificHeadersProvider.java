package com.zephyr.scraper.request.headers;

import java.util.List;
import java.util.Map;

public interface EngineSpecificHeadersProvider {

    Map<String, List<String>> provide(String baseUrl);
}
