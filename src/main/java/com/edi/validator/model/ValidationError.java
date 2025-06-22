package com.edi.validator.model;

public class ValidationError {
    private final String segmentCode;
    private final String field;
    private final String message;
    private final int lineNumber;

    public ValidationError(String segmentCode, String field, String message, int lineNumber) {
        this.segmentCode = segmentCode;
        this.field = field;
        this.message = message;
        this.lineNumber = lineNumber;
    }

    public String getSegmentCode() {
        return segmentCode;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return String.format("Line %d: %s segment - %s - %s", 
            lineNumber, segmentCode, field, message);
    }
} 