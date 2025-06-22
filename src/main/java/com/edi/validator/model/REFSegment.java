package com.edi.validator.model;

public class REFSegment extends EDISegment {
    public static final String SEGMENT_CODE = "REF";

    public REFSegment(String line, int lineNumber) {
        super(SEGMENT_CODE, line, lineNumber);
    }

    public String getReferenceIdentificationQualifier() {
        return getField(1);
    }

    public String getReferenceIdentification() {
        return getField(2);
    }

    public String getDescription() {
        return getField(3);
    }

    public String getReferenceIdentifier() {
        return getField(4);
    }
} 