package com.date.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.date.unit.DateCreationException;
import com.date.unit.TimeUnit;

import lombok.Getter;

@Getter
public final class MyDate {
    private final int day;
    private final int month;
    private final int year;

    // Use ENUMS to represent time units with min & max-value-ranges
    // DAYS - {1 - (28-31)}
    // MONTHS - {1 - 12}
    // YEARS - {1901 - 2999}
    private static final Pattern DATE_MATCH_PATTERN = Pattern.compile("^[0-3]\\d\\/[0-1]\\d\\/[1-2]\\d{3}$");
    private static final List<Integer> MONTHS_WITH_31_DAYS = Arrays.asList(1, 3, 5, 7, 8, 10, 12);
    private static final List<Integer> MONTHS_WITH_30_DAYS = Arrays.asList(9, 4, 6, 11);

    // Prevent external creation
    private MyDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    private static MyDate create(int day, int month, int year) {
        boolean isValidDate = false;
        if (!TimeUnit.DAY.isSafeValue(day)) {
            // Value lies in an ambiguous zone
            switch (month) {
                case 9:
                case 4:
                case 6:
                case 11:
                    // Months with 30 days
                    isValidDate = day <= 30;
                    break;
                case 2:
                    // February
                    isValidDate = isLeapYear(year) ? day <= 29 : day <= 28;
                    break;
                default:
                    // Months with 31 days
                    isValidDate = day <= 31;
                    break;
            }
            if (!isValidDate) {
                throw new DateCreationException("The month " + month + " of the year "
                        + year + " does not have " + day + " days");
            }
        }
        return new MyDate(day, month, year);
    }


    public static MyDate of(String dayString) {
        if (!DATE_MATCH_PATTERN.matcher(dayString).matches()) {
            throw new DateCreationException("Invalid date " + dayString);
        }
        String[] parts = dayString.split("/");
        int inputDay = Integer.valueOf(parts[0]);
        int inputMonth = Integer.valueOf(parts[1]);
        int inputYear = Integer.valueOf(parts[2]);
        // Straight forward validations for month and year
        TimeUnit.MONTH.validateValueInRange(inputMonth);
        TimeUnit.YEAR.validateValueInRange(inputYear);
        return create(inputDay, inputMonth, inputYear);
    }

    private static boolean isLeapYear(int inputYear) {
        return (inputYear % 400 == 0) || (inputYear % 4 == 0 && inputYear % 100 != 0);
    }

}
