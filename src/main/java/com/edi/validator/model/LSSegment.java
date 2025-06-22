package com.edi.validator.model;

public class LSSegment extends EDISegment {
    public static final String SEGMENT_CODE = "LS";

    public LSSegment(String line, int lineNumber) {
        super(SEGMENT_CODE, line, lineNumber);
    }

    public String getLoopIdentifierCode() {
        return getField(1);
    }
} 