package com.edi.validator.model;

public class STSegment extends EDISegment {
    public static final String SEGMENT_CODE = "ST";

    public STSegment(String line, int lineNumber) {
        super(SEGMENT_CODE, line, lineNumber);
    }

    public String getTransactionSetId() {
        return getField(1);
    }

    public String getControlNumber() {
        return getField(2);
    }

    public String getVersion() {
        return getField(3);
    }
} 