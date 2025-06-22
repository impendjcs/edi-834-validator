package com.edi.validator;

import com.edi.validator.model.EDISegment;
import com.edi.validator.model.ISASegment;
import com.edi.validator.model.GSSegment;
import com.edi.validator.model.STSegment;
import com.edi.validator.model.BGNSegment;
import com.edi.validator.model.N1Segment;
import com.edi.validator.model.INSSegment;
import com.edi.validator.model.REFSegment;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Set;

public class EDI834Validator extends EDIValidator {
    private static final Logger logger = LoggerFactory.getLogger(EDI834Validator.class);
    private final List<String> validationErrors;
    private static final Pattern SSN_PATTERN = Pattern.compile("^\\d{9}$");
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{8}$");
    private int currentLineNumber;
    private static final Set<String> REQUIRED_SEGMENTS = new HashSet<>(Arrays.asList(
        "ISA", "GS", "ST", "BGN", "N1", "INS", "REF", "NM1", "DMG", "HD", "DTP", "AMT"
    ));

    public EDI834Validator() {
        this.validationErrors = new ArrayList<>();
        this.currentLineNumber = 0;
    }

    public boolean validateEDI834(String ediFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ediFilePath))) {
            String line;
            boolean hasISA = false;
            boolean hasGS = false;
            boolean hasST = false;
            boolean hasBGN = false;
            boolean hasN1 = false;
            boolean hasINS = false;
            boolean hasREF = false;
            boolean hasNM1 = false;
            boolean hasDMG = false;
            boolean hasHD = false;
            boolean hasDTP = false;
            boolean hasAMT = false;
            boolean hasLX = false;
            boolean hasPLA = false;
            boolean hasLS = false;
            boolean hasLE = false;

            while ((line = reader.readLine()) != null) {
                currentLineNumber++;
                line = line.trim();
                if (line.endsWith("~")) {
                    line = line.substring(0, line.length() - 1);
                }
                if (line.startsWith("ISA")) {
                    hasISA = true;
                    validateISA(line);
                } else if (line.startsWith("GS")) {
                    hasGS = true;
                    validateGS(line);
                } else if (line.startsWith("ST")) {
                    hasST = true;
                    validateST(line);
                } else if (line.startsWith("BGN")) {
                    hasBGN = true;
                    validateBGN(line);
                } else if (line.startsWith("N1")) {
                    hasN1 = true;
                    validateN1(line);
                } else if (line.startsWith("INS")) {
                    hasINS = true;
                    validateINS(line);
                } else if (line.startsWith("REF")) {
                    hasREF = true;
                    validateREF(line);
                } else if (line.startsWith("NM1")) {
                    hasNM1 = true;
                    validateNM1(line);
                } else if (line.startsWith("DMG")) {
                    hasDMG = true;
                    validateDMG(line);
                } else if (line.startsWith("HD")) {
                    hasHD = true;
                    validateHD(line);
                } else if (line.startsWith("DTP")) {
                    hasDTP = true;
                    validateDTP(line);
                } else if (line.startsWith("AMT")) {
                    hasAMT = true;
                    validateAMT(line);
                } else if (line.startsWith("LX")) {
                    hasLX = true;
                    validateLX(line);
                } else if (line.startsWith("PLA")) {
                    hasPLA = true;
                    validatePLA(line);
                } else if (line.startsWith("LS")) {
                    hasLS = true;
                    validateLS(line);
                } else if (line.startsWith("LE")) {
                    hasLE = true;
                    validateLE(line);
                }
            }

            // Check for required segments
            if (!hasISA) addValidationError("Missing required ISA segment");
            if (!hasGS) addValidationError("Missing required GS segment");
            if (!hasST) addValidationError("Missing required ST segment");
            if (!hasBGN) addValidationError("Missing required BGN segment");
            if (!hasN1) addValidationError("Missing required N1 segment");
            if (!hasINS) addValidationError("Missing required INS segment");
            if (!hasREF) addValidationError("Missing required REF segment");
            if (!hasNM1) addValidationError("Missing required NM1 segment");
            if (!hasDMG) addValidationError("Missing required DMG segment");
            if (!hasHD) addValidationError("Missing required HD segment");
            if (!hasDTP) addValidationError("Missing required DTP segment");
            if (!hasAMT) addValidationError("Missing required AMT segment");
            if (!hasLX) addValidationError("Missing required LX segment");
            if (!hasPLA) addValidationError("Missing required PLA segment");
            if (!hasLS) addValidationError("Missing required LS segment");
            if (!hasLE) addValidationError("Missing required LE segment");

            return validationErrors.isEmpty();
        } catch (IOException e) {
            logger.error("Error reading EDI file: {}", e.getMessage(), e);
            addValidationError("Error reading EDI file: " + e.getMessage());
            return false;
        }
    }

    private void addValidationError(String error) {
        String detailedError = String.format("Line %d: %s", currentLineNumber, error);
        validationErrors.add(detailedError);
        logger.error(detailedError);
    }

    private void validateISA(String line) {
        String[] fields = line.split("\\*");
        if (fields.length < 16) {
            addValidationError("ISA segment has insufficient fields (expected 16, found " + fields.length + ")");
            return;
        }
        // Validate ISA sender ID (NY HCS specific)
        if (!fields[6].startsWith("NY")) {
            addValidationError("Invalid ISA sender ID: " + fields[6] + " (must start with NY)");
        }
        logger.debug("Validating ISA segment at line {}: {}", currentLineNumber, line);
    }

    private void validateGS(String line) {
        String[] fields = line.split("\\*");
        if (fields.length < 8) {
            addValidationError("GS segment has insufficient fields (expected 8, found " + fields.length + ")");
            return;
        }
        if (!"BE".equals(fields[1])) {
            addValidationError("Invalid functional identifier in GS segment: " + fields[1]);
        }
        // Validate GS sender ID (NY HCS specific)
        if (!fields[2].startsWith("NY")) {
            addValidationError("Invalid GS sender ID: " + fields[2] + " (must start with NY)");
        }
        logger.debug("Validating GS segment at line {}: {}", currentLineNumber, line);
    }

    private void validateST(String line) {
        String[] fields = line.split("\\*");
        if (fields.length < 3) {
            addValidationError("ST segment has insufficient fields (expected 3, found " + fields.length + ")");
            return;
        }
        if (!"834".equals(fields[1])) {
            addValidationError("Invalid transaction set identifier in ST segment: " + fields[1]);
        }
        logger.debug("Validating ST segment at line {}: {}", currentLineNumber, line);
    }

    private void validateBGN(String line) {
        String[] fields = line.split("\\*");
        if (fields.length < 5) {
            addValidationError("BGN segment has insufficient fields (expected 5, found " + fields.length + ")");
            return;
        }
        // Validate BGN date format (CCYYMMDD)
        if (!DATE_PATTERN.matcher(fields[4]).matches()) {
            addValidationError("Invalid BGN date format: " + fields[4] + " (must be CCYYMMDD)");
        }
        logger.debug("Validating BGN segment at line {}: {}", currentLineNumber, line);
    }

    private void validateN1(String line) {
        String[] fields = line.split("\\*");
        if (fields.length < 3) {
            addValidationError("N1 segment has insufficient fields (expected 3, found " + fields.length + ")");
            return;
        }
        // Validate entity identifier codes per NY HCS guide
        String entityCode = fields[1];
        if (!entityCode.matches("^(41|40|ACV|IAE|IN|PE|PR|TV)$")) {
            addValidationError("Invalid entity identifier code in N1 segment: " + entityCode);
        }
        logger.debug("Validating N1 segment at line {}: {}", currentLineNumber, line);
    }

    private void validateINS(String line) {
        String[] fields = line.split("\\*");
        if (fields.length < 9) {
            addValidationError("INS segment has insufficient fields (expected 9, found " + fields.length + ")");
            return;
        }
        // Validate member indicator (Y/N)
        if (!fields[1].matches("^(Y|N)$")) {
            addValidationError("Invalid member indicator in INS segment: " + fields[1]);
        }
        logger.debug("Validating INS segment at line {}: {}", currentLineNumber, line);
    }

    private void validateREF(String line) {
        String[] fields = line.split("\\*");
        if (fields.length < 2) {
            addValidationError("REF segment has insufficient fields (expected 2, found " + fields.length + ")");
            return;
        }
        String refCode = fields[1];
        if ("0F".equals(refCode)) {
            if (!SSN_PATTERN.matcher(fields[2]).matches()) {
                addValidationError("Invalid SSN format in REF segment: " + fields[2]);
            }
        } else if ("1L".equals(refCode)) {
            // Validate member ID format (NY HCS specific)
            if (!fields[2].matches("^[A-Z0-9]{1,20}$")) {
                addValidationError("Invalid member ID format in REF segment: " + fields[2]);
            }
        }
        logger.debug("Validating REF segment at line {}: {}", currentLineNumber, line);
    }

    private void validateNM1(String line) {
        String[] fields = line.split("\\*");
        if (fields.length < 9) {
            addValidationError("NM1 segment has insufficient fields (expected 9, found " + fields.length + ")");
            return;
        }
        // Validate entity type qualifier per NY HCS guide
        String entityType = fields[1];
        if (!entityType.matches("^(IL|70|31|36|M8|74|QD)$")) {
            addValidationError("Invalid entity type qualifier in NM1 segment: " + entityType);
        }
        logger.debug("Validating NM1 segment at line {}: {}", currentLineNumber, line);
    }

    private void validateDMG(String line) {
        String[] fields = line.split("\\*");
        if (fields.length < 3) {
            addValidationError("DMG segment has insufficient fields (expected 3, found " + fields.length + ")");
            return;
        }
        if (!"D8".equals(fields[1])) {
            addValidationError("Invalid date format in DMG segment: " + fields[1]);
        }
        // Validate date format (CCYYMMDD)
        if (!DATE_PATTERN.matcher(fields[2]).matches()) {
            addValidationError("Invalid DMG date format: " + fields[2] + " (must be CCYYMMDD)");
        }
        logger.debug("Validating DMG segment at line {}: {}", currentLineNumber, line);
    }

    private void validateHD(String line) {
        String[] fields = line.split("\\*");
        if (fields.length < 4) {
            addValidationError("HD segment has insufficient fields (expected 4, found " + fields.length + ")");
            return;
        }
        // Validate coverage type per NY HCS guide
        if (!fields[3].matches("^(DEN|HLT|VIS|DNT)$")) {
            addValidationError("Invalid coverage type in HD segment: " + fields[3]);
        }
        logger.debug("Validating HD segment at line {}: {}", currentLineNumber, line);
    }

    private void validateDTP(String line) {
        String[] fields = line.split("\\*");
        if (fields.length < 3) {
            addValidationError("DTP segment has insufficient fields (expected 3, found " + fields.length + ")");
            return;
        }
        if (!"D8".equals(fields[2])) {
            addValidationError("Invalid date format in DTP segment: " + fields[2]);
        }
        // Validate date format (CCYYMMDD)
        if (!DATE_PATTERN.matcher(fields[3]).matches()) {
            addValidationError("Invalid DTP date format: " + fields[3] + " (must be CCYYMMDD)");
        }
        logger.debug("Validating DTP segment at line {}: {}", currentLineNumber, line);
    }

    private void validateAMT(String line) {
        String[] fields = line.split("\\*");
        if (fields.length < 3) {
            addValidationError("AMT segment has insufficient fields (expected 3, found " + fields.length + ")");
            return;
        }
        // Validate amount qualifiers per NY HCS guide
        String qualifier = fields[1];
        if (!qualifier.matches("^(D2|P3|T3|T4|T5|T6|T7|T8|T9)$")) {
            addValidationError("Invalid amount qualifier in AMT segment: " + qualifier);
        }
        logger.debug("Validating AMT segment at line {}: {}", currentLineNumber, line);
    }

    private void validateLX(String line) {
        String[] fields = line.split("\\*");
        if (fields.length < 2) {
            addValidationError("LX segment has insufficient fields (expected 2, found " + fields.length + ")");
            return;
        }
        logger.debug("Validating LX segment at line {}: {}", currentLineNumber, line);
    }

    private void validatePLA(String line) {
        String[] fields = line.split("\\*");
        if (fields.length < 4) {
            addValidationError("PLA segment has insufficient fields (expected 4, found " + fields.length + ")");
            return;
        }
        logger.debug("Validating PLA segment at line {}: {}", currentLineNumber, line);
    }

    private void validateLS(String line) {
        String[] fields = line.split("\\*");
        if (fields.length < 2) {
            addValidationError("LS segment has insufficient fields (expected 2, found " + fields.length + ")");
            return;
        }
        // Validate loop identifier per NY HCS guide
        if (!fields[1].matches("^(2700|2750|2760|2770|2780|2790)$")) {
            addValidationError("Invalid loop identifier in LS segment: " + fields[1]);
        }
        logger.debug("Validating LS segment at line {}: {}", currentLineNumber, line);
    }

    private void validateLE(String line) {
        String[] fields = line.split("\\*");
        if (fields.length < 2) {
            addValidationError("LE segment has insufficient fields (expected 2, found " + fields.length + ")");
            return;
        }
        // Validate loop identifier per NY HCS guide
        if (!fields[1].matches("^(2700|2750|2760|2770|2780|2790)$")) {
            addValidationError("Invalid loop identifier in LE segment: " + fields[1]);
        }
        logger.debug("Validating LE segment at line {}: {}", currentLineNumber, line);
    }

    @Override
    protected EDISegment createSegment(String segmentCode, String line) {
        return EDISegmentFactory.createSegment(segmentCode, line, getCurrentLineNumber());
    }

    @Override
    protected void validateRequiredSegments() {
        for (String segmentCode : REQUIRED_SEGMENTS) {
            if (!isSegmentPresent(segmentCode)) {
                addValidationError(segmentCode, "Segment", "Missing required segment: " + segmentCode);
            }
        }
    }
}