package com.edi.validator.model;

import java.util.Arrays;
import java.util.List;

public abstract class EDISegment {
    protected final String segmentCode;
    protected final String rawLine;
    protected final int lineNumber;
    protected final List<String> fields;

    protected EDISegment(String segmentCode, String line, int lineNumber) {
        this.segmentCode = segmentCode;
        this.rawLine = line;
        this.lineNumber = lineNumber;
        this.fields = Arrays.asList(line.split("\\*"));
    }

    public String getSegmentCode() {
        return segmentCode;
    }

    public String getRawLine() {
        return rawLine;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public List<String> getFields() {
        return fields;
    }

    protected String getField(int index) {
        if (index < 0 || index >= fields.size()) {
            return "";
        }
        return fields.get(index);
    }
} 