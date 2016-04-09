package com.date.unit;

public enum TimeUnit {
    DAY(Range.of(1, 28, 31)),
    MONTH(Range.of(1, 12)),
    YEAR(Range.of(1901, 2999));

    TimeUnit(Range valueRange) {
        this.valueRange = valueRange;
    }

    private final Range valueRange;

    public void validateValueInRange(int value) {
        if (!valueRange.isValid(value))
            throw new DateCreationException("Illegal value for " + this.name());

    }

    public boolean isSafeValue(int value) {
        return valueRange.isSafeValue(value);
    }
}
