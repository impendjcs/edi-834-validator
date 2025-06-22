package com.edi.validator;

import com.edi.validator.interfaces.EDISegmentValidator;
import com.edi.validator.model.DTPSegment;
import com.edi.validator.model.EDISegment;
import com.edi.validator.model.ValidationError;
import java.util.ArrayList;
import java.util.List;

public class DTPSegmentValidator implements EDISegmentValidator {

    @Override
    public List<ValidationError> validate(EDISegment segment) {
        List<ValidationError> errors = new ArrayList<>();
        if (!(segment instanceof DTPSegment)) {
            errors.add(new ValidationError(DTPSegment.SEGMENT_CODE, "Segment Type", "Invalid segment type", segment.getLineNumber()));
            return errors;
        }

        DTPSegment dtpSegment = (DTPSegment) segment;

        if (dtpSegment.getFields().size() < 4) {
            errors.add(new ValidationError(DTPSegment.SEGMENT_CODE, "Fields", "DTP segment has insufficient fields (expected 3, found " + dtpSegment.getFields().size() + ")", segment.getLineNumber()));
            return errors;
        }

        if (!"D8".equals(dtpSegment.getDateTimeFormatQualifier())) {
            errors.add(new ValidationError(DTPSegment.SEGMENT_CODE, "Date Format", "Invalid date format qualifier in DTP segment: " + dtpSegment.getDateTimeFormatQualifier(), segment.getLineNumber()));
        }

        if (!dtpSegment.getDate().matches("^\\d{8}$")) {
            errors.add(new ValidationError(DTPSegment.SEGMENT_CODE, "Date", "Invalid DTP date format: " + dtpSegment.getDate() + " (must be CCYYMMDD)", segment.getLineNumber()));
        }

        return errors;
    }

    @Override
    public String getSegmentCode() {
        return DTPSegment.SEGMENT_CODE;
    }
} 