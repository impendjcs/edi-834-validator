package com.edi.validator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.LinkedHashMap;

public class TestReportGenerator {
    private static final String REPORT_DIR = "target/test-reports";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public enum TestType {
        REQUIRED_SEGMENTS, DATE_FORMATS, LOOP_IDENTIFIERS, SUMMARY
    }

    private static String getTestDescription(String testName) {
        switch (testName) {
            case "Required Segments":
                return "Checks that all required segments (ISA, GS, ST, BGN, etc.) are present and properly formatted according to NY HCS requirements.";
            case "Date Formats":
                return "Validates that all date fields in the EDI file follow the CCYYMMDD format in their respective segments (BGN, DTP, DMG, etc.).";
            case "Loop Identifiers":
                return "Checks that all loop identifiers (e.g., LS, LE) are present and valid in the EDI file.";
            default:
                return "Test validation report for EDI 834 file.";
        }
    }

    private static List<String> getExpectedValidations(String testName) {
        List<String> validations = new ArrayList<>();
        switch (testName) {
            case "Required Segments":
                validations.add("Presence of all required segments (ISA, GS, ST, BGN, etc.)");
                validations.add("Proper segment order");
                validations.add("Required field counts");
                break;
            case "Date Formats":
                validations.add("BGN segment date format (CCYYMMDD)");
                validations.add("DTP segment date formats");
                validations.add("DMG segment date formats");
                validations.add("PLA segment date formats");
                validations.add("Date format validation in all segments");
                break;
            case "Loop Identifiers":
                validations.add("Valid LS and LE loop identifiers");
                validations.add("Proper loop structure");
                break;
        }
        return validations;
    }

    public static String generateReport(String testName, boolean passed, List<String> validationErrors, String filePath, TestType testType, String summaryReportLink) {
        try {
            Files.createDirectories(Paths.get(REPORT_DIR));
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String reportFile = String.format("%s/%s_%s.html", REPORT_DIR, testName.replaceAll("\\s+", "_"), timestamp);

            List<String> filteredErrors = filterErrorsByTestType(validationErrors, testType);

            try (FileWriter writer = new FileWriter(reportFile)) {
                writer.write(generateHtmlContent(testName, passed, filteredErrors, filePath, testType, summaryReportLink));
            }

            System.out.println("Test report generated: " + reportFile);
            return reportFile;
        } catch (IOException e) {
            System.err.println("Error generating test report: " + e.getMessage());
            return null;
        }
    }

    public static String generateSummaryReport(Map<String, String> testReportLinks, List<String> allErrors, String filePath) {
        try {
            Files.createDirectories(Paths.get(REPORT_DIR));
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String reportFile = String.format("%s/summary-report-%s.html", REPORT_DIR, timestamp);

            try (FileWriter writer = new FileWriter(reportFile)) {
                writer.write(generateSummaryHtmlContent(testReportLinks, allErrors, filePath));
            }

            System.out.println("Summary report generated: " + reportFile);
            return reportFile;
        } catch (IOException e) {
            System.err.println("Error generating summary report: " + e.getMessage());
            return null;
        }
    }

    private static List<String> filterErrorsByTestType(List<String> errors, TestType testType) {
        if (testType == TestType.SUMMARY) return errors;
        List<String> filtered = new ArrayList<>();
        for (String error : errors) {
            switch (testType) {
                case REQUIRED_SEGMENTS:
                    if (error.toLowerCase().contains("segment") || error.toLowerCase().contains("missing")) {
                        filtered.add(error);
                    }
                    break;
                case DATE_FORMATS:
                    if (error.toLowerCase().contains("date format")) {
                        filtered.add(error);
                    }
                    break;
                case LOOP_IDENTIFIERS:
                    if (error.toLowerCase().contains("loop identifier")) {
                        filtered.add(error);
                    }
                    break;
                default:
                    break;
            }
        }
        return filtered;
    }

    private static String generateHtmlContent(String testName, boolean passed, List<String> validationErrors, String filePath, TestType testType, String summaryReportLink) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"en\">\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("    <title>EDI 834 Validation Test Report</title>\n");
        html.append("    <link href=\"https://fonts.googleapis.com/css?family=Inter:400,600,700&display=swap\" rel=\"stylesheet\">\n");
        html.append("    <style>\n");
        html.append("        body { font-family: 'Inter', Arial, sans-serif; margin: 0; background: #fff7f0; color: #3d2c1e; }\n");
        html.append("        .header { background: linear-gradient(90deg, #ffb347 0%, #ffcc80 100%); color: #5d4037; padding: 32px 0 24px 0; text-align: center; box-shadow: 0 2px 8px #ffecb3; }\n");
        html.append("        .header h1 { margin: 0 0 8px 0; font-size: 2.5em; font-weight: 700; letter-spacing: 1px; }\n");
        html.append("        .timestamp { color: #a1887f; font-size: 1em; margin-bottom: 8px; }\n");
        html.append("        .main-content { max-width: 1100px; margin: 0 auto; padding: 0 12px; }\n");
        html.append("        .main-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 24px; }\n");
        html.append("        .main-col { display: flex; flex-direction: column; gap: 12px; }\n");
        html.append("        .card { background: #fff3e0; border-radius: 12px; box-shadow: 0 2px 8px #ffe0b2; padding: 24px 32px; }\n");
        html.append("        .centered { display: flex; flex-direction: column; align-items: center; justify-content: center; }\n");
        html.append("        .nav-summary { margin: 32px 0 24px 0; text-align: center; }\n");
        html.append("        .nav-summary button { background: linear-gradient(90deg, #ff7043 0%, #ffa726 100%); color: #fff; padding: 12px 32px; border: none; border-radius: 24px; cursor: pointer; font-size: 1.1em; font-weight: 600; box-shadow: 0 2px 6px #ffccbc; transition: background 0.2s, box-shadow 0.2s; }\n");
        html.append("        .nav-summary button:hover { background: linear-gradient(90deg, #ffa726 0%, #ff7043 100%); box-shadow: 0 4px 12px #ffccbc; }\n");
        html.append("        .test-description { background: #ffe0b2; border-radius: 8px; padding: 18px 24px; box-shadow: 0 1px 4px #ffe0b2; }\n");
        html.append("        .expected-validations { background: #fffde7; border-radius: 8px; padding: 18px 24px; box-shadow: 0 1px 4px #ffe082; }\n");
        html.append("        .result { padding: 10px; border-radius: 8px; font-size: 1.2em; font-weight: 600; text-align: center; margin-bottom: 12px; }\n");
        html.append("        .success { background-color: #ffe082; color: #33691e; }\n");
        html.append("        .failure { background-color: #ffccbc; color: #b71c1c; }\n");
        html.append("        .file-info { background: #ffe0b2; border-radius: 8px; padding: 18px 24px; box-shadow: 0 1px 4px #ffe0b2; }\n");
        html.append("        .file-info h3 { margin-top: 0; color: #e65100; }\n");
        html.append("        .error-list { background: #ffebee; border-radius: 8px; padding: 18px 24px; box-shadow: 0 1px 4px #ffcdd2; }\n");
        html.append("        .error-list h4 { color: #b71c1c; margin-top: 0; }\n");
        html.append("        .error-item { margin: 5px 0; padding-left: 8px; border-left: 3px solid #e57373; }\n");
        html.append("        .file-preview { background: #fffde7; border-radius: 8px; padding: 18px 24px; box-shadow: 0 1px 4px #ffe082; }\n");
        html.append("        .file-preview h3 { color: #f57c00; margin-top: 0; }\n");
        html.append("        .file-preview pre { margin: 0; white-space: pre-wrap; font-family: 'Fira Mono', 'Consolas', monospace; font-size: 1em; }\n");
        html.append("        .error-highlight { background-color: #ffccbc; color: #b71c1c; border-radius: 4px; padding: 0 2px; }\n");
        html.append("        .line-number { color: #a1887f; margin-right: 10px; }\n");
        html.append("        .segment-name { color: #ff7043; font-weight: bold; }\n");
        html.append("        .footer { text-align: center; color: #a1887f; font-size: 0.95em; margin: 40px 0 0 0; padding: 16px 0 0 0; border-top: 1px solid #ffe0b2; }\n");
        html.append("        @media (max-width: 900px) { .main-content { max-width: 98vw; } .main-grid { grid-template-columns: 1fr; } }\n");
        html.append("        @media (max-width: 800px) { .card { padding: 16px 6vw; } }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <div class=\"header\">\n");
        html.append("        <h1>EDI 834 Validation Test Report</h1>\n");
        html.append("        <div class=\"timestamp\">Generated: ").append(LocalDateTime.now().format(DATE_FORMAT)).append("</div>\n");
        html.append("    </div>\n");
        html.append("    <div class=\"main-content\">\n");

        // Navigation to summary (move here, before main-grid)
        if (summaryReportLink != null && !summaryReportLink.isEmpty()) {
            html.append("    <div class=\"nav-summary centered\"><button onclick=\"window.location.href='" + summaryReportLink + "'\" style=\"background: linear-gradient(90deg, #ff7043 0%, #ffa726 100%); color: #fff; padding: 12px 32px; border: none; border-radius: 24px; cursor: pointer; font-size: 1.1em; font-weight: 600; box-shadow: 0 2px 6px #ffccbc; transition: background 0.2s, box-shadow 0.2s;\">Back to Summary Report</button></div>\n");
        }

        html.append("    <div class=\"main-grid\">\n");
        // Left column
        html.append("      <div class=\"main-col\">\n");
        html.append("        <div class=\"card test-description\">\n");
        html.append("            <h3>Test Description</h3>\n");
        html.append("            <p>").append(getTestDescription(testName)).append("</p>\n");
        html.append("        </div>\n");
        html.append("        <div class=\"card expected-validations\">\n");
        html.append("            <h3>Expected Validations</h3>\n");
        html.append("            <ul>\n");
        for (String validation : getExpectedValidations(testName)) {
            html.append("                <li>").append(validation).append("</li>\n");
        }
        html.append("            </ul>\n");
        html.append("        </div>\n");
        html.append("        <div class=\"card error-list\">\n");
        html.append("            <h3>Validation Details</h3>\n");
        if (validationErrors.isEmpty()) {
            html.append("            <p>No validation errors found.</p>\n");
        } else {
            html.append("            <div class=\"error-list\">\n");
            html.append("                <h4>Validation Errors (").append(validationErrors.size()).append("):</h4>\n");
            for (String error : validationErrors) {
                html.append("                <div class=\"error-item\">").append(error).append("</div>\n");
            }
            html.append("            </div>\n");
        }
        html.append("        </div>\n");
        html.append("      </div>\n");
        // Right column
        html.append("      <div class=\"main-col\">\n");
        html.append("        <div class=\"card result ").append(passed ? "success" : "failure").append("\">\n");
        html.append("            <h2>Test: ").append(testName).append("</h2>\n");
        html.append("            <p>Status: ").append(passed ? "PASSED" : "FAILED").append("</p>\n");
        html.append("        </div>\n");
        html.append("        <div class=\"card file-info\">\n");
        html.append("            <h3>File Information</h3>\n");
        html.append("            <p>File Path: ").append(filePath).append("</p>\n");
        File file = new File(filePath);
        if (file.exists()) {
            html.append("            <p>File Size: ").append(file.length()).append(" bytes</p>\n");
            html.append("            <p>Last Modified: ").append(LocalDateTime.ofInstant(
                Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault()).format(DATE_FORMAT)).append("</p>\n");
        }
        html.append("        </div>\n");
        html.append("        <div class=\"card file-preview\">\n");
        html.append("            <h3>File Preview with Validation Highlights</h3>\n");
        html.append("            <pre>\n");
        try {
            List<String> fileLines = Files.readAllLines(Paths.get(filePath));
            List<Integer> errorLines = extractErrorLineNumbers(validationErrors);
            List<String> expectedValidations = getExpectedValidations(testName);
            
            for (int i = 0; i < fileLines.size(); i++) {
                String line = fileLines.get(i);
                int lineNumber = i + 1;
                
                html.append("<span class=\"line-number\">").append(String.format("%3d", lineNumber)).append("</span>");
                
                String[] fields = line.split("\\*");
                if (fields.length > 0) {
                    html.append("<span class=\"segment-name\">").append(fields[0]).append("</span>*");
                    
                    for (int j = 1; j < fields.length; j++) {
                        if (errorLines.contains(lineNumber)) {
                            html.append("<span class=\"error-highlight\">").append(fields[j]).append("</span>");
                        } else if (passed && isExpectedValidation(fields[0], fields[j], expectedValidations)) {
                            html.append("<span class=\"success-highlight\">").append(fields[j]).append("</span>");
                        } else {
                            html.append(fields[j]);
                        }
                        if (j < fields.length - 1) {
                            html.append("*");
                        }
                    }
                } else {
                    html.append(line);
                }
                html.append("\n");
            }
        } catch (IOException e) {
            html.append("Error reading file: ").append(e.getMessage());
        }
        html.append("            </pre>\n");
        html.append("        </div>\n");
        html.append("      </div>\n");
        html.append("    </div>\n"); // End main-grid

        // Footer
        html.append("    <div class=\"footer\">Generated by EDI 834 Validator &mdash; ").append(LocalDateTime.now().format(DATE_FORMAT)).append("</div>\n");

        html.append("    </div>\n"); // End main-content
        html.append("</body>\n");
        html.append("</html>");

        return html.toString();
    }

    private static String generateSummaryHtmlContent(Map<String, String> testReportLinks, List<String> allErrors, String filePath) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"en\">\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("    <title>EDI 834 Validation Summary Report</title>\n");
        html.append("    <link href=\"https://fonts.googleapis.com/css?family=Inter:400,600,700&display=swap\" rel=\"stylesheet\">\n");
        html.append("    <style>\n");
        html.append("        body { font-family: 'Inter', Arial, sans-serif; margin: 0; background: #fff7f0; color: #3d2c1e; }\n");
        html.append("        .header { background: linear-gradient(90deg, #ffb347 0%, #ffcc80 100%); color: #5d4037; padding: 32px 0 24px 0; text-align: center; box-shadow: 0 2px 8px #ffecb3; }\n");
        html.append("        .header h1 { margin: 0 0 8px 0; font-size: 2.5em; font-weight: 700; letter-spacing: 1px; }\n");
        html.append("        .timestamp { color: #a1887f; font-size: 1em; margin-bottom: 8px; }\n");
        html.append("        .main-content { max-width: 1100px; margin: 0 auto; padding: 0 12px; }\n");
        html.append("        .main-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 24px; }\n");
        html.append("        .main-col { display: flex; flex-direction: column; gap: 12px; }\n");
        html.append("        .card { background: #fff3e0; border-radius: 12px; box-shadow: 0 2px 8px #ffe0b2; padding: 24px 32px; }\n");
        html.append("        .nav-links { display: flex; flex-direction: column; align-items: center; margin: 32px 0 0 0; }\n");
        html.append("        .nav-title { font-size: 1.3em; font-weight: 600; color: #bf360c; margin-bottom: 16px; }\n");
        html.append("        .nav-btns { display: flex; gap: 24px; }\n");
        html.append("        .nav-btn { background: linear-gradient(90deg, #ff7043 0%, #ffa726 100%); color: #fff; padding: 12px 32px; border: none; border-radius: 24px; cursor: pointer; font-size: 1.1em; font-weight: 600; box-shadow: 0 2px 6px #ffccbc; transition: background 0.2s, box-shadow 0.2s; }\n");
        html.append("        .nav-btn:hover { background: linear-gradient(90deg, #ffa726 0%, #ff7043 100%); box-shadow: 0 4px 12px #ffccbc; }\n");
        html.append("        .file-info { background: #ffe0b2; border-radius: 8px; padding: 18px 24px; margin: 24px 0; box-shadow: 0 1px 4px #ffe0b2; }\n");
        html.append("        .file-info h3 { margin-top: 0; color: #e65100; }\n");
        html.append("        .error-list { background: #ffebee; border-radius: 8px; padding: 18px 24px; box-shadow: 0 1px 4px #ffcdd2; }\n");
        html.append("        .error-list h4 { color: #b71c1c; margin-top: 0; }\n");
        html.append("        .error-item { margin: 5px 0; padding-left: 8px; border-left: 3px solid #e57373; }\n");
        html.append("        .file-preview { background: #fffde7; border-radius: 8px; padding: 18px 24px; box-shadow: 0 1px 4px #ffe082; }\n");
        html.append("        .file-preview h3 { color: #f57c00; margin-top: 0; }\n");
        html.append("        .file-preview pre { margin: 0; white-space: pre-wrap; font-family: 'Fira Mono', 'Consolas', monospace; font-size: 1em; }\n");
        html.append("        .footer { text-align: center; color: #a1887f; font-size: 0.95em; margin: 40px 0 0 0; padding: 16px 0 0 0; border-top: 1px solid #ffe0b2; }\n");
        html.append("        .error-highlight { background-color: #ffccbc; color: #b71c1c; border-radius: 4px; padding: 0 2px; }\n");
        html.append("        .line-number { color: #a1887f; margin-right: 10px; }\n");
        html.append("        .segment-name { color: #ff7043; font-weight: bold; }\n");
        html.append("        @media (max-width: 900px) { .main-content { max-width: 98vw; } .main-grid { grid-template-columns: 1fr; } }\n");
        html.append("        @media (max-width: 800px) { .card { padding: 16px 6vw; } }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <div class=\"header\">\n");
        html.append("        <h1>EDI 834 Validation Summary Report</h1>\n");
        html.append("        <div class=\"timestamp\">Generated: ").append(LocalDateTime.now().format(DATE_FORMAT)).append("</div>\n");
        html.append("        <div class=\"desc\">This summary report contains all validation errors found in the EDI file. Use the navigation below to view detailed results for each test aspect.</div>\n");
        html.append("    </div>\n");

        // Navigation links
        html.append("    <div class=\"card nav-links\">\n");
        html.append("        <div class=\"nav-title\">Individual Test Results</div>\n");
        html.append("        <div class=\"nav-btns\">\n");
        for (String key : new String[]{"Date Formats", "Loop Identifiers", "Required Segments"}) {
            if (testReportLinks.containsKey(key)) {
                html.append("            <button class=\"nav-btn\" onclick=\"window.location.href='" + testReportLinks.get(key) + "'\">" + key + "</button>\n");
            }
        }
        html.append("        </div>\n");
        html.append("    </div>\n");

        // File Information
        html.append("    <div class=\"card file-info\">\n");
        html.append("        <h3>File Information</h3>\n");
        html.append("        <p>File Path: ").append(filePath).append("</p>\n");
        File file = new File(filePath);
        if (file.exists()) {
            html.append("        <p>File Size: ").append(file.length()).append(" bytes</p>\n");
            html.append("        <p>Last Modified: ").append(LocalDateTime.ofInstant(
                Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault()).format(DATE_FORMAT)).append("</p>\n");
        }
        html.append("    </div>\n");

        // Side-by-side grid for errors and file preview
        html.append("    <div class=\"main-grid\">\n");
        // Left: All validation errors
        html.append("      <div class=\"main-col\">\n");
        html.append("        <div class=\"card error-list\">\n");
        html.append("            <h4>All Validation Errors (").append(allErrors.size()).append("):</h4>\n");
        for (String error : allErrors) {
            html.append("            <div class=\"error-item\">").append(error).append("</div>\n");
        }
        html.append("        </div>\n");
        html.append("      </div>\n");
        // Right: File preview
        html.append("      <div class=\"main-col\">\n");
        html.append("        <div class=\"card file-preview\">\n");
        html.append("            <h3>File Preview with Error Highlights</h3>\n");
        html.append("            <pre>\n");
        try {
            List<String> fileLines = Files.readAllLines(Paths.get(filePath));
            List<Integer> errorLines = extractErrorLineNumbers(allErrors);
            for (int i = 0; i < fileLines.size(); i++) {
                String line = fileLines.get(i);
                int lineNumber = i + 1;
                html.append("<span class=\"line-number\">").append(String.format("%3d", lineNumber)).append("</span>");
                String[] fields = line.split("\\*");
                if (fields.length > 0) {
                    html.append("<span class=\"segment-name\">").append(fields[0]).append("</span>*");
                    for (int j = 1; j < fields.length; j++) {
                        if (errorLines.contains(lineNumber)) {
                            html.append("<span class=\"error-highlight\">").append(fields[j]).append("</span>");
                        } else {
                            html.append(fields[j]);
                        }
                        if (j < fields.length - 1) {
                            html.append("*");
                        }
                    }
                } else {
                    html.append(line);
                }
                html.append("\n");
            }
        } catch (IOException e) {
            html.append("Error reading file: ").append(e.getMessage());
        }
        html.append("            </pre>\n");
        html.append("        </div>\n");
        html.append("      </div>\n");
        html.append("    </div>\n"); // End main-grid

        // Footer
        html.append("    <div class=\"footer\">Generated by EDI 834 Validator &mdash; ").append(LocalDateTime.now().format(DATE_FORMAT)).append("</div>\n");

        html.append("    </div>\n"); // End main-content
        html.append("</body>\n");
        html.append("</html>");

        return html.toString();
    }

    private static boolean isExpectedValidation(String segment, String field, List<String> expectedValidations) {
        for (String validation : expectedValidations) {
            if (validation.toLowerCase().contains(segment.toLowerCase()) && 
                validation.toLowerCase().contains(field.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private static List<Integer> extractErrorLineNumbers(List<String> validationErrors) {
        List<Integer> errorLines = new ArrayList<>();
        Pattern linePattern = Pattern.compile("Line (\\d+):");
        
        for (String error : validationErrors) {
            java.util.regex.Matcher matcher = linePattern.matcher(error);
            if (matcher.find()) {
                try {
                    errorLines.add(Integer.parseInt(matcher.group(1)));
                } catch (NumberFormatException e) {
                    // Skip if line number can't be parsed
                }
            }
        }
        return errorLines;
    }
} 