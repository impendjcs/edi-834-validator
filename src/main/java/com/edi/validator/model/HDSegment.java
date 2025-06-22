package com.edi.validator.model;

public class HDSegment extends EDISegment {
    public static final String SEGMENT_CODE = "HD";

    public HDSegment(String line, int lineNumber) {
        super(SEGMENT_CODE, line, lineNumber);
    }

    public String getMaintenanceTypeCode() {
        return getField(1);
    }

    public String getInsuranceLineCode() {
        return getField(2);
    }

    public String getCoverageType() {
        return getField(3);
    }

    public String getPlanCoverageDescription() {
        return getField(4);
    }

    public String getCoverageLevelCode() {
        return getField(5);
    }

    public String getCount() {
        return getField(6);
    }

    public String getUnderwritingDecisionCode() {
        return getField(7);
    }

    public String getYesNoConditionOrResponseCode() {
        return getField(8);
    }

    public String getDrugHouseCode() {
        return getField(9);
    }

    public String getYesNoConditionOrResponseCode2() {
        return getField(10);
    }

    public String getPlanCoverageDescription2() {
        return getField(11);
    }

    public String getPlanCoverageDescription3() {
        return getField(12);
    }

    public String getPlanCoverageDescription4() {
        return getField(13);
    }

    public String getPlanCoverageDescription5() {
        return getField(14);
    }
} 