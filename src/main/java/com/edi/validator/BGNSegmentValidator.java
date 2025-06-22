package com.edi.validator;

import com.edi.validator.interfaces.EDISegmentValidator;
import com.edi.validator.model.EDISegment;
import com.edi.validator.model.BGNSegment;
import com.edi.validator.model.ValidationError;
import java.util.ArrayList;
import java.util.List;

public class BGNSegmentValidator implements EDISegmentValidator {
    @Override
    public List<ValidationError> validate(EDISegment segment) {
        List<ValidationError> errors = new ArrayList<>();
        
        if (!(segment instanceof BGNSegment)) {
            errors.add(new ValidationError(BGNSegment.SEGMENT_CODE, "Segment Type", 
                "Invalid segment type", segment.getLineNumber()));
            return errors;
        }

        BGNSegment bgnSegment = (BGNSegment) segment;

        // Validate number of fields
        if (segment.getFields().size() < 5) {
            errors.add(new ValidationError(BGNSegment.SEGMENT_CODE, "Fields", 
                "BGN segment has insufficient fields (expected 5, found " + segment.getFields().size() + ")",
                segment.getLineNumber()));
            return errors;
        }

        // Validate transaction set purpose code
        String purposeCode = bgnSegment.getTransactionSetPurposeCode();
        if (!purposeCode.matches("^(00|01|02|03|04|05|06|07|08|09|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|63|64|65|66|67|68|69|70|71|72|73|74|75|76|77|78|79|80|81|82|83|84|85|86|87|88|89|90|91|92|93|94|95|96|97|98|99)$")) {
            errors.add(new ValidationError(BGNSegment.SEGMENT_CODE, "Transaction Set Purpose Code",
                "Invalid transaction set purpose code: " + purposeCode,
                segment.getLineNumber()));
        }

        // Validate date format (CCYYMMDD)
        String date = bgnSegment.getDate();
        if (!date.matches("^\\d{8}$")) {
            errors.add(new ValidationError(BGNSegment.SEGMENT_CODE, "Date",
                "Invalid date format: " + date + " (must be CCYYMMDD)",
                segment.getLineNumber()));
        }

        // Validate time format (HHMM)
        String time = bgnSegment.getTime();
        if (!time.matches("^\\d{4}$")) {
            errors.add(new ValidationError(BGNSegment.SEGMENT_CODE, "Time",
                "Invalid time format: " + time + " (must be HHMM)",
                segment.getLineNumber()));
        }

        return errors;
    }

    @Override
    public String getSegmentCode() {
        return BGNSegment.SEGMENT_CODE;
    }
} 