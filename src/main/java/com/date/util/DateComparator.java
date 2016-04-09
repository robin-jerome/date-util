package com.date.util;

import com.date.unit.SimpleDate;

public class DateComparator {

    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                printUsage();
                System.exit(1);
            }
            SimpleDate fromDate = SimpleDate.of(args[0]);
            SimpleDate toDate = SimpleDate.of(args[1]);
            if (toDate.isAfter(fromDate)) {
                System.out.println(fromDate.getFullDaysTo(toDate));
            } else {
                System.out.println(toDate.getFullDaysTo(fromDate));
            }
        } catch (DateCreationException e) {
            printUsage();
            throw e;
        }
    }

    private static void printUsage() {
        System.err.println("Please provide the correct arguments, eg.");
        System.err.println("java -jar date-util-1.0.jar 01/01/1901 25/01/1901");
    }

}
