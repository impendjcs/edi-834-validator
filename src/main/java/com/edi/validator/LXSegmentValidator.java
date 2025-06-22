package com.edi.validator;

import com.edi.validator.interfaces.EDISegmentValidator;
import com.edi.validator.model.EDISegment;
import com.edi.validator.model.LXSegment;
import com.edi.validator.model.ValidationError;
import java.util.ArrayList;
import java.util.List;

public class LXSegmentValidator implements EDISegmentValidator {
    @Override
    public List<ValidationError> validate(EDISegment segment) {
        List<ValidationError> errors = new ArrayList<>();
        
        if (!(segment instanceof LXSegment)) {
            errors.add(new ValidationError(LXSegment.SEGMENT_CODE, "Segment Type", 
                "Invalid segment type", segment.getLineNumber()));
            return errors;
        }

        LXSegment lxSegment = (LXSegment) segment;

        // Validate number of fields
        if (lxSegment.getFields().size() < 2) {
            errors.add(new ValidationError(LXSegment.SEGMENT_CODE, "Fields", 
                "LX segment has insufficient fields (expected 2, found " + lxSegment.getFields().size() + ")",
                segment.getLineNumber()));
            return errors;
        }

        // Validate assigned number
        String assignedNumber = lxSegment.getAssignedNumber();
        if (!assignedNumber.matches("^\\d{1,6}$")) {
            errors.add(new ValidationError(LXSegment.SEGMENT_CODE, "Assigned Number",
                "Invalid assigned number format: " + assignedNumber + " (must be 1-6 digits)",
                segment.getLineNumber()));
        }

        return errors;
    }

    @Override
    public String getSegmentCode() {
        return LXSegment.SEGMENT_CODE;
    }
} 