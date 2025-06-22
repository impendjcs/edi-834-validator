package com.edi.validator;

import com.edi.validator.interfaces.EDISegmentValidator;
import com.edi.validator.model.EDISegment;
import com.edi.validator.model.N1Segment;
import com.edi.validator.model.ValidationError;
import java.util.ArrayList;
import java.util.List;

public class N1SegmentValidator implements EDISegmentValidator {
    @Override
    public List<ValidationError> validate(EDISegment segment) {
        List<ValidationError> errors = new ArrayList<>();
        
        if (!(segment instanceof N1Segment)) {
            errors.add(new ValidationError(N1Segment.SEGMENT_CODE, "Segment Type", 
                "Invalid segment type", segment.getLineNumber()));
            return errors;
        }

        N1Segment n1Segment = (N1Segment) segment;

        // Validate number of fields
        if (segment.getFields().size() < 3) {
            errors.add(new ValidationError(N1Segment.SEGMENT_CODE, "Fields", 
                "N1 segment has insufficient fields (expected 3, found " + segment.getFields().size() + ")",
                segment.getLineNumber()));
            return errors;
        }

        // Validate entity identifier code
        String entityCode = n1Segment.getEntityIdentifierCode();
        if (!entityCode.matches("^(41|40|ACV|IAE|IN|PE|PR|TV)$")) {
            errors.add(new ValidationError(N1Segment.SEGMENT_CODE, "Entity Identifier Code",
                "Invalid entity identifier code in N1 segment: " + entityCode,
                segment.getLineNumber()));
        }

        // Validate identification code qualifier if present
        String qualifier = n1Segment.getIdentificationCodeQualifier();
        if (!qualifier.isEmpty() && !"FI".equals(qualifier)) {
            errors.add(new ValidationError(N1Segment.SEGMENT_CODE, "Identification Code Qualifier",
                "Invalid identification code qualifier: " + qualifier + " (must be FI)",
                segment.getLineNumber()));
        }

        // Validate identification code if present
        String code = n1Segment.getIdentificationCode();
        if (!code.isEmpty() && !code.matches("^\\d{9}$")) {
            errors.add(new ValidationError(N1Segment.SEGMENT_CODE, "Identification Code",
                "Invalid identification code format: " + code + " (must be 9 digits)",
                segment.getLineNumber()));
        }

        return errors;
    }

    @Override
    public String getSegmentCode() {
        return N1Segment.SEGMENT_CODE;
    }
} 