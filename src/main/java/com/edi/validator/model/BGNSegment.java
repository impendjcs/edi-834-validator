package com.edi.validator.model;

public class BGNSegment extends EDISegment {
    public static final String SEGMENT_CODE = "BGN";

    public BGNSegment(String line, int lineNumber) {
        super(SEGMENT_CODE, line, lineNumber);
    }

    public String getTransactionSetPurposeCode() {
        return getField(1);
    }

    public String getReferenceIdentification() {
        return getField(2);
    }

    public String getDate() {
        return getField(4);
    }

    public String getTime() {
        return getField(5);
    }

    public String getTimeCode() {
        return getField(3);
    }

    public String getActionCode() {
        return getField(8);
    }
} 