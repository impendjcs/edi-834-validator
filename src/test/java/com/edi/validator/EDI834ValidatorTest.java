package com.edi.validator;

import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.awt.Desktop;
import java.io.File;

public class EDI834ValidatorTest {
    private static final String TEST_FILE = "data/sample-834Dental.edi";
    private static final List<String> allErrors = new ArrayList<>();
    private static final Map<String, String> testReportLinks = new LinkedHashMap<>();
    private static String summaryReportLink = "";

    @Test
    public void testRequiredSegments() {
        EDI834Validator validator = new EDI834Validator();
        validator.validateEDI834(TEST_FILE);
        List<String> errors = validator.getValidationErrors();
        allErrors.addAll(errors);
        String reportFile = TestReportGenerator.generateReport(
            "Required Segments", errors.isEmpty(), errors, TEST_FILE,
            TestReportGenerator.TestType.REQUIRED_SEGMENTS, ""
        );
        testReportLinks.put("Required Segments", getRelativeReportPath(reportFile));
    }

    @Test
    public void testDateFormats() {
        EDI834Validator validator = new EDI834Validator();
        validator.validateEDI834(TEST_FILE);
        List<String> errors = validator.getValidationErrors();
        allErrors.addAll(errors);
        String reportFile = TestReportGenerator.generateReport(
            "Date Formats", errors.isEmpty(), errors, TEST_FILE,
            TestReportGenerator.TestType.DATE_FORMATS, ""
        );
        testReportLinks.put("Date Formats", getRelativeReportPath(reportFile));
    }

    @Test
    public void testLoopIdentifiers() {
        EDI834Validator validator = new EDI834Validator();
        validator.validateEDI834(TEST_FILE);
        List<String> errors = validator.getValidationErrors();
        allErrors.addAll(errors);
        String reportFile = TestReportGenerator.generateReport(
            "Loop Identifiers", errors.isEmpty(), errors, TEST_FILE,
            TestReportGenerator.TestType.LOOP_IDENTIFIERS, ""
        );
        testReportLinks.put("Loop Identifiers", getRelativeReportPath(reportFile));
    }

    @AfterClass
    public static void generateSummaryReport() {
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