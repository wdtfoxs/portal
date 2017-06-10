package com.code405.entity.enumeration;

import com.code405.entity.embeddable.Time;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hajrullinbulat on 20.05.17.
 */
public enum Course {
    ПЕРВЫЙ("1 курс"), ВТОРОЙ("2 курс"), ТРЕТИЙ("3 курс"), ЧЕТВЕРТЫЙ("4 курс"), UNDEFINED("ошибка");

    private static final List<Integer> forFirstSemester = Arrays.asList(8, 9, 10, 11);
    private static final List<Integer> forSecondSemester = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
    private final String text;

    Course(final String text) {
        this.text = text;
    }

    public static Time getTimeForCourse(Timestamp admissionDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(admissionDate);
        Time time = new Time();
        switch (getCourse(admissionDate)) {
            case ПЕРВЫЙ:
                time.setFrom(admissionDate);
                cal.add(Calendar.YEAR, 1);
                cal.add(Calendar.DATE, -1);
                time.setTo(new Timestamp(cal.getTime().getTime()));
                break;
            case ВТОРОЙ:
                cal.add(Calendar.YEAR, 1);
                time.setFrom(new Timestamp(cal.getTime().getTime()));
                cal.add(Calendar.YEAR, 1);
                cal.add(Calendar.DATE, -1);
                time.setTo(new Timestamp(cal.getTime().getTime()));
                break;
            case ТРЕТИЙ:
                cal.add(Calendar.YEAR, 2);
                time.setFrom(new Timestamp(cal.getTime().getTime()));
                cal.add(Calendar.YEAR, 1);
                cal.add(Calendar.DATE, -1);
                time.setTo(new Timestamp(cal.getTime().getTime()));
                break;
            case ЧЕТВЕРТЫЙ:
                cal.add(Calendar.YEAR, 3);
                time.setFrom(new Timestamp(cal.getTime().getTime()));
                cal.add(Calendar.YEAR, 1);
                cal.add(Calendar.DATE, -1);
                time.setTo(new Timestamp(cal.getTime().getTime()));
                break;
        }
        return time;
    }

    public static Course getCourse(Integer course) {
        switch (course) {
            case 1:
                return ПЕРВЫЙ;
            case 2:
                return ВТОРОЙ;
            case 3:
                return ТРЕТИЙ;
            case 4:
                return ЧЕТВЕРТЫЙ;
            default:
               return UNDEFINED;
        }
    }

    public static Course getCourse(Timestamp admissionDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(admissionDate);
        int admissionYear = cal.get(Calendar.YEAR);
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int yearDiff = year - admissionYear;
        switch (yearDiff) {
            case 0:
                return Course.ПЕРВЫЙ;
            case 1:
                if (forFirstSemester.contains(month)) {
                    return Course.ВТОРОЙ;
                }
                if (forSecondSemester.contains(month)) {
                    return Course.ПЕРВЫЙ;
                }
                return Course.UNDEFINED;
            case 2:
                if (forFirstSemester.contains(month)) {
                    return Course.ТРЕТИЙ;
                }
                if (forSecondSemester.contains(month)) {
                    return Course.ВТОРОЙ;
                }
                return Course.UNDEFINED;
            case 3:
                if (forFirstSemester.contains(month)) {
                    return Course.ЧЕТВЕРТЫЙ;
                }
                if (forSecondSemester.contains(month)) {
                    return Course.ТРЕТИЙ;
                }
                return Course.UNDEFINED;
            case 4:
                return Course.ЧЕТВЕРТЫЙ;
            default:
                return Course.UNDEFINED;
        }
    }

    public String getTime() {
        return text;
    }

}
