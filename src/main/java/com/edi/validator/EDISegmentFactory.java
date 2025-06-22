package com.edi.validator;

import com.edi.validator.model.*;

public class EDISegmentFactory {
    public static EDISegment createSegment(String segmentCode, String line, int lineNumber) {
        switch (segmentCode) {
            case "ISA":
                return new ISASegment(line, lineNumber);
            case "GS":
                return new GSSegment(line, lineNumber);
            case "ST":
                return new STSegment(line, lineNumber);
            case "BGN":
                return new BGNSegment(line, lineNumber);
            case "N1":
                return new N1Segment(line, lineNumber);
            case "INS":
                return new INSSegment(line, lineNumber);
            case "REF":
                return new REFSegment(line, lineNumber);
            case "NM1":
                return new NM1Segment(line, lineNumber);
            case "DMG":
                return new DMGSegment(line, lineNumber);
            case "HD":
                return new HDSegment(line, lineNumber);
            case "DTP":
                return new DTPSegment(line, lineNumber);
            case "AMT":
                return new AMTSegment(line, lineNumber);
            case "LX":
                return new LXSegment(line, lineNumber);
            case "PLA":
                return new PLASegment(line, lineNumber);
            case "LS":
                return new LSSegment(line, lineNumber);
            case "LE":
                return new LESegment(line, lineNumber);
            case "SV1":
                return new SV1Segment(line, lineNumber);
            case "SV2":
                return new SV2Segment(line, lineNumber);
            case "SV3":
                return new SV3Segment(line, lineNumber);
            case "SV4":
                return new SV4Segment(line, lineNumber);
            case "SV5":
                return new SV5Segment(line, lineNumber);
            case "SV6":
                return new SV6Segment(line, lineNumber);
            case "SV7":
                return new SV7Segment(line, lineNumber);
            case "SV8":
                return new SV8Segment(line, lineNumber);
            default:
                return new GenericSegment(segmentCode, line, lineNumber);
        }
    }
} 