package com.edi.validator;

import com.edi.validator.interfaces.EDISegmentValidator;
import com.edi.validator.model.EDISegment;
import com.edi.validator.model.GSSegment;
import com.edi.validator.model.ValidationError;
import java.util.ArrayList;
import java.util.List;

public class GSSegmentValidator implements EDISegmentValidator {
    @Override
    public List<ValidationError> validate(EDISegment segment) {
        List<ValidationError> errors = new ArrayList<>();
        
        if (!(segment instanceof GSSegment)) {
            errors.add(new ValidationError(GSSegment.SEGMENT_CODE, "Segment Type", 
                "Invalid segment type", segment.getLineNumber()));
            return errors;
        }

        GSSegment gsSegment = (GSSegment) segment;

        // Validate number of fields
        if (segment.getFields().size() < 8) {
            errors.add(new ValidationError(GSSegment.SEGMENT_CODE, "Fields", 
                "GS segment has insufficient fields (expected 8, found " + segment.getFields().size() + ")",
                segment.getLineNumber()));
            return errors;
        }

        // Validate functional identifier code
        String functionalId = gsSegment.getFunctionalIdentifierCode();
        if (!"BE".equals(functionalId)) {
            errors.add(new ValidationError(GSSegment.SEGMENT_CODE, "Functional Identifier",
                "Invalid functional identifier: " + functionalId + " (must be BE)",
                segment.getLineNumber()));
        }

        // Validate sender ID (NY HCS specific)
        String senderId = gsSegment.getSenderId();
        if (!senderId.startsWith("NY")) {
            errors.add(new ValidationError(GSSegment.SEGMENT_CODE, "Sender ID",
                "Invalid sender ID: " + senderId + " (must start with NY)",
                segment.getLineNumber()));
        }

        // Validate date format (YYMMDD)
        String date = gsSegment.getDate();
        if (!date.matches("^\\d{6}$")) {
            errors.add(new ValidationError(GSSegment.SEGMENT_CODE, "Date",
                "Invalid date format: " + date + " (must be YYMMDD)",
                segment.getLineNumber()));
        }

        // Validate time format (HHMM)
        String time = gsSegment.getTime();
        if (!time.matches("^\\d{4}$")) {
            errors.add(new ValidationError(GSSegment.SEGMENT_CODE, "Time",
                "Invalid time format: " + time + " (must be HHMM)",
                segment.getLineNumber()));
        }

        return errors;
    }

    @Override
    public String getSegmentCode() {
        return GSSegment.SEGMENT_CODE;
    }
} 