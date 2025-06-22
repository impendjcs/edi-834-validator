package com.edi.validator;

import com.edi.validator.interfaces.EDISegmentValidator;
import com.edi.validator.model.EDISegment;
import com.edi.validator.model.PLASegment;
import com.edi.validator.model.ValidationError;
import java.util.ArrayList;
import java.util.List;

public class PLASegmentValidator implements EDISegmentValidator {

    @Override
    public List<ValidationError> validate(EDISegment segment) {
        List<ValidationError> errors = new ArrayList<>();
        if (!(segment instanceof PLASegment)) {
            errors.add(new ValidationError(PLASegment.SEGMENT_CODE, "Segment Type", "Invalid segment type", segment.getLineNumber()));
            return errors;
        }

        PLASegment plaSegment = (PLASegment) segment;

        if (plaSegment.getFields().size() < 4) {
            errors.add(new ValidationError(PLASegment.SEGMENT_CODE, "Fields", "PLA segment has insufficient fields (expected 4, found " + plaSegment.getFields().size() + ")", segment.getLineNumber()));
            return errors;
        }

        return errors;
    }

    @Override
    public String getSegmentCode() {
        return PLASegment.SEGMENT_CODE;
    }
} 