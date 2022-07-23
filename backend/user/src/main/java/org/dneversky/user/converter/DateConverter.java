package org.dneversky.user.converter;

import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public final class DateConverter {

    public static LocalDate convert(Timestamp timestamp) {
        return Instant
                .ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos())
                .atZone(ZoneId.of("Europe/Moscow"))
                .toLocalDate();
    }

    public static Timestamp convert(LocalDate localDate) {
        long registeredDays = localDate.toEpochDay();
        return Timestamp.newBuilder().setSeconds(registeredDays * 24 * 60 * 60).build();
    }
}
