package com.date.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import com.date.unit.DateCreationException;

public class MyDateTest {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Test
    public void testValidityOfDatesInRange() throws Exception {
        LocalDate minDate = LocalDate.of(1901, 1, 1);
        LocalDate maxDate = LocalDate.of(2999, 12, 31);
        // All dates between (01/01/1901 - 31/12/2999)
        while (minDate.isBefore(maxDate) || minDate.equals(maxDate)) {
            String dateAsString = toDateString(minDate);
            MyDate.of(dateAsString);
            minDate = minDate.plusDays(1);
        }
    }

    @Test
    public void zeroPaddedDatesAreAccepted() throws Exception {
        MyDate.of("01/01/1901");
    }

    @Test(expected = DateCreationException.class)
    public void constructingNonZeroPaddedDatesThrowException() throws Exception {
        MyDate.of("1/1/1901");
    }

    @Test(expected = DateCreationException.class)
    public void constructingDatesAfterRangeThrowsException() throws Exception {
        LocalDate maxDate = LocalDate.of(2999, 12, 31);
        MyDate.of(toDateString(maxDate.plusDays(1)));
    }

    // TODO : Fix functionality
    @Test(expected = DateCreationException.class)
    public void constructingDatesBeforeRangeThrowsException() throws Exception {
        LocalDate minDate = LocalDate.of(1901, 1, 1);
        MyDate.of(toDateString(minDate.minusDays(1)));
    }


    @Test(expected = DateCreationException.class)
    public void malformedDatesAsInputThrowException() throws Exception {
        MyDate.of("01-01-1901");
    }

    private String toDateString(LocalDate localDate) {
        return DATE_FORMATTER.format(localDate);
    }
}