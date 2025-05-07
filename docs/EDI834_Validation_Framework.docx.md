# EDI 834 Validation Framework Documentation

## Table of Contents
1. [Overview](#overview)
2. [Project Structure](#project-structure)
3. [Components](#components)
4. [Validation Rules](#validation-rules)
5. [Test Framework](#test-framework)
6. [Report Generation](#report-generation)
7. [Usage Guide](#usage-guide)

## Overview

The EDI 834 Validation Framework is a comprehensive solution for validating EDI 834 files according to the NY State Health Commerce System (HCS) requirements. The framework provides robust validation, detailed error reporting, and a visual test reporting system.

## Project Structure

```
edi-validator/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── edi/
│   │               └── validator/
│   │                   ├── EDI834Validator.java
│   │                   └── TestReportGenerator.java
│   └── test/
│       └── java/
│           └── com/
│               └── edi/
│                   └── validator/
│                       └── EDI834ValidatorTest.java
├── data/
│   ├── valid-834.edi
│   ├── dental-834.edi
│   ├── invalid-entity-834.edi
│   ├── invalid-date-834.edi
│   └── missing-segments-834.edi
├── target/
│   └── test-reports/
│       └── [test reports]
├── pom.xml
└── README.md
```

### Project Structure Summary

The EDI 834 Validation Framework is organized into three main components:

1. **Core Validation Engine** (`src/main/java/com/edi/validator/EDI834Validator.java`)
   - Contains all validation logic for EDI 834 files
   - Implements NY HCS specific validation rules
   - Handles segment validation and error collection

2. **Test Framework** (`src/test/java/com/edi/validator/EDI834ValidatorTest.java`)
   - Contains test cases for various EDI file scenarios
   - Validates both correct and incorrect files
   - Ensures comprehensive test coverage

3. **Report Generation** (`src/main/java/com/edi/validator/TestReportGenerator.java`)
   - Generates detailed HTML test reports
   - Provides visual error highlighting
   - Creates timestamped reports for each test run

The project uses a standard Maven structure with:
- `src/main/java`: Core application code
- `src/test/java`: Test cases and test data
- `data/`: Sample EDI files for testing
- `target/test-reports/`: Generated test reports
- `pom.xml`: Project dependencies and build configuration

## Components

### 1. EDI834Validator
The core validation engine that implements the NY HCS validation rules:

- Validates required segments (ISA, GS, ST, BGN, etc.)
- Checks segment field counts and formats
- Validates entity identifier codes
- Ensures proper date formats
- Validates SSN formats
- Checks coverage types and qualifiers

### 2. TestReportGenerator
Generates detailed HTML test reports with:

- Test status (PASS/FAIL)
- File information
- Validation errors
- Visual file preview with error highlighting
- Line numbers and segment highlighting

## Validation Rules

### Required Segments
1. ISA (Interchange Control Header)
   - Must start with "NY" in sender ID
   - Must have 16 fields

2. GS (Functional Group Header)
   - Must have functional identifier "BE"
   - Must start with "NY" in sender ID
   - Must have 8 fields

3. ST (Transaction Set Header)
   - Must have transaction set identifier "834"
   - Must have 3 fields

4. BGN (Beginning Segment)
   - Must have date in CCYYMMDD format
   - Must have 5 fields

5. N1 (Name Segment)
   - Must have valid entity identifier code (41, 40, ACV, IAE, IN, PE, PR, TV)
   - Must have 3 fields

6. INS (Member Level Detail)
   - Must have valid coverage type
   - Must have valid qualifiers

7. REF (Reference Information)
   - Must have valid SSN format (9 digits)
   - Must have valid member ID format

8. NM1 (Member Name)
   - Must have valid entity type qualifier
   - Must have valid name format

9. DMG (Demographic Information)
   - Must have valid date format
   - Must have valid gender code

10. HD (Health Coverage)
    - Must have valid coverage type
    - Must have valid qualifiers

11. DTP (Date/Time Reference)
    - Must have valid date format
    - Must have valid qualifier

12. AMT (Monetary Amount)
    - Must have valid amount qualifier
    - Must have valid amount format

13. LX (Transaction Set Line Number)
    - Must have valid line number

14. PLA (Place or Location)
    - Must have valid date format
    - Must have valid qualifier

15. LS (Loop Header)
    - Must have valid loop identifier

16. LE (Loop Trailer)
    - Must have valid loop identifier

## Test Framework

### Test Cases

1. Valid EDI 834 File
   - Tests a complete, valid EDI file
   - Verifies all required segments
   - Checks proper formatting

2. Dental EDI 834 File
   - Tests dental-specific segments
   - Verifies additional N1 segments
   - Checks dental coverage types

3. Invalid Entity Code
   - Tests invalid entity identifier codes
   - Verifies error detection
   - Checks error messages

4. Missing Required Segments
   - Tests files with missing segments
   - Verifies error detection
   - Checks error messages

5. Invalid Date Formats
   - Tests invalid date formats
   - Verifies error detection
   - Checks error messages

## Report Generation

### HTML Report Features

1. Header Section
   - Test name
   - Generation timestamp
   - Overall status

2. File Information
   - File path
   - File size
   - Last modified date

3. Validation Details
   - List of validation errors
   - Error count
   - Detailed error messages

4. File Preview
   - Line numbers
   - Segment highlighting
   - Error field highlighting
   - Proper EDI formatting

### Report Styling

1. Color Coding
   - Success: Green background
   - Failure: Red background
   - Error highlights: Light red background
   - Segment names: Blue text

2. Layout
   - Side-by-side validation and preview
   - Responsive design
   - Monospace font for EDI content
   - Clear visual hierarchy

## Usage Guide

### Running Tests

1. Using Maven:
   ```bash
   mvn clean test
   ```

2. Test Reports Location:
   - Generated in `target/test-reports/`
   - Named with test name and timestamp
   - HTML format for easy viewing

### Creating New Tests

1. Add test file to `data/` directory
2. Create test method in `EDI834ValidatorTest.java`
3. Use `TestReportGenerator` to generate report
4. Run test to verify validation

### Customizing Validation

1. Modify validation rules in `EDI834Validator.java`
2. Add new validation methods as needed
3. Update test cases to cover new rules
4. Verify changes with test reports

## Best Practices

1. Always include line numbers in error messages
2. Use descriptive test names
3. Create specific test files for each test case
4. Review test reports for validation accuracy
5. Keep validation rules up to date with NY HCS requirements

## Maintenance

1. Regular Updates
   - Review NY HCS requirements
   - Update validation rules
   - Add new test cases
   - Enhance error messages

2. Documentation
   - Keep this document updated
   - Document new features
   - Update test cases
   - Maintain changelog

## Support

For issues or questions:
1. Check test reports for detailed error information
2. Review validation rules in documentation
3. Consult NY HCS companion guide
4. Contact development team 