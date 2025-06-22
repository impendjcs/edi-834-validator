package com.edi.validator;

import com.edi.validator.model.EDISegment;
import java.util.List;

public interface EDISegmentValidator {
    List<ValidationError> validate(EDISegment segment);
    String getSegmentCode();
} 