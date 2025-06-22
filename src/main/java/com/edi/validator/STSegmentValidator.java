package com.edi.validator;

import com.edi.validator.interfaces.EDISegmentValidator;
import com.edi.validator.model.EDISegment;
import com.edi.validator.model.STSegment;
import com.edi.validator.model.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class STSegmentValidator implements EDISegmentValidator {
    @Override
    public List<ValidationError> validate(EDISegment segment) {
        List<ValidationError> errors = new ArrayList<>();
        
        if (!(segment instanceof STSegment)) {
            errors.add(new ValidationError(STSegment.SEGMENT_CODE, "Segment Type", 
                "Invalid segment type", segment.getLineNumber()));
            return errors;
        }

        STSegment stSegment = (STSegment) segment;

        // Validate number of fields
        if (segment.getFields().size() < 3) {
            errors.add(new ValidationError(STSegment.SEGMENT_CODE, "Fields", 
                "ST segment has insufficient fields (expected 3, found " + segment.getFields().size() + ")",
                segment.getLineNumber()));
            return errors;
        }

        // Validate transaction set identifier
        String transactionSetId = stSegment.getTransactionSetId();
        if (!"834".equals(transactionSetId)) {
            errors.add(new ValidationError(STSegment.SEGMENT_CODE, "Transaction Set ID",
                "Invalid transaction set identifier: " + transactionSetId + " (must be 834)",
                segment.getLineNumber()));
        }

        // Validate version
        String version = stSegment.getVersion();
        if (!"005010X220A1".equals(version)) {
            errors.add(new ValidationError(STSegment.SEGMENT_CODE, "Version",
                "Invalid version: " + version + " (must be 005010X220A1)",
                segment.getLineNumber()));
        }

        return errors;
    }

    @Override
    public String getSegmentCode() {
        return STSegment.SEGMENT_CODE;
    }
} 