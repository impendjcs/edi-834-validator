package com.edi.validator.model;

public class DTPSegment extends EDISegment {
    public static final String SEGMENT_CODE = "DTP";

    public DTPSegment(String line, int lineNumber) {
        super(SEGMENT_CODE, line, lineNumber);
    }

    public String getDateTimeQualifier() {
        return getField(1);
    }

    public String getDateTimeFormatQualifier() {
        return getField(2);
    }

    public String getDate() {
        return getField(3);
    }
} 