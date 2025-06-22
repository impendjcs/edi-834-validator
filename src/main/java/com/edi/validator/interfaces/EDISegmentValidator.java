package com.edi.validator.interfaces;

import com.edi.validator.model.EDISegment;
import com.edi.validator.model.ValidationError;
import java.util.List;

public interface EDISegmentValidator {
    /**
     * Validates an EDI segment and returns a list of validation errors.
     * @param segment The EDI segment to validate
     * @return List of validation errors, empty if no errors found
     */
    List<ValidationError> validate(EDISegment segment);

    /**
     * Returns the segment code this validator handles
     * @return The segment code (e.g., "ISA", "GS", etc.)
     */
    String getSegmentCode();
} 