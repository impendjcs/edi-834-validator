# Contributing to EDI 834 Validation Framework

Thank you for your interest in contributing to the EDI 834 Validation Framework! This document provides guidelines and information for contributors.

## 🚀 Getting Started

1. **Fork the repository** on GitHub
2. **Clone your fork** locally:
   ```bash
   git clone https://github.com/yourusername/edi-834-validator.git
   cd edi-834-validator
   ```
3. **Add the upstream remote**:
   ```bash
   git remote add upstream https://github.com/impendjcs/edi-834-validator.git
   ```

## 🔧 Development Setup

1. **Ensure you have the prerequisites**:
   - Java 11 or higher
   - Maven 3.6 or higher

2. **Build the project**:
   ```bash
   mvn clean install
   ```

3. **Run tests** to ensure everything works:
   ```bash
   mvn test
   ```

## 📝 Making Changes

1. **Create a feature branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Make your changes** following the coding standards below

3. **Test your changes**:
   ```bash
   mvn test
   ```

4. **Commit your changes** with a descriptive message:
   ```bash
   git commit -m "feat: Add new validation for XYZ segment"
   ```

5. **Push to your fork**:
   ```bash
   git push origin feature/your-feature-name
   ```

6. **Create a Pull Request** on GitHub

## 📋 Coding Standards

### Java Code Style
- Follow standard Java naming conventions
- Use meaningful variable and method names
- Add comprehensive comments for complex logic
- Keep methods focused and single-purpose
- Use appropriate access modifiers

### Architecture Guidelines
- **New Segments**: Create both a model class and validator class
- **Validation Logic**: Implement in the appropriate validator class
- **Factory Registration**: Register new validators in `EDISegmentValidatorFactory`
- **Error Handling**: Use the `ValidationError` class for all validation errors

### Example: Adding a New Segment

1. **Create the segment model**:
   ```java
   public class NewSegment extends EDISegment {
       public static final String SEGMENT_CODE = "NEW";
       
       public NewSegment(String line, int lineNumber) {
           super(SEGMENT_CODE, line, lineNumber);
       }
       
       // Add getter methods for specific fields
       public String getField1() {
           return getField(1);
       }
   }
   ```

2. **Create the validator**:
   ```java
   public class NewSegmentValidator implements EDISegmentValidator {
       @Override
       public List<ValidationError> validate(EDISegment segment) {
           List<ValidationError> errors = new ArrayList<>();
           
           if (!(segment instanceof NewSegment)) {
               errors.add(new ValidationError(NewSegment.SEGMENT_CODE, "Segment Type", 
                   "Invalid segment type", segment.getLineNumber()));
               return errors;
           }
           
           // Add your validation logic here
           
           return errors;
       }
       
       @Override
       public String getSegmentCode() {
           return NewSegment.SEGMENT_CODE;
       }
   }
   ```

3. **Register in the factory**:
   ```java
   // In EDISegmentValidatorFactory static block
   validators.put("NEW", new NewSegmentValidator());
   ```

## 🧪 Testing

### Running Tests
```bash
mvn test
```

### Adding New Tests
- Create test methods in `EDI834ValidatorTest.java`
- Test both valid and invalid scenarios
- Ensure test coverage for new functionality
- Use descriptive test method names

### Test Data
- Add sample EDI files to `src/test/resources/`
- Include both valid and invalid examples
- Document the purpose of each test file

## 📚 Documentation

### Code Documentation
- Add JavaDoc comments for public methods
- Include examples in comments where helpful
- Document complex validation logic

### README Updates
- Update the README.md if you add new features
- Include usage examples for new functionality
- Update the project structure if needed

## 🔍 Pull Request Guidelines

### Before Submitting
1. **Ensure all tests pass**:
   ```bash
   mvn clean test
   ```

2. **Check code style**:
   - No compilation warnings
   - Consistent formatting
   - Proper imports

3. **Update documentation**:
   - README.md if needed
   - Code comments
   - Test documentation

### Pull Request Description
Include:
- **Summary**: Brief description of changes
- **Motivation**: Why this change is needed
- **Changes**: Detailed list of modifications
- **Testing**: How you tested the changes
- **Screenshots**: If UI changes are involved

## 🐛 Bug Reports

When reporting bugs, please include:
- **Description**: Clear description of the issue
- **Steps to reproduce**: Detailed steps to recreate the bug
- **Expected behavior**: What should happen
- **Actual behavior**: What actually happens
- **Environment**: Java version, OS, etc.
- **Sample data**: EDI file that causes the issue (if applicable)

## 💡 Feature Requests

When requesting features, please include:
- **Description**: Clear description of the feature
- **Use case**: Why this feature is needed
- **Proposed implementation**: How you think it should work
- **Examples**: Sample usage or data

## 📞 Getting Help

If you need help:
1. Check the existing documentation
2. Look at existing issues and pull requests
3. Create a new issue with your question
4. Join discussions in existing issues

## 🎉 Recognition

Contributors will be recognized in:
- The project README
- Release notes
- GitHub contributors list

Thank you for contributing to the EDI 834 Validation Framework! 