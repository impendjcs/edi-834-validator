package com.edi.validator;

import com.edi.validator.model.ValidationError;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.awt.Desktop;
import java.io.File;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class EDI834ValidatorTest {
    private static final String TEST_FILE = Paths.get("src", "test", "resources", "valid-834.edi").toAbsolutePath().toString();
    private static List<ValidationError> allErrors;
    private static final Map<String, String> testReportLinks = new LinkedHashMap<>();

    @BeforeClass
    public static void validateFile() {
        System.out.println("Running EDI 834 Validation...");
        EDIValidator validator = new EDI834Validator();
        allErrors = validator.validate(TEST_FILE);
        System.out.println("Validation complete. Found " + allErrors.size() + " errors.");
    }

    @Test
    public void testRequiredSegments() {
        List<ValidationError> errors = filterErrors(TestReportGenerator.TestType.REQUIRED_SEGMENTS);
        String reportFile = TestReportGenerator.generateReport(
            "Required Segments", errors.isEmpty(), errors, TEST_FILE,
            TestReportGenerator.TestType.REQUIRED_SEGMENTS, ""
        );
        testReportLinks.put("Required Segments", getRelativeReportPath(reportFile));
    }

    @Test
    public void testDateFormats() {
        List<ValidationError> errors = filterErrors(TestReportGenerator.TestType.DATE_FORMATS);
        String reportFile = TestReportGenerator.generateReport(
            "Date Formats", errors.isEmpty(), errors, TEST_FILE,
            TestReportGenerator.TestType.DATE_FORMATS, ""
        );
        testReportLinks.put("Date Formats", getRelativeReportPath(reportFile));
    }

    @Test
    public void testLoopIdentifiers() {
        List<ValidationError> errors = filterErrors(TestReportGenerator.TestType.LOOP_IDENTIFIERS);
        String reportFile = TestReportGenerator.generateReport(
            "Loop Identifiers", errors.isEmpty(), errors, TEST_FILE,
            TestReportGenerator.TestType.LOOP_IDENTIFIERS, ""
        );
        testReportLinks.put("Loop Identifiers", getRelativeReportPath(reportFile));
    }

    @AfterClass
    public static void generateSummaryReport() {
        String summaryFile = TestReportGenerator.generateSummaryReport(testReportLinks, allErrors, TEST_FILE);
        String summaryReportLink = getRelativeReportPath(summaryFile);

        // Regenerate each test report with the summary link
        for (Map.Entry<String, String> entry : testReportLinks.entrySet()) {
            String testName = entry.getKey();
            TestReportGenerator.TestType type;
            switch (testName) {
                case "Required Segments": type = TestReportGenerator.TestType.REQUIRED_SEGMENTS; break;
                case "Date Formats": type = TestReportGenerator.TestType.DATE_FORMATS; break;
                case "Loop Identifiers": type = TestReportGenerator.TestType.LOOP_IDENTIFIERS; break;
                default: type = TestReportGenerator.TestType.SUMMARY;
            }
            List<ValidationError> errors = filterErrors(type);
            TestReportGenerator.generateReport(
                testName, errors.isEmpty(), errors, TEST_FILE, type, summaryReportLink
            );
        }

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

    private static List<ValidationError> filterErrors(TestReportGenerator.TestType testType) {
        return allErrors.stream()
            .filter(error -> {
                String errorMessage = error.getMessage().toLowerCase();
                switch (testType) {
                    case REQUIRED_SEGMENTS:
                        return errorMessage.contains("segment") || errorMessage.contains("missing");
                    case DATE_FORMATS:
                        return errorMessage.contains("date format");
                    case LOOP_IDENTIFIERS:
                        return errorMessage.contains("loop identifier");
                    default:
                        return false;
                }
            })
            .collect(Collectors.toList());
    }

    private static String getRelativeReportPath(String absolutePath) {
        if (absolutePath == null) return "";
        File file = new File(absolutePath);
        return file.getName();
    }
} 