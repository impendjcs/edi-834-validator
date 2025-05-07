package com.edi.validator;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.awt.Desktop;
import java.io.File;
import java.nio.file.Paths;

public class EDI834ValidatorTest {
    private static final String TEST_FILE = Paths.get("src", "test", "resources", "valid-834.edi").toAbsolutePath().toString();
    private static final List<String> allErrors = new ArrayList<>();
    private static final Map<String, String> testReportLinks = new LinkedHashMap<>();
    private static String summaryReportLink = "";

    @Before
    public void clearErrors() {
        allErrors.clear();
    }

    @Test
    public void testRequiredSegments() {
        System.out.println("\nTesting Required Segments...");
        EDI834Validator validator = new EDI834Validator();
        validator.validateEDI834(TEST_FILE);
        List<String> errors = validator.getValidationErrors();
        System.out.println("Required Segments Errors:");
        errors.forEach(System.out::println);
        allErrors.addAll(errors);
        String reportFile = TestReportGenerator.generateReport(
            "Required Segments", errors.isEmpty(), errors, TEST_FILE,
            TestReportGenerator.TestType.REQUIRED_SEGMENTS, ""
        );
        testReportLinks.put("Required Segments", getRelativeReportPath(reportFile));
    }

    @Test
    public void testDateFormats() {
        System.out.println("\nTesting Date Formats...");
        EDI834Validator validator = new EDI834Validator();
        validator.validateEDI834(TEST_FILE);
        List<String> errors = validator.getValidationErrors();
        System.out.println("Date Format Errors:");
        errors.forEach(System.out::println);
        allErrors.addAll(errors);
        String reportFile = TestReportGenerator.generateReport(
            "Date Formats", errors.isEmpty(), errors, TEST_FILE,
            TestReportGenerator.TestType.DATE_FORMATS, ""
        );
        testReportLinks.put("Date Formats", getRelativeReportPath(reportFile));
    }

    @Test
    public void testLoopIdentifiers() {
        System.out.println("\nTesting Loop Identifiers...");
        EDI834Validator validator = new EDI834Validator();
        validator.validateEDI834(TEST_FILE);
        List<String> errors = validator.getValidationErrors();
        System.out.println("Loop Identifier Errors:");
        errors.forEach(System.out::println);
        allErrors.addAll(errors);
        String reportFile = TestReportGenerator.generateReport(
            "Loop Identifiers", errors.isEmpty(), errors, TEST_FILE,
            TestReportGenerator.TestType.LOOP_IDENTIFIERS, ""
        );
        testReportLinks.put("Loop Identifiers", getRelativeReportPath(reportFile));
    }

    @AfterClass
    public static void generateSummaryReport() {
        System.out.println("\nTotal unique errors: " + allErrors.size());
        System.out.println("All Errors:");
        allErrors.forEach(System.out::println);

        // Generate the summary report and get its filename
        String summaryFile = TestReportGenerator.generateSummaryReport(testReportLinks, allErrors, TEST_FILE);
        summaryReportLink = getRelativeReportPath(summaryFile);

        // Regenerate each test report with the summary link
        for (Map.Entry<String, String> entry : testReportLinks.entrySet()) {
            String testName = entry.getKey();
            String reportFile = "target/test-reports/" + entry.getValue();
            // Re-run the validator to get errors for this test
            EDI834Validator validator = new EDI834Validator();
            validator.validateEDI834(TEST_FILE);
            List<String> errors = validator.getValidationErrors();
            TestReportGenerator.TestType type;
            switch (testName) {
                case "Required Segments": type = TestReportGenerator.TestType.REQUIRED_SEGMENTS; break;
                case "Date Formats": type = TestReportGenerator.TestType.DATE_FORMATS; break;
                case "Loop Identifiers": type = TestReportGenerator.TestType.LOOP_IDENTIFIERS; break;
                default: type = TestReportGenerator.TestType.SUMMARY;
            }
            // Regenerate the report with the summary link
            TestReportGenerator.generateReport(
                testName, errors.isEmpty(), errors, TEST_FILE, type, summaryReportLink
            );
        }

        // Auto-open the summary report in the default browser
        if (summaryFile != null) {
            try {
                File htmlFile = new File(summaryFile);
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(htmlFile.toURI());
                }
            } catch (Exception e) {
                System.err.println("Could not open summary report in browser: " + e.getMessage());
            }
        }
    }

    private static String getRelativeReportPath(String absolutePath) {
        if (absolutePath == null) return "";
        int idx = absolutePath.lastIndexOf("/");
        if (idx >= 0) {
            return absolutePath.substring(idx + 1);
        }
        return absolutePath;
    }
} 