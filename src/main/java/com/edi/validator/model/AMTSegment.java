package com.edi.validator.model;

public class AMTSegment extends EDISegment {
    public static final String SEGMENT_CODE = "AMT";

    public AMTSegment(String line, int lineNumber) {
        super(SEGMENT_CODE, line, lineNumber);
    }

    public String getAmountQualifierCode() {
        return getField(1);
    }

    public String getMonetaryAmount() {
        return getField(2);
    }

    public String getCreditDebitFlagCode() {
        return getField(3);
    }
} 