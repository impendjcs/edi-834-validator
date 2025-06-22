package com.edi.validator.model;

public class SV1Segment extends EDISegment {
    public static final String SEGMENT_CODE = "SV1";

    public SV1Segment(String line, int lineNumber) {
        super(SEGMENT_CODE, line, lineNumber);
    }

    public String getCompositeMedicalProcedureIdentifier() {
        return getField(1);
    }

    public String getMonetaryAmount() {
        return getField(2);
    }

    public String getUnitBasisForMeasurementCode() {
        return getField(3);
    }

    public String getQuantity() {
        return getField(4);
    }

    public String getFacilityCodeValue() {
        return getField(5);
    }

    public String getServiceTypeCode() {
        return getField(6);
    }

    public String getDiagnosisCodePointer() {
        return getField(7);
    }
} 