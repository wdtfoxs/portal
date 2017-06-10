package com.code405.entity.enumeration;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
public enum Week {
    //четная, нечетная, каждая
    Четная("Четная"), Нечетная("Нечетная"), Каждая("Каждая");

    private final String type;

    Week(String s) {
        this.type = s;
    }

    public static Week getWeek() {
        Calendar cal = Calendar.getInstance();
        Date currentDate = new Date();
        cal.setTime(currentDate);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return (week % 2 == 0 ? Week.Четная : Week.Нечетная);
    }

    public String getType() {
        return type;
    }

}
