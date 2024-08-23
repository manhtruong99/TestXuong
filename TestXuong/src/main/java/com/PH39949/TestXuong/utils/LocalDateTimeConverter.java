package com.PH39949.TestXuong.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.Instant;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Long> {

    @Override
    public Long convertToDatabaseColumn(LocalDateTime attribute) {
        return attribute != null ? attribute.toInstant(ZoneOffset.UTC).toEpochMilli() : null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Long dbData) {
        return dbData != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(dbData), ZoneOffset.UTC) : null;
    }
}


