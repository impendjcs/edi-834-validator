package com.edi.validator;

import com.edi.validator.interfaces.EDISegmentValidator;
import com.edi.validator.model.EDISegment;
import com.edi.validator.model.REFSegment;
import com.edi.validator.model.ValidationError;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class REFSegmentValidator implements EDISegmentValidator {
    private static final Pattern SSN_PATTERN = Pattern.compile("^\\d{9}$");

    @Override
    public List<ValidationError> validate(EDISegment segment) {
        List<ValidationError> errors = new ArrayList<>();
        
        if (!(segment instanceof REFSegment)) {
            errors.add(new ValidationError(REFSegment.SEGMENT_CODE, "Segment Type", 
                "Invalid segment type", segment.getLineNumber()));
            return errors;
        }

        REFSegment refSegment = (REFSegment) segment;

        // Validate number of fields
        if (refSegment.getFields().size() < 2) {
            errors.add(new ValidationError(REFSegment.SEGMENT_CODE, "Fields", 
                "REF segment has insufficient fields (expected 2, found " + refSegment.getFields().size() + ")",
                segment.getLineNumber()));
            return errors;
        }

        // Validate reference identification qualifier
        String refCode = refSegment.getReferenceIdentificationQualifier();
        if ("0F".equals(refCode)) {
            if (refSegment.getFields().size() < 3 || !SSN_PATTERN.matcher(refSegment.getReferenceIdentification()).matches()) {
                errors.add(new ValidationError(REFSegment.SEGMENT_CODE, "SSN", "Invalid SSN format in REF segment: " + refSegment.getReferenceIdentification(), segment.getLineNumber()));
            }
        } else if ("1L".equals(refCode)) {
            if (refSegment.getFields().size() < 3 || !refSegment.getReferenceIdentification().matches("^[A-Z0-9]{1,20}$")) {
                errors.add(new ValidationError(REFSegment.SEGMENT_CODE, "Member ID", "Invalid member ID format in REF segment: " + refSegment.getReferenceIdentification(), segment.getLineNumber()));
            }
        }

        return errors;
    }

    @Override
    public String getSegmentCode() {
        return REFSegment.SEGMENT_CODE;
    }
} 