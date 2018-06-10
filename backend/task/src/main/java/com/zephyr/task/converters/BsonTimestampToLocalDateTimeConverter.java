package com.zephyr.task.converters;

import org.bson.BsonTimestamp;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class BsonTimestampToLocalDateTimeConverter implements Converter<BsonTimestamp, LocalDateTime> {

    @Override
    public LocalDateTime convert(@NonNull BsonTimestamp bsonTimestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(bsonTimestamp.getTime()), ZoneId.systemDefault());
    }
}
