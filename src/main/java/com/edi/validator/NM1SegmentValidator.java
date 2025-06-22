package com.edi.validator;

import com.edi.validator.interfaces.EDISegmentValidator;
import com.edi.validator.model.EDISegment;
import com.edi.validator.model.NM1Segment;
import com.edi.validator.model.ValidationError;
import java.util.ArrayList;
import java.util.List;

public class NM1SegmentValidator implements EDISegmentValidator {
    @Override
    public List<ValidationError> validate(EDISegment segment) {
        List<ValidationError> errors = new ArrayList<>();
        
        if (!(segment instanceof NM1Segment)) {
            errors.add(new ValidationError(NM1Segment.SEGMENT_CODE, "Segment Type", 
                "Invalid segment type", segment.getLineNumber()));
            return errors;
        }

        NM1Segment nm1Segment = (NM1Segment) segment;

        // Validate number of fields
        if (segment.getFields().size() < 9) {
            errors.add(new ValidationError(NM1Segment.SEGMENT_CODE, "Fields", 
                "NM1 segment has insufficient fields (expected 9, found " + segment.getFields().size() + ")",
                segment.getLineNumber()));
            return errors;
        }

        // Validate entity identifier code
        String entityIdentifierCode = nm1Segment.getEntityIdentifierCode();
        if (!entityIdentifierCode.matches("^(IL|70|31|36|M8|74|QD)$")) {
            errors.add(new ValidationError(NM1Segment.SEGMENT_CODE, "Entity Identifier Code",
                "Invalid entity identifier code: " + entityIdentifierCode,
                segment.getLineNumber()));
        }

        // Validate entity type qualifier
        String entityTypeQualifier = nm1Segment.getEntityTypeQualifier();
        if (!entityTypeQualifier.matches("^(1|2)$")) {
            errors.add(new ValidationError(NM1Segment.SEGMENT_CODE, "Entity Type Qualifier",
                "Invalid entity type qualifier: " + entityTypeQualifier + " (must be 1 or 2)",
                segment.getLineNumber()));
        }

        // Validate identification code qualifier
        String identificationCodeQualifier = nm1Segment.getIdentificationCodeQualifier();
        if (!identificationCodeQualifier.matches("^(34|XX|FI|NI|PI|PP|SV|XV)$")) {
            errors.add(new ValidationError(NM1Segment.SEGMENT_CODE, "Identification Code Qualifier",
                "Invalid identification code qualifier: " + identificationCodeQualifier,
                segment.getLineNumber()));
        }

        // Validate identification code based on qualifier
        String identificationCode = nm1Segment.getIdentificationCode();
        if ("34".equals(identificationCodeQualifier)) {
            if (!identificationCode.matches("^\\d{9}$")) {
                errors.add(new ValidationError(NM1Segment.SEGMENT_CODE, "Identification Code",
                    "Invalid SSN format: " + identificationCode + " (must be 9 digits)",
                    segment.getLineNumber()));
            }
        } else if ("XX".equals(identificationCodeQualifier)) {
            if (!identificationCode.matches("^[A-Z0-9]{1,20}$")) {
                errors.add(new ValidationError(NM1Segment.SEGMENT_CODE, "Identification Code",
                    "Invalid member ID format: " + identificationCode + " (must be 1-20 alphanumeric characters)",
                    segment.getLineNumber()));
            }
        }

        return errors;
    }

    @Override
    public String getSegmentCode() {
        return NM1Segment.SEGMENT_CODE;
    }
} 