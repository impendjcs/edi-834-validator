package com.edi.validator.model;

public class SV2Segment extends EDISegment {
    public static final String SEGMENT_CODE = "SV2";

    public SV2Segment(String line, int lineNumber) {
        super(SEGMENT_CODE, line, lineNumber);
    }

    public String getServiceLineRevenueCode() {
        return getField(1);
    }

    public String getCompositeMedicalProcedureIdentifier() {
        return getField(2);
    }

    public String getMonetaryAmount() {
        return getField(3);
    }

    public String getUnitBasisForMeasurementCode() {
        return getField(4);
    }

    public String getQuantity() {
        return getField(5);
    }

    public String getFacilityCodeValue() {
        return getField(6);
    }

    public String getServiceTypeCode() {
        return getField(7);
    }

    public String getDiagnosisCodePointer() {
        return getField(8);
    }
} 