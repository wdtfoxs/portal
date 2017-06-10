package com.code405.entity.enumeration;

import com.code405.entity.embeddable.Time;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;

/**
 * Created by hajrullinbulat on 21.05.17.
 */
public enum Semester {
    FIRST(1), SECOND(2), EVERY(3);

    private final int value;

    Semester(final int value) {
        this.value = value;
    }

    public static Semester valueOf(Integer semester) {
        switch (semester) {
            case 1:
                return FIRST;
            case 2:
                return SECOND;
            default:
                return EVERY;
        }
    }

    public static Semester getCurrentSemester() {
        LocalDate localDate = LocalDate.now();
        int value = localDate.getMonth().getValue();
        if (value >= 2 && value < 9) {
            return SECOND;
        }
        return FIRST;
    }

    public static Time getTimeForCurrentSemester() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        Time time = new Time();
        switch (getCurrentSemester()) {
            case FIRST:
                cal.set(year, Calendar.SEPTEMBER, 1);
                time.setFrom(new Timestamp(cal.getTime().getTime()));
                cal.set(year + 1, Calendar.JANUARY, 31);
                time.setTo(new Timestamp(cal.getTime().getTime()));
                return time;
            case SECOND:
                cal.set(year, Calendar.FEBRUARY, 1);
                time.setFrom(new Timestamp(cal.getTime().getTime()));
                cal.set(year, Calendar.AUGUST, 31);
                time.setTo(new Timestamp(cal.getTime().getTime()));
                return time;
            default:
                return time;
        }
    }

    public int getValue() {
        return value;
    }
}
