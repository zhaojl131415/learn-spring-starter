package com.zhao.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class StringConvertDate implements Converter<String, Date> {

    String dateFormat;

    public StringConvertDate(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public Date convert(String s) {
        LocalDateTime parse = LocalDateTime.parse(s, DateTimeFormatter.ofPattern(dateFormat));
        return Date.from(parse.atZone(ZoneId.systemDefault()).toInstant());
    }
}
