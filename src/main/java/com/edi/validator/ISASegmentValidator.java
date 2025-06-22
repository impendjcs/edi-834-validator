package com.edi.validator;

import com.edi.validator.interfaces.EDISegmentValidator;
import com.edi.validator.model.EDISegment;
import com.edi.validator.model.ISASegment;
import com.edi.validator.model.ValidationError;
import java.util.ArrayList;
import java.util.List;

public class ISASegmentValidator implements EDISegmentValidator {
    @Override
    public List<ValidationError> validate(EDISegment segment) {
        List<ValidationError> errors = new ArrayList<>();
        
        if (!(segment instanceof ISASegment)) {
            errors.add(new ValidationError(ISASegment.SEGMENT_CODE, "Segment Type", 
                "Invalid segment type", segment.getLineNumber()));
            return errors;
        }

        ISASegment isaSegment = (ISASegment) segment;

        // Validate number of fields
        if (segment.getFields().size() < 16) {
            errors.add(new ValidationError(ISASegment.SEGMENT_CODE, "Fields", 
                "ISA segment has insufficient fields (expected 16, found " + segment.getFields().size() + ")",
                segment.getLineNumber()));
            return errors;
        }

        // Validate sender ID (NY HCS specific)
        String senderId = isaSegment.getSenderId();
        if (!senderId.startsWith("NY")) {
            errors.add(new ValidationError(ISASegment.SEGMENT_CODE, "Sender ID",
                "Invalid sender ID: " + senderId + " (must start with NY)",
                segment.getLineNumber()));
        }

        // Validate date format (YYMMDD)
        String date = isaSegment.getDate();
        if (!date.matches("^\\d{6}$")) {
            errors.add(new ValidationError(ISASegment.SEGMENT_CODE, "Date",
                "Invalid date format: " + date + " (must be YYMMDD)",
                segment.getLineNumber()));
        }

        // Validate time format (HHMM)
        String time = isaSegment.getTime();
        if (!time.matches("^\\d{4}$")) {
            errors.add(new ValidationError(ISASegment.SEGMENT_CODE, "Time",
                "Invalid time format: " + time + " (must be HHMM)",
                segment.getLineNumber()));
        }

        return errors;
    }

    @Override
    public String getSegmentCode() {
        return ISASegment.SEGMENT_CODE;
    }
} 