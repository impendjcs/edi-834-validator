package com.edi.validator;

import com.edi.validator.model.ValidationError;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide the path to your EDI file as an argument");
            System.out.println("Example: java -cp target/edi-834-validator-1.0-SNAPSHOT.jar com.edi.validator.Main sample-834.edi");
            return;
        }

        String ediFilePath = args[0];
        EDI834Validator validator = new EDI834Validator();

        try {
            List<ValidationError> errors = validator.validate(ediFilePath);

            if (errors.isEmpty()) {
                System.out.println("EDI file is valid!");
            } else {
                System.out.println("EDI file has " + errors.size() + " validation errors:");
                for (ValidationError error : errors) {
                    System.out.println("- Line " + error.getLineNumber() + " (" + error.getSegmentCode() + 
                                     "): " + error.getField() + " - " + error.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error validating EDI file: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 