package com.zephyr.jobs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Locale;

@Data
@Component
@ConfigurationProperties("data")
public class MockDataProperties {

    private int batchSize;
    private long daysUptime;
    private FakerProperties faker;

    public Locale getLocale() {
        return new Locale(faker.getLang(), faker.getCountry());
    }

    public LocalDate getLaunchDate(LocalDate now) {
        return now.minusDays(daysUptime);
    }

    @Data
    public static class FakerProperties {

        private String lang;
        private String country;
    }
}
