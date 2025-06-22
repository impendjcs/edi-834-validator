package com.edi.validator.model;

public class GSSegment extends EDISegment {
    public static final String SEGMENT_CODE = "GS";

    public GSSegment(String line, int lineNumber) {
        super(SEGMENT_CODE, line, lineNumber);
    }

    public String getFunctionalIdentifierCode() {
        return getField(1);
    }

    public String getSenderId() {
        return getField(2);
    }

    public String getReceiverId() {
        return getField(3);
    }

    public String getDate() {
        return getField(4);
    }

    public String getTime() {
        return getField(5);
    }

    public String getGroupControlNumber() {
        return getField(6);
    }

    public String getResponsibleAgency() {
        return getField(7);
    }

    public String getVersion() {
        return getField(8);
    }
} 