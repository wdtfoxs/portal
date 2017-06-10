package com.code405.entity.enumeration;

/**
 * Created by hajrullinbulat on 10.05.17.
 */
public enum Class {
    ПЕРВАЯ("08.30-10.00"), ВТОРАЯ("10.10-11.40"), ТРЕТЬЯ("11.50-13.20"),
    ЧЕТВЕРТАЯ("13.35-15.05"), ПЯТАЯ("15.20-16.50"), ШЕСТАЯ("17.00-18.30"), СЕДЬМАЯ("18.40-20.10");

    private final String text;

    Class(final String text) {
        this.text = text;
    }

    public String getTime() {
        return text;
    }
}
