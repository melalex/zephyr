package com.zephyr.scraper.source.impl;

import com.zephyr.commons.MapUtils;
import com.zephyr.scraper.domain.Country;
import com.zephyr.scraper.source.CountrySource;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CountrySourceImpl implements CountrySource {

    // TODO: Refactor when spring cloud config be able to serve Resources

    @Value("classpath:data/countries.csv")
    private Resource resource;

    private Map<String, Country> countries;

    @PostConstruct
    public void init() {
        @Cleanup CSVParser parser = createParser();

        countries = StreamSupport.stream(parser.spliterator(), false)
                .map(r -> Country.of(r.get(0), r.get(1)))
                .collect(Collectors.toMap(Country::getIso, c -> c));
    }

    @Override
    public Country getByIso(String iso) {
        return MapUtils.getOrThrow(countries, iso);
    }

    @SneakyThrows
    private CSVParser createParser() {
        return CSVParser.parse(resource.getFile(), Charset.defaultCharset(), CSVFormat.DEFAULT);
    }
}