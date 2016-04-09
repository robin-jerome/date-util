package com.date.unit;

import java.util.regex.Pattern;

import com.date.util.DateCreationException;

import lombok.Getter;

@Getter
public final class SimpleDate {
    private final int day;
    private final int month;
    private final int year;

    // Use ENUMS to represent time units with min & max-value-ranges
    // DAYS - {1 - (28-31)}
    // MONTHS - {1 - 12}
    // YEARS - {1901 - 2999}
    private static final Pattern DATE_MATCH_PATTERN = Pattern.compile("^[0-3]\\d\\/[0-1]\\d\\/[1-2]\\d{3}$");

    // Prevent external creation
    private SimpleDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    private static SimpleDate create(int day, int month, int year) {
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
        return new SimpleDate(day, month, year);
    }

    public boolean isAfter(SimpleDate other) {
        return getDayCountSinceEpoch() > other.getDayCountSinceEpoch();
    }

    public int getFullDaysTo(SimpleDate toDate) {
        int dayCountDiff = toDate.getDayCountSinceEpoch() - getDayCountSinceEpoch();
        if (dayCountDiff > 1) {
            return dayCountDiff - 1;
        } else {
            return 0;
        }
    }

    private int getDayCountSinceEpoch() {
        int dayCountSinceEpoch = getStartDayCountOfYear();
        for (int i = 1; i < month; i++) {
            dayCountSinceEpoch += getLength(i, TimeUnit.MONTH);
        }
        dayCountSinceEpoch += day;
        return dayCountSinceEpoch;
    }

    private int getStartDayCountOfYear() {
        int fourCenturyCycles = year / 400;
        int fourYearCycles = year / 4;
        int hundredYearCycles = year / 100;
        int totalLeapYears = fourYearCycles - hundredYearCycles + fourCenturyCycles;
        int nonLeapYears = year - totalLeapYears;
        return nonLeapYears * 365 + totalLeapYears  * 366;
    }

    private static int getLength(int value, TimeUnit timeUnit) {
        switch (timeUnit) {
            case DAY:
                return value;
            case YEAR:
                return isLeapYear(value) ? 366 : 365;
            default:
                switch (value) {
                    case 9:
                    case 4:
                    case 6:
                    case 11:
                        return 30;
                    case 2:
                        return isLeapYear(value) ? 29 : 28;
                    default:
                        return 31;
                }
        }
    }

    public static SimpleDate of(String dayString) {
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
        return inputYear % 400 == 0 || (inputYear % 4 == 0 && inputYear % 100 != 0);
    }

}
