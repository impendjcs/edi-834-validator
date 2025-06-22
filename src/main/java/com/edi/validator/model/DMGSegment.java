package com.edi.validator.model;

public class DMGSegment extends EDISegment {
    public static final String SEGMENT_CODE = "DMG";

    public DMGSegment(String line, int lineNumber) {
        super(SEGMENT_CODE, line, lineNumber);
    }

    public String getDateTimeFormatQualifier() {
        return getField(1);
    }

    public String getDate() {
        return getField(2);
    }

    public String getGenderCode() {
        return getField(3);
    }

    public String getMaritalStatusCode() {
        return getField(4);
    }

    public String getCompositeRaceOrEthnicityInformation() {
        return getField(5);
    }

    public String getCitizenshipStatusCode() {
        return getField(6);
    }

    public String getCountryCode() {
        return getField(7);
    }

    public String getBasisOfVerificationCode() {
        return getField(8);
    }

    public String getQuantity() {
        return getField(9);
    }

    public String getCodeListQualifierCode() {
        return getField(10);
    }

    public String getCodeListResponsibleAgencyCode() {
        return getField(11);
    }
} 