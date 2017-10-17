package com.zephyr.scraper.source;

import com.zephyr.scraper.domain.Country;

public interface CountrySource {

    Country getByIso(String iso);
}
