package com.edi.validator;

import com.edi.validator.interfaces.EDISegmentValidator;
import com.edi.validator.model.DMGSegment;
import com.edi.validator.model.EDISegment;
import com.edi.validator.model.ValidationError;
import java.util.ArrayList;
import java.util.List;

public class DMGSegmentValidator implements EDISegmentValidator {

    @Override
    public List<ValidationError> validate(EDISegment segment) {
        List<ValidationError> errors = new ArrayList<>();
        if (!(segment instanceof DMGSegment)) {
            errors.add(new ValidationError(DMGSegment.SEGMENT_CODE, "Segment Type", "Invalid segment type", segment.getLineNumber()));
            return errors;
        }

        DMGSegment dmgSegment = (DMGSegment) segment;

        if (dmgSegment.getFields().size() < 3) {
            errors.add(new ValidationError(DMGSegment.SEGMENT_CODE, "Fields", "DMG segment has insufficient fields (expected 3, found " + dmgSegment.getFields().size() + ")", segment.getLineNumber()));
            return errors;
        }

        if (!"D8".equals(dmgSegment.getDateTimeFormatQualifier())) {
            errors.add(new ValidationError(DMGSegment.SEGMENT_CODE, "Date Format", "Invalid date format qualifier in DMG segment: " + dmgSegment.getDateTimeFormatQualifier(), segment.getLineNumber()));
        }

        if (!dmgSegment.getDate().matches("^\\d{8}$")) {
            errors.add(new ValidationError(DMGSegment.SEGMENT_CODE, "Date", "Invalid DMG date format: " + dmgSegment.getDate() + " (must be CCYYMMDD)", segment.getLineNumber()));
        }

        return errors;
    }

    @Override
    public String getSegmentCode() {
        return DMGSegment.SEGMENT_CODE;
    }
} 