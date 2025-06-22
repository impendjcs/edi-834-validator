package com.edi.validator.model;

public class LESegment extends EDISegment {
    public static final String SEGMENT_CODE = "LE";

    public LESegment(String line, int lineNumber) {
        super(SEGMENT_CODE, line, lineNumber);
    }

    public String getLoopIdentifierCode() {
        return getField(1);
    }
} 