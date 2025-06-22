package com.edi.validator.model;

public class INSSegment extends EDISegment {
    public static final String SEGMENT_CODE = "INS";

    public INSSegment(String line, int lineNumber) {
        super(SEGMENT_CODE, line, lineNumber);
    }

    public String getMemberIndicator() {
        return getField(1);
    }

    public String getIndividualRelationshipCode() {
        return getField(2);
    }

    public String getMaintenanceTypeCode() {
        return getField(3);
    }

    public String getMaintenanceReasonCode() {
        return getField(4);
    }

    public String getBenefitStatusCode() {
        return getField(5);
    }

    public String getMedicareStatusCode() {
        return getField(6);
    }

    public String getConsolidatedOmnibusBudgetReconciliationActCode() {
        return getField(7);
    }

    public String getEmploymentStatusCode() {
        return getField(8);
    }

    public String getStudentStatusCode() {
        return getField(9);
    }

    public String getHandicapIndicator() {
        return getField(10);
    }

    public String getDateTimePeriodFormatQualifier() {
        return getField(11);
    }

    public String getDateTimePeriod() {
        return getField(12);
    }

    public String getConfidentialityCode() {
        return getField(13);
    }

    public String getCityName() {
        return getField(14);
    }

    public String getStateOrProvinceCode() {
        return getField(15);
    }

    public String getCountryCode() {
        return getField(16);
    }

    public String getBirthSequenceNumber() {
        return getField(17);
    }
} 