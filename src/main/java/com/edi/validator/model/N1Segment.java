package com.edi.validator.model;

public class N1Segment extends EDISegment {
    public static final String SEGMENT_CODE = "N1";

    public N1Segment(String line, int lineNumber) {
        super(SEGMENT_CODE, line, lineNumber);
    }

    public String getEntityIdentifierCode() {
        return getField(1);
    }

    public String getName() {
        return getField(2);
    }

    public String getIdentificationCodeQualifier() {
        return getField(3);
    }

    public String getIdentificationCode() {
        return getField(4);
    }
} 