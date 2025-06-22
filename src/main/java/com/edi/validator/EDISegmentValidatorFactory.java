package com.edi.validator;

import com.edi.validator.interfaces.EDISegmentValidator;
import java.util.HashMap;
import java.util.Map;

public class EDISegmentValidatorFactory {
    private static final Map<String, EDISegmentValidator> validators = new HashMap<>();

    static {
        // Register all segment validators
        validators.put("ISA", new ISASegmentValidator());
        validators.put("GS", new GSSegmentValidator());
        validators.put("ST", new STSegmentValidator());
        validators.put("BGN", new BGNSegmentValidator());
        validators.put("N1", new N1SegmentValidator());
        validators.put("INS", new INSSegmentValidator());
        validators.put("REF", new REFSegmentValidator());
        validators.put("NM1", new NM1SegmentValidator());
        validators.put("DMG", new DMGSegmentValidator());
        validators.put("HD", new HDSegmentValidator());
        validators.put("DTP", new DTPSegmentValidator());
        validators.put("AMT", new AMTSegmentValidator());
        validators.put("LX", new LXSegmentValidator());
        validators.put("PLA", new PLASegmentValidator());
        validators.put("LS", new LSSegmentValidator());
        validators.put("LE", new LESegmentValidator());
        validators.put("SV1", new SV1SegmentValidator());
        validators.put("SV2", new SV2SegmentValidator());
        validators.put("SV3", new SV3SegmentValidator());
        validators.put("SV4", new SV4SegmentValidator());
        validators.put("SV5", new SV5SegmentValidator());
        validators.put("SV6", new SV6SegmentValidator());
        validators.put("SV7", new SV7SegmentValidator());
        validators.put("SV8", new SV8SegmentValidator());
    }

    public static EDISegmentValidator getValidator(String segmentCode) {
        return validators.get(segmentCode);
    }

    public static boolean hasValidator(String segmentCode) {
        return validators.containsKey(segmentCode);
    }
} 