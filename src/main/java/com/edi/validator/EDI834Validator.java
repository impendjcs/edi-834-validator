package com.edi.validator;

import com.edi.validator.model.EDISegment;
import com.edi.validator.model.ISASegment;
import com.edi.validator.model.GSSegment;
import com.edi.validator.model.STSegment;
import com.edi.validator.model.BGNSegment;
import com.edi.validator.model.N1Segment;
import com.edi.validator.model.INSSegment;
import com.edi.validator.model.REFSegment;
import com.edi.validator.model.NM1Segment;
import com.edi.validator.model.DMGSegment;
import com.edi.validator.model.HDSegment;
import com.edi.validator.model.DTPSegment;
import com.edi.validator.model.AMTSegment;
import com.edi.validator.model.LXSegment;
import com.edi.validator.model.PLASegment;
import com.edi.validator.model.LSSegment;
import com.edi.validator.model.LESegment;
import com.edi.validator.model.SV1Segment;
import com.edi.validator.model.SV2Segment;
import com.edi.validator.model.SV3Segment;
import com.edi.validator.model.SV4Segment;
import com.edi.validator.model.SV5Segment;
import com.edi.validator.model.SV6Segment;
import com.edi.validator.model.SV7Segment;
import com.edi.validator.model.SV8Segment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EDI834Validator extends EDIValidator {
    private static final Logger logger = LoggerFactory.getLogger(EDI834Validator.class);
    private static final Set<String> REQUIRED_SEGMENTS = new HashSet<>(Arrays.asList(
        "ISA", "GS", "ST", "BGN", "N1", "INS", "REF", "NM1", "DMG", "HD", "DTP", "AMT", "LX", "PLA", "LS", "LE"
    ));

    public EDI834Validator() {
        super();
    }

    @Override
    protected EDISegment createSegment(String segmentCode, String line) {
        switch (segmentCode) {
            case "ISA": return new ISASegment(line, getCurrentLineNumber());
            case "GS": return new GSSegment(line, getCurrentLineNumber());
            case "ST": return new STSegment(line, getCurrentLineNumber());
            case "BGN": return new BGNSegment(line, getCurrentLineNumber());
            case "N1": return new N1Segment(line, getCurrentLineNumber());
            case "INS": return new INSSegment(line, getCurrentLineNumber());
            case "REF": return new REFSegment(line, getCurrentLineNumber());
            case "NM1": return new NM1Segment(line, getCurrentLineNumber());
            case "DMG": return new DMGSegment(line, getCurrentLineNumber());
            case "HD": return new HDSegment(line, getCurrentLineNumber());
            case "DTP": return new DTPSegment(line, getCurrentLineNumber());
            case "AMT": return new AMTSegment(line, getCurrentLineNumber());
            case "LX": return new LXSegment(line, getCurrentLineNumber());
            case "PLA": return new PLASegment(line, getCurrentLineNumber());
            case "LS": return new LSSegment(line, getCurrentLineNumber());
            case "LE": return new LESegment(line, getCurrentLineNumber());
            case "SV1": return new SV1Segment(line, getCurrentLineNumber());
            case "SV2": return new SV2Segment(line, getCurrentLineNumber());
            case "SV3": return new SV3Segment(line, getCurrentLineNumber());
            case "SV4": return new SV4Segment(line, getCurrentLineNumber());
            case "SV5": return new SV5Segment(line, getCurrentLineNumber());
            case "SV6": return new SV6Segment(line, getCurrentLineNumber());
            case "SV7": return new SV7Segment(line, getCurrentLineNumber());
            case "SV8": return new SV8Segment(line, getCurrentLineNumber());
            default:
                logger.debug("Unknown segment code: {}", segmentCode);
                return null;
        }
    }

    @Override
    protected void validateRequiredSegments() {
        for (String segmentCode : REQUIRED_SEGMENTS) {
            if (!isSegmentPresent(segmentCode)) {
                addValidationError(segmentCode, "Segment", "Missing required " + segmentCode + " segment");
            }
        }
    }
}