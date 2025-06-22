package com.edi.validator;

import com.edi.validator.interfaces.EDISegmentValidator;
import com.edi.validator.model.AMTSegment;
import com.edi.validator.model.EDISegment;
import com.edi.validator.model.ValidationError;
import java.util.ArrayList;
import java.util.List;

public class AMTSegmentValidator implements EDISegmentValidator {
    @Override
    public List<ValidationError> validate(EDISegment segment) {
        List<ValidationError> errors = new ArrayList<>();
        
        if (!(segment instanceof AMTSegment)) {
            errors.add(new ValidationError(AMTSegment.SEGMENT_CODE, "Segment Type", 
                "Invalid segment type", segment.getLineNumber()));
            return errors;
        }

        AMTSegment amtSegment = (AMTSegment) segment;

        // Validate number of fields
        if (segment.getFields().size() < 3) {
            errors.add(new ValidationError(AMTSegment.SEGMENT_CODE, "Fields", 
                "AMT segment has insufficient fields (expected 3, found " + segment.getFields().size() + ")",
                segment.getLineNumber()));
            return errors;
        }

        // Validate amount qualifier code
        String amountQualifierCode = amtSegment.getAmountQualifierCode();
        if (!amountQualifierCode.matches("^(D2|P3|T3|T4|T5|T6|T7|T8|T9)$")) {
            errors.add(new ValidationError(AMTSegment.SEGMENT_CODE, "Amount Qualifier", "Invalid amount qualifier in AMT segment: " + amountQualifierCode, segment.getLineNumber()));
        }

        // Validate monetary amount
        String monetaryAmount = amtSegment.getMonetaryAmount();
        if (!monetaryAmount.matches("^\\d+(\\.\\d{2})?$")) {
            errors.add(new ValidationError(AMTSegment.SEGMENT_CODE, "Monetary Amount",
                "Invalid monetary amount format: " + monetaryAmount + " (must be a valid decimal number)",
                segment.getLineNumber()));
        }

        // Validate credit debit flag code if present
        String creditDebitFlagCode = amtSegment.getCreditDebitFlagCode();
        if (creditDebitFlagCode != null && !creditDebitFlagCode.isEmpty()) {
            if (!creditDebitFlagCode.matches("^(C|D)$")) {
                errors.add(new ValidationError(AMTSegment.SEGMENT_CODE, "Credit Debit Flag Code",
                    "Invalid credit debit flag code: " + creditDebitFlagCode + " (must be C or D)",
                    segment.getLineNumber()));
            }
        }

        return errors;
    }

    @Override
    public String getSegmentCode() {
        return AMTSegment.SEGMENT_CODE;
    }
} 