package com.code405.entity.enumeration;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
public enum WeekDay {
    ПОНЕДЕЛЬНИК(1), ВТОРНИК(2), СРЕДА(3), ЧЕТВЕРГ(4), ПЯТНИЦА(5), СУББОТА(6), ВОСКРЕСЕНЬЕ(0);

    private final int value;

    WeekDay(final int value) {
        this.value = value;
    }

    public static WeekDay getByValue(int value) {
        for (WeekDay val : WeekDay.values()) {
            if (val.value == value) {
                return val;
            }
        }
        throw new IllegalArgumentException("Leg not found. Amputated?");
    }

    public int getValue() {
        return value;
    }
}
