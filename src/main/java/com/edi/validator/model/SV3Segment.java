package com.edi.validator.model;

public class SV3Segment extends EDISegment {
    public static final String SEGMENT_CODE = "SV3";

    public SV3Segment(String line, int lineNumber) {
        super(SEGMENT_CODE, line, lineNumber);
    }

    public String getCompositeMedicalProcedureIdentifier() {
        return getField(1);
    }

    public String getMonetaryAmount() {
        return getField(2);
    }

    public String getFacilityCodeValue() {
        return getField(3);
    }

    public String getServiceTypeCode() {
        return getField(4);
    }

    public String getDiagnosisCodePointer() {
        return getField(5);
    }
} 