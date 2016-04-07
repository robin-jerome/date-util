package com.date.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public final class MyDate {
    private int day;
    private int month;
    private int year;

    // Use ENUMS to represent time units with min & max-value-ranges
    // DAYS - {1 - (28-31)}
    // MONTHS - {1 - 12}
    // YEARS - {1901 - 2999}

    private static final Pattern DATE_MATCH_PATTERN = Pattern.compile("^[0-3]\\d\\/[0-1][0-2]\\/[1-2]\\d{3}$");
    private static final List<Integer> MONTHS_WITH_31_DAYS = Arrays.asList(1, 3, 5, 7, 8, 10, 12);
    private static final List<Integer> MONTHS_WITH_30_DAYS = Arrays.asList(9, 4, 6, 11);

    // Prevent external creation
    private MyDate(List<Integer> parts) {
        this.day = parts.get(0);
        this.month = parts.get(1);
        this.year = parts.get(2);
    }

    static MyDate makeFromString(String dayString) {
        if (!DATE_MATCH_PATTERN.matcher(dayString).matches()) {
            throw new IllegalArgumentException("Invalid date " + dayString);
        }
        List<Integer> parts = Arrays.asList(dayString.split("/"))
                .stream()
                .map(s -> Integer.valueOf(s))
                .collect(Collectors.toList());
        return new MyDate(parts);
    }

    public boolean isValid() {
        boolean valid = true;
        if (MONTHS_WITH_30_DAYS.contains(month) && day > 30) {
            valid = false;
        } else if (MONTHS_WITH_31_DAYS.contains(month) && day > 31) {
            valid = false;
        } else {
            if (isLeapYear() && day > 29) {
                valid = false;
            } else if (day > 28) {
                valid = false;
            }
        }
        return valid;
    }

    private boolean isLeapYear() {
        return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
    }

    private void decrementYear() {
        year--;
    }

    private void decrementMonth() {
        month--;
    }

    private void decrementDay() {
        day--;
    }

    public int getFullDaysTo(MyDate toDate) {
        MyDate dateCopy = toDate;
        int dayCount = 0;
        while (dateCopy.getYear() > getYear()) {
            dayCount += dateCopy.isLeapYear() ? 366 : 365;
            dateCopy.decrementYear();
        }

        if (toDate.getYear() > year) {

        }
        return dayCount;
    }
}
