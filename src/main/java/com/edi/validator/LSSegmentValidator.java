package com.edi.validator;

import com.edi.validator.interfaces.EDISegmentValidator;
import com.edi.validator.model.EDISegment;
import com.edi.validator.model.LSSegment;
import com.edi.validator.model.ValidationError;
import java.util.ArrayList;
import java.util.List;

public class LSSegmentValidator implements EDISegmentValidator {

    @Override
    public List<ValidationError> validate(EDISegment segment) {
        List<ValidationError> errors = new ArrayList<>();
        if (!(segment instanceof LSSegment)) {
            errors.add(new ValidationError(LSSegment.SEGMENT_CODE, "Segment Type", "Invalid segment type", segment.getLineNumber()));
            return errors;
        }

        LSSegment lsSegment = (LSSegment) segment;

        if (lsSegment.getFields().size() < 2) {
            errors.add(new ValidationError(LSSegment.SEGMENT_CODE, "Fields", "LS segment has insufficient fields (expected 2, found " + lsSegment.getFields().size() + ")", segment.getLineNumber()));
            return errors;
        }
        
        String loopId = lsSegment.getLoopIdentifierCode();
        if (!loopId.matches("^(2700|2750|2760|2770|2780|2790)$")) {
            errors.add(new ValidationError(LSSegment.SEGMENT_CODE, "Loop Identifier", "Invalid loop identifier in LS segment: " + loopId, segment.getLineNumber()));
        }

        return errors;
    }

    @Override
    public String getSegmentCode() {
        return LSSegment.SEGMENT_CODE;
    }
} 