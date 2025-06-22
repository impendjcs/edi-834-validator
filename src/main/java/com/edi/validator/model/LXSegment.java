package com.edi.validator.model;

public class LXSegment extends EDISegment {
    public static final String SEGMENT_CODE = "LX";

    public LXSegment(String line, int lineNumber) {
        super(SEGMENT_CODE, line, lineNumber);
    }

    public String getAssignedNumber() {
        return getField(1);
    }
} 