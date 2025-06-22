package com.edi.validator.model;

public class NM1Segment extends EDISegment {
    public static final String SEGMENT_CODE = "NM1";

    public NM1Segment(String line, int lineNumber) {
        super(SEGMENT_CODE, line, lineNumber);
    }

    public String getEntityIdentifierCode() {
        return getField(1);
    }

    public String getEntityTypeQualifier() {
        return getField(2);
    }

    public String getNameLast() {
        return getField(3);
    }

    public String getNameFirst() {
        return getField(4);
    }

    public String getNameMiddle() {
        return getField(5);
    }

    public String getNamePrefix() {
        return getField(6);
    }

    public String getNameSuffix() {
        return getField(7);
    }

    public String getIdentificationCodeQualifier() {
        return getField(8);
    }

    public String getIdentificationCode() {
        return getField(9);
    }

    public String getEntityRelationshipCode() {
        return getField(10);
    }

    public String getEntityIdentifierCode2() {
        return getField(11);
    }

    public String getNameLastOrOrganizationName() {
        return getField(12);
    }
} 