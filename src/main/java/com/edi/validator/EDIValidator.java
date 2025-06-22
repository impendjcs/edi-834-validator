package com.edi.validator;

import com.edi.validator.interfaces.EDISegmentValidator;
import com.edi.validator.model.EDISegment;
import com.edi.validator.model.ValidationError;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public abstract class EDIValidator {
    private final List<ValidationError> validationErrors;
    private final Map<String, Boolean> requiredSegments;
    private int currentLineNumber;

    public EDIValidator() {
        this.validationErrors = new ArrayList<>();
        this.requiredSegments = new HashMap<>();
        this.currentLineNumber = 0;
    }

    public List<ValidationError> validate(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                currentLineNumber++;
                line = line.trim();
                
                // Remove segment terminator if present
                if (line.endsWith("~")) {
                    line = line.substring(0, line.length() - 1);
                }

                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                // Get segment code (first 3 characters)
                String segmentCode = line.substring(0, Math.min(3, line.length()));
                
                // Mark segment as present
                requiredSegments.put(segmentCode, true);

                // Get validator for this segment
                EDISegmentValidator validator = EDISegmentValidatorFactory.getValidator(segmentCode);
                if (validator != null) {
                    // Create segment object and validate
                    EDISegment segment = createSegment(segmentCode, line);
                    if(segment != null) {
                        List<ValidationError> errors = validator.validate(segment);
                        validationErrors.addAll(errors);
                    }
                }
            }

            // Check for missing required segments
            validateRequiredSegments();

            return validationErrors;
        } catch (IOException e) {
            validationErrors.add(new ValidationError("SYSTEM", "File", 
                "Error reading EDI file: " + e.getMessage(), currentLineNumber));
            return validationErrors;
        }
    }

    protected abstract EDISegment createSegment(String segmentCode, String line);

    protected abstract void validateRequiredSegments();

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    public int getCurrentLineNumber() {
        return currentLineNumber;
    }

    protected void addValidationError(String segmentCode, String fieldName, String message) {
        validationErrors.add(new ValidationError(segmentCode, fieldName, message, currentLineNumber));
    }

    protected boolean isSegmentPresent(String segmentCode) {
        return requiredSegments.getOrDefault(segmentCode, false);
    }
} 