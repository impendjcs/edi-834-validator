package com.edi.validator.model;

public class ISASegment extends EDISegment {
    public static final String SEGMENT_CODE = "ISA";

    public ISASegment(String line, int lineNumber) {
        super(SEGMENT_CODE, line, lineNumber);
    }

    public String getSenderId() {
        return getField(5);
    }

    public String getReceiverId() {
        return getField(6);
    }

    public String getDate() {
        return getField(8);
    }

    public String getTime() {
        return getField(9);
    }

    public String getControlNumber() {
        return getField(12);
    }
} 