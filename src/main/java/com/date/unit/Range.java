package com.date.unit;

import lombok.Getter;

@Getter
public final class Range {
    private final int minimumLowerValue;
    private final int minimumUpperValue;
    private final int maximumUpperValue;

    private Range(int minimumLowerValue, int minimumUpperValue, int maximumUpperValue) {
        if (minimumLowerValue >= minimumUpperValue)
            throw new RuntimeException("Minimum lower value of range should not be " +
                    "more than the minimum upper value");
        if (minimumUpperValue > maximumUpperValue)
            throw new RuntimeException("Minimum upper value of range should not be " +
                    "more than the maximum upper value");
        this.minimumLowerValue = minimumLowerValue;
        this.minimumUpperValue = minimumUpperValue;
        this.maximumUpperValue = maximumUpperValue;
    }

    public static Range of(int minimumValue, int maximumValue) {
        return new Range(minimumValue, maximumValue, maximumValue);
    }

    public static Range of(int minimumValue, int maximumValueLowerBound, int maximumValueUpperBound) {
        return new Range(minimumValue, maximumValueLowerBound, maximumValueUpperBound);
    }

    public boolean isValid(int value) {
        return value >= minimumLowerValue && value <= maximumUpperValue;
    }

    // Within safe limits of the value
    public boolean isSafeValue(int value) {
        return value >= minimumLowerValue && value <= minimumUpperValue;
    }
}
