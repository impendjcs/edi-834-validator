package com.edi.validator;

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
            boolean isValid = validator.validateEDI834(ediFilePath);
            List<String> errors = validator.getValidationErrors();

            if (isValid) {
                System.out.println("EDI file is valid!");
            } else {
                System.out.println("EDI file has validation errors:");
                for (String error : errors) {
                    System.out.println("- " + error);
                }
            }
        } finally {
            validator.close();
        }
    }
} 