package com.edi.validator;

public class ValidationError {
    private final String segmentCode;
    private final String fieldName;
    private final String message;
    private final int lineNumber;

    public ValidationError(String segmentCode, String fieldName, String message, int lineNumber) {
        this.segmentCode = segmentCode;
        this.fieldName = fieldName;
        this.message = message;
        this.lineNumber = lineNumber;
    }

    public String getSegmentCode() {
        return segmentCode;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return String.format("Line %d: %s - %s: %s", lineNumber, segmentCode, fieldName, message);
    }
} 