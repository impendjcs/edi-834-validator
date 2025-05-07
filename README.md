# EDI 834 Validator for NY X318 Member Reporting

This project provides a validation framework for EDI 834 files, specifically designed to handle NY X318 Member Reporting standards. It uses Smooks for EDI parsing and validation.

## Features

- Validates required segments and elements in 834 EDI files
- Enforces NY X318 Member Reporting standards
- Validates SSN format (9 digits)
- Generates detailed validation reports
- Supports custom validation rules

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── edi/
│   │           └── validator/
│   │               ├── EDI834Validator.java
│   │               └── Main.java
│   └── resources/
│       ├── smooks-config.xml
│       └── edi-mapping.xml
├── test/
│   └── java/
│       └── com/
│           └── edi/
│               └── validator/
│                   └── EDI834ValidatorTest.java
└── data/                    # Directory for custom EDI files
    ├── sample-834.edi       # Sample valid EDI file
    └── my-custom-834.edi    # Your custom EDI files go here
```

## Usage

1. Build the project:
   ```bash
   mvn clean install
   ```

2. Place your EDI files in the `data` directory:
   ```bash
   # Copy your EDI file to the data directory
   cp path/to/your/834file.edi data/
   ```

3. Run the validator:
   ```bash
   java -cp target/edi-834-validator-1.0-SNAPSHOT.jar com.edi.validator.Main data/your-834-file.edi
   ```

## Validation Rules

The validator checks for:

1. Required segments (ISA, GS, ST, BGN, INS, etc.)
2. Required elements within segments
3. SSN format validation (9 digits)
4. NY X318 specific requirements

## Configuration

The validation rules can be customized by modifying:

- `src/main/resources/smooks-config.xml`: Contains validation rules
- `src/main/resources/edi-mapping.xml`: Defines EDI structure mapping

## Testing

Run the test suite:
```bash
mvn test
```

## License

This project is licensed under the MIT License - see the LICENSE file for details. 