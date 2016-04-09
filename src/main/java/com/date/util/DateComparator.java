package com.date.util;

public class DateComparator {

    public static void main(String[] args) {
        DateComparator comparator = new DateComparator();
        try {
            MyDate fromDate = MyDate.of(args[0]);
            MyDate toDate = MyDate.of(args[1]);
            if (args.length == 2 && fromDate != null && toDate != null) {
                System.out.println(fromDate.getFullDaysTo(toDate));
                System.exit(0);
            } else {
                comparator.printUsage();
                System.exit(1);
            }
        } catch (Exception e) {
            comparator.printUsage();
            throw e;
        }
    }

    private void printUsage() {
        System.err.println("Enter dates between (01/01/1901 - 31/12/2999) in DD/MM/YYYY format");
    }
}
