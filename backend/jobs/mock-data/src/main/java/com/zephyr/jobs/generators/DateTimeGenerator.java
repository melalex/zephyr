package com.zephyr.jobs.generators;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DateTimeGenerator {

    LocalDate generateDate();

    LocalDate generateDate(LocalDate to);

    LocalDateTime generateDateTime();

    LocalDateTime generateDateTime(LocalDateTime to);
}
