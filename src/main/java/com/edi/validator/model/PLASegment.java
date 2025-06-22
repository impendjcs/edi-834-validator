package com.edi.validator.model;

public class PLASegment extends EDISegment {
    public static final String SEGMENT_CODE = "PLA";

    public PLASegment(String line, int lineNumber) {
        super(SEGMENT_CODE, line, lineNumber);
    }

    public String getActionCode() {
        return getField(1);
    }

    public String getEntityIdentifierCode() {
        return getField(2);
    }

    public String getDate() {
        return getField(3);
    }

    public String getTime() {
        return getField(4);
    }
} 