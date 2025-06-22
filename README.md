# EDI 834 Validation Framework

A comprehensive, modular Java-based validation framework for EDI 834 files, specifically designed for NY State Health Commerce System (HCS) requirements. This framework provides extensive validation capabilities without relying on third-party EDI parsing libraries.

## 🚀 Features

- **Modular Architecture**: Separate validator classes for each EDI segment
- **Factory Pattern**: Clean creation and validation of EDI segments
- **Comprehensive Validation**: Validates all major EDI 834 segments (ISA, GS, ST, BGN, N1, INS, REF, NM1, DMG, HD, DTP, AMT, LX, PLA, LS, LE, SV1-SV8)
- **NY HCS Compliance**: Built specifically for NY State Health Commerce System requirements
- **Detailed Reporting**: Generates beautiful HTML reports with error highlighting
- **Extensible Design**: Easy to add new segments and validation rules
- **No External Dependencies**: Uses only Java Standard Library and Apache Commons Lang

## 📋 Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

## 🏗️ Project Structure

```
edi-validator/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── edi/
│   │               └── validator/
│   │                   ├── interfaces/
│   │                   │   └── EDISegmentValidator.java
│   │                   ├── model/
│   │                   │   ├── EDISegment.java
│   │                   │   ├── ValidationError.java
│   │                   │   ├── ISASegment.java
│   │                   │   ├── GSSegment.java
│   │                   │   └── ... (other segment models)
│   │                   ├── EDI834Validator.java
│   │                   ├── EDIValidator.java
│   │                   ├── EDISegmentFactory.java
│   │                   ├── EDISegmentValidatorFactory.java
│   │                   ├── TestReportGenerator.java
│   │                   ├── ISASegmentValidator.java
│   │                   ├── GSSegmentValidator.java
│   │                   └── ... (other segment validators)
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── edi/
│       │           └── validator/
│       │               └── EDI834ValidatorTest.java
│       └── resources/
│           └── valid-834.edi
├── data/
│   └── sample-834Dental.edi
├── target/
│   └── test-reports/
│       └── [HTML test reports]
├── docs/
│   └── EDI834_KT_Guide.docx.md
├── pom.xml
└── README.md
```

## 🚀 Quick Start

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/edi-834-validator.git
   cd edi-834-validator
   ```

2. **Build the project**:
   ```bash
   mvn clean install
   ```

3. **Run validation tests**:
   ```bash
   mvn test
   ```

4. **View the generated reports**:
   - The test will automatically open the summary report in your browser
   - Reports are also available in `target/test-reports/`

## 📖 Usage

### Basic Validation

```java
import com.edi.validator.EDI834Validator;
import com.edi.validator.model.ValidationError;
import java.util.List;

EDI834Validator validator = new EDI834Validator();
List<ValidationError> errors = validator.validate("path/to/your/834file.edi");

if (errors.isEmpty()) {
    System.out.println("Validation passed!");
} else {
    System.out.println("Validation failed with " + errors.size() + " errors:");
    errors.forEach(System.out::println);
}
```

### Custom Validation

The framework is designed to be easily extensible. To add validation for a new segment:

1. Create a new segment model class extending `EDISegment`
2. Create a new validator class implementing `EDISegmentValidator`
3. Register the validator in `EDISegmentValidatorFactory`

## 🔍 Validation Rules

The framework validates the following aspects:

### Required Segments
- ISA (Interchange Control Header)
- GS (Functional Group Header)
- ST (Transaction Set Header)
- BGN (Beginning Segment)
- N1 (Name Segment)
- INS (Member Level Detail)
- REF (Reference Information)
- NM1 (Individual Name)
- DMG (Demographic Information)
- HD (Health Coverage)
- DTP (Date/Time Reference)
- AMT (Monetary Amount)
- LX (Assigned Number)
- PLA (Provider Level Adjustment)
- LS/LE (Loop Header/Trailer)
- SV1-SV8 (Service Line)

### Field Validations
- **Date Formats**: CCYYMMDD format validation
- **SSN Format**: 9-digit validation
- **Entity Codes**: NY HCS specific entity identifier validation
- **Amount Formats**: Decimal number validation
- **Loop Identifiers**: Valid loop structure validation

## 📊 Reporting

The framework generates comprehensive HTML reports including:

- **Summary Report**: Overview of all validation errors
- **Individual Test Reports**: Detailed reports for specific validation aspects
- **File Preview**: EDI file content with error highlighting
- **Navigation**: Easy navigation between different report types

## 🧪 Testing

Run the complete test suite:

```bash
mvn test
```

This will:
1. Validate the sample EDI file
2. Generate individual test reports
3. Create a summary report
4. Automatically open the summary in your browser

## 🔧 Configuration

### Adding New Segments

1. **Create Segment Model**:
   ```java
   public class NewSegment extends EDISegment {
       public static final String SEGMENT_CODE = "NEW";
       
       public NewSegment(String line, int lineNumber) {
           super(SEGMENT_CODE, line, lineNumber);
       }
       
       // Add getter methods for specific fields
   }
   ```

2. **Create Validator**:
   ```java
   public class NewSegmentValidator implements EDISegmentValidator {
       @Override
       public List<ValidationError> validate(EDISegment segment) {
           // Implement validation logic
       }
       
       @Override
       public String getSegmentCode() {
           return NewSegment.SEGMENT_CODE;
       }
   }
   ```

3. **Register in Factory**:
   ```java
   // In EDISegmentValidatorFactory
   validators.put("NEW", new NewSegmentValidator());
   ```

## 📚 Documentation

- **Knowledge Transfer Guide**: See `docs/EDI834_KT_Guide.docx.md` for detailed technical documentation
- **Code Comments**: Extensive inline documentation in all classes
- **Test Examples**: See test classes for usage examples

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

For questions or issues:
1. Check the documentation in `docs/`
2. Review the test examples
3. Open an issue on GitHub

## 🔄 Version History

- **v1.0.0**: Initial modular framework with comprehensive validation
- **v0.9.0**: Refactored from monolithic to modular architecture
- **v0.8.0**: Added HTML reporting capabilities
- **v0.7.0**: Implemented factory patterns
- **v0.6.0**: Added individual segment validators
- **v0.5.0**: Basic EDI 834 validation framework 