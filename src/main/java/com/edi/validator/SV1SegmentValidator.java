package com.edi.validator;

import com.edi.validator.interfaces.EDISegmentValidator;
import com.edi.validator.model.EDISegment;
import com.edi.validator.model.SV1Segment;
import com.edi.validator.model.ValidationError;
import java.util.ArrayList;
import java.util.List;

public class SV1SegmentValidator implements EDISegmentValidator {
    @Override
    public List<ValidationError> validate(EDISegment segment) {
        List<ValidationError> errors = new ArrayList<>();
        
        if (!(segment instanceof SV1Segment)) {
            errors.add(new ValidationError(SV1Segment.SEGMENT_CODE, "Segment Type", 
                "Invalid segment type", segment.getLineNumber()));
            return errors;
        }

        SV1Segment sv1Segment = (SV1Segment) segment;

        // Validate number of fields
        if (segment.getFields().size() < 7) {
            errors.add(new ValidationError(SV1Segment.SEGMENT_CODE, "Fields", 
                "SV1 segment has insufficient fields (expected 7, found " + segment.getFields().size() + ")",
                segment.getLineNumber()));
            return errors;
        }

        // Validate composite medical procedure identifier
        String procedureId = sv1Segment.getCompositeMedicalProcedureIdentifier();
        if (!procedureId.matches("^(HC|IV|ZZ)\\d{5}$")) {
            errors.add(new ValidationError(SV1Segment.SEGMENT_CODE, "Composite Medical Procedure Identifier",
                "Invalid procedure identifier format: " + procedureId,
                segment.getLineNumber()));
        }

        // Validate monetary amount
        String monetaryAmount = sv1Segment.getMonetaryAmount();
        if (!monetaryAmount.matches("^\\d+(\\.\\d{2})?$")) {
            errors.add(new ValidationError(SV1Segment.SEGMENT_CODE, "Monetary Amount",
                "Invalid monetary amount format: " + monetaryAmount + " (must be a valid decimal number)",
                segment.getLineNumber()));
        }

        // Validate unit basis for measurement code
        String unitBasis = sv1Segment.getUnitBasisForMeasurementCode();
        if (!unitBasis.matches("^(DA|F2|MJ|UN)$")) {
            errors.add(new ValidationError(SV1Segment.SEGMENT_CODE, "Unit Basis For Measurement Code",
                "Invalid unit basis code: " + unitBasis,
                segment.getLineNumber()));
        }

        // Validate quantity
        String quantity = sv1Segment.getQuantity();
        if (!quantity.matches("^\\d+(\\.\\d{1,2})?$")) {
            errors.add(new ValidationError(SV1Segment.SEGMENT_CODE, "Quantity",
                "Invalid quantity format: " + quantity + " (must be a valid decimal number)",
                segment.getLineNumber()));
        }

        // Validate facility code value
        String facilityCode = sv1Segment.getFacilityCodeValue();
        if (!facilityCode.matches("^(11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|63|64|65|66|67|68|69|70|71|72|73|74|75|76|77|78|79|80|81|82|83|84|85|86|87|88|89|90|91|92|93|94|95|96|97|98|99)$")) {
            errors.add(new ValidationError(SV1Segment.SEGMENT_CODE, "Facility Code Value",
                "Invalid facility code: " + facilityCode,
                segment.getLineNumber()));
        }

        // Validate service type code
        String serviceType = sv1Segment.getServiceTypeCode();
        if (!serviceType.matches("^(1|2|3|4|5|6|7|8|9|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z)$")) {
            errors.add(new ValidationError(SV1Segment.SEGMENT_CODE, "Service Type Code",
                "Invalid service type code: " + serviceType,
                segment.getLineNumber()));
        }

        // Validate diagnosis code pointer
        String diagnosisPointer = sv1Segment.getDiagnosisCodePointer();
        if (!diagnosisPointer.matches("^[1-9]$")) {
            errors.add(new ValidationError(SV1Segment.SEGMENT_CODE, "Diagnosis Code Pointer",
                "Invalid diagnosis code pointer: " + diagnosisPointer + " (must be 1-9)",
                segment.getLineNumber()));
        }

        return errors;
    }

    @Override
    public String getSegmentCode() {
        return SV1Segment.SEGMENT_CODE;
    }
} 