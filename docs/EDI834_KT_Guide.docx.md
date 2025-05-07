# EDI 834 Validation Framework – Knowledge Transfer (KT) Guideline

## 1. Project Overview

This framework is a custom, Java-based solution for validating EDI 834 files, especially for NY State Health Commerce System (HCS) requirements. It does not use Smooks or any third-party EDI parsing library; all logic is implemented using the Java Standard Library.

---

## 2. Project Structure

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
│   └── sample-834Dental.edi
├── target/
│   └── test-reports/
│       └── [HTML test reports]
├── pom.xml
└── README.md
```

- **src/main/java/com/edi/validator/**: Core Java classes for validation and reporting.
- **src/test/java/com/edi/validator/**: JUnit test class for running validations and generating reports.
- **data/**: Place for EDI files to be validated.
- **target/test-reports/**: Generated HTML reports.
- **pom.xml**: Maven build file (no EDI-specific dependencies).
- **README.md**: Project documentation.

---

## 3. Key Classes and Their Components

### 3.1. EDI834Validator.java
- **Purpose:** Core validation logic for EDI 834 files.
- **How it works:**
  - Reads the EDI file line by line.
  - Splits each line into segments and fields.
  - Checks for required segments (ISA, GS, ST, BGN, etc.).
  - Validates field counts, date formats, SSN formats, loop identifiers, and more.
  - Collects validation errors in a list.
- **Key Methods:**
  - `validateEDI834(String filePath)`: Main entry point for validation.
  - `getValidationErrors()`: Returns the list of validation errors found.
  - Private helper methods for each segment type (e.g., `validateISA`, `validateGS`, etc.).

### 3.2. TestReportGenerator.java
- **Purpose:** Generates modern, visually appealing HTML reports for test results.
- **How it works:**
  - Creates individual test reports for each validation aspect (date formats, required segments, etc.).
  - Generates a summary report with all errors and navigation to individual reports.
  - Uses a warm color palette and responsive, card-based layout.
  - Highlights errors in the file preview and provides navigation buttons.
- **Key Methods:**
  - `generateReport(...)`: Creates an individual test report.
  - `generateSummaryReport(...)`: Creates the summary report with navigation.
  - Private methods for HTML/CSS generation and error highlighting.

### 3.3. EDI834ValidatorTest.java
- **Purpose:** JUnit test class to automate validation and report generation.
- **How it works:**
  - Runs the validator on a sample EDI file for each validation aspect.
  - Collects errors and generates individual and summary reports.
  - After all tests, automatically opens the summary report in the default browser.
- **Key Features:**
  - Uses `@AfterClass` to generate the summary and open it.
  - Each test focuses on a specific validation aspect (date formats, required segments, loop identifiers).
  - No file creation logic—uses a static test file in `data/`.

---

## 4. How to Use

1. **Place your EDI file** in the `data/` directory (e.g., `data/sample-834Dental.edi`).
2. **Run the tests** (e.g., with `mvn test`).
3. **View the summary report** (auto-opens in your browser, or find it in `target/test-reports/`).
4. **Navigate to individual test reports** using the summary page.

---

## 5. Customization & Extension

- **To add new validation rules:**  
  Edit or extend methods in `EDI834Validator.java`.
- **To add new test cases:**  
  Add new test methods in `EDI834ValidatorTest.java` and update `TestReportGenerator.java` if needed.
- **To change report appearance:**  
  Edit the HTML/CSS in `TestReportGenerator.java`.

---

## 6. Best Practices

- Keep your EDI test files in `data/` for easy management.
- Use descriptive test names and report titles.
- Review the summary report for a high-level overview, and drill down into individual reports for details.
- Update validation logic as NY HCS or business requirements evolve.

---

## 7. Troubleshooting

- **No reports generated?**  
  Check for exceptions in the test output and ensure the `target/test-reports/` directory exists.
- **Browser doesn't open?**  
  Make sure Java Desktop API is supported on your OS, or open the summary HTML file manually.
- **Validation not working as expected?**  
  Review the logic in `EDI834Validator.java` and the structure of your EDI file.

---

## 8. Contact & Further KT

- For further questions, review the code comments and README.
- For a live KT, walk through the codebase in the order above, running a test and reviewing the generated reports together. 