package com.date.util;

import java.util.regex.Pattern;

public class DateComparator {
    private static final Pattern DATE_MATCH_PATTERN = Pattern.compile("^[0-3]\\d\\/[0-1][0-2]\\/[1-2]\\d{3}$");

    public int getDateDifference(String fromDate, String toDate) throws IllegalArgumentException {
        return 1;
    }

    private boolean validateDateString(String dateString) {
        // Coarse date range matching (01/01/1901 - 31/12/2999)
        if (!DATE_MATCH_PATTERN.matcher(dateString).matches()) {
            return false;
        }
        // Month - day matching
        // Leap year matching
        return true;
    }

    public static void main(String[] args) {
        DateComparator comparator = new DateComparator();
        if (args.length == 2 && comparator.validateDateString(args[0]) && comparator.validateDateString(args[1])) {
            System.out.println(comparator.getDateDifference(args[0], args[1]));
            System.exit(0);
        } else {
            comparator.printUsage();
            System.exit(1);
        }

    }

    private void printUsage() {

    }
}
