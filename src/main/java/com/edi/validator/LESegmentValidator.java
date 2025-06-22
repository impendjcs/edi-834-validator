package com.edi.validator;

import com.edi.validator.interfaces.EDISegmentValidator;
import com.edi.validator.model.EDISegment;
import com.edi.validator.model.LESegment;
import com.edi.validator.model.ValidationError;
import java.util.ArrayList;
import java.util.List;

public class LESegmentValidator implements EDISegmentValidator {

    @Override
    public List<ValidationError> validate(EDISegment segment) {
        List<ValidationError> errors = new ArrayList<>();
        if (!(segment instanceof LESegment)) {
            errors.add(new ValidationError(LESegment.SEGMENT_CODE, "Segment Type", "Invalid segment type", segment.getLineNumber()));
            return errors;
        }

        LESegment leSegment = (LESegment) segment;

        if (leSegment.getFields().size() < 2) {
            errors.add(new ValidationError(LESegment.SEGMENT_CODE, "Fields", "LE segment has insufficient fields (expected 2, found " + leSegment.getFields().size() + ")", segment.getLineNumber()));
            return errors;
        }
        
        String loopId = leSegment.getLoopIdentifierCode();
        if (!loopId.matches("^(2700|2750|2760|2770|2780|2790)$")) {
            errors.add(new ValidationError(LESegment.SEGMENT_CODE, "Loop Identifier", "Invalid loop identifier in LE segment: " + loopId, segment.getLineNumber()));
        }

        return errors;
    }

    @Override
    public String getSegmentCode() {
        return LESegment.SEGMENT_CODE;
    }
} 