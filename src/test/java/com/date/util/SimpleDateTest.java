package com.date.util;

import static junit.framework.TestCase.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import com.date.unit.SimpleDate;

public class SimpleDateTest {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Test
    public void testValidityOfDatesInRange() throws Exception {
        LocalDate minDate = LocalDate.of(1901, 1, 1);
        LocalDate maxDate = LocalDate.of(2999, 12, 31);
        // All dates between (01/01/1901 - 31/12/2999)
        while (minDate.isBefore(maxDate) || minDate.equals(maxDate)) {
            String dateAsString = toDateString(minDate);
            SimpleDate.of(dateAsString);
            minDate = minDate.plusDays(1);
        }
    }

    @Test
    public void testDateDifferences() throws Exception {
        assertEquals(19, SimpleDate.of("02/06/1983").getFullDaysTo(SimpleDate.of("22/06/1983")));
        assertEquals(173, SimpleDate.of("04/07/1984").getFullDaysTo(SimpleDate.of("25/12/1984")));
        assertEquals(1979, SimpleDate.of("03/08/1983").getFullDaysTo(SimpleDate.of("03/01/1989")));
        assertEquals(0, SimpleDate.of("03/08/1983").getFullDaysTo(SimpleDate.of("03/08/1983")));
        assertEquals(0, SimpleDate.of("03/08/1983").getFullDaysTo(SimpleDate.of("04/08/1983")));
    }

    @Test
    public void zeroPaddedDatesAreAccepted() throws Exception {
        SimpleDate.of("01/01/1901");
    }

    @Test(expected = DateCreationException.class)
    public void constructingNonZeroPaddedDatesThrowException() throws Exception {
        SimpleDate.of("1/1/1901");
    }

    @Test(expected = DateCreationException.class)
    public void constructingDatesAfterRangeThrowsException() throws Exception {
        LocalDate maxDate = LocalDate.of(2999, 12, 31);
        SimpleDate.of(toDateString(maxDate.plusDays(1)));
    }

    @Test(expected = DateCreationException.class)
    public void constructingDatesBeforeRangeThrowsException() throws Exception {
        LocalDate minDate = LocalDate.of(1901, 1, 1);
        SimpleDate.of(toDateString(minDate.minusDays(1)));
    }


    @Test(expected = DateCreationException.class)
    public void malformedDatesAsInputThrowException() throws Exception {
        SimpleDate.of("01-01-1901");
    }

    private String toDateString(LocalDate localDate) {
        return DATE_FORMATTER.format(localDate);
    }
}