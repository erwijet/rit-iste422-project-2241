# EdgeConvertFileParser Unit Tests

This document outlines the unit tests for the `EdgeConvertFileParser` class.

## Tests Overview

1. **testEmptyFile**
   - **File**: `empty_file.edg`
   - **Description**: Tests the parser's ability to handle an empty file.
   - **Expected Outcome**: A `ParseException` is thrown due to the absence of content.

2. **testWrongExtension**
   - **File**: `wrong_extension.txt`
   - **Description**: Verifies the parser's response to a file with an incorrect extension.
   - **Expected Outcome**: An `IllegalArgumentException` is thrown for unsupported file types.

3. **testMissingConnector**
   - **File**: `missing_connector.edg`
   - **Description**: Tests the handling of files missing necessary connectors between entities.
   - **Expected Outcome**: A `ParseException` is thrown due to the lack of connectors.

4. **testInvalidStyle**
   - **File**: `invalid_style.edg`
   - **Description**: Ensures that the parser detects invalid figure styles.
   - **Expected Outcome**: A `ParseException` is thrown for invalid styles.

5. **testDuplicateFiguresFile**
   - **File**: `duplicate_figures.edg`
   - **Description**: Tests for the presence of duplicate figures in the file.
   - **Expected Outcome**: A `ParseException` is thrown due to duplicate figures.

6. **testOnlyAttributesNoEntities**
   - **File**: `attributes_only.edg`
   - **Description**: Verifies that files with attributes but no entities are parsed correctly.
   - **Expected Outcome**: The parser successfully handles the file, with entities present.

7. **testOnlyEntitiesNoAttributes**
   - **File**: `entities_only.edg`
   - **Description**: Tests the parser's ability to process files with entities but no attributes.
   - **Expected Outcome**: The parser successfully parses the file, indicating the presence of attributes.

8. **testNoRelationshipsPresent**
   - **File**: `relationships.edg`
   - **Description**: Checks files that should contain no relationships.
   - **Expected Outcome**: The parser confirms that no relationships are present.

9. **testNoEntitiesNorAttributes**
   - **File**: `noEntities_noAttributes.edg`
   - **Description**: Tests files devoid of both entities and attributes.
   - **Expected Outcome**: The parser confirms the absence of entities and attributes.

10. **testEntityWithBlankName**
    - **File**: `blankEntityName.edg`
    - **Description**: Tests how the parser handles entities with blank names.
    - **Expected Outcome**: A `ParseException` is thrown for blank entity names.

11. **testAttributeWithBlankName**
    - **File**: `blankAttributeName.edg`
    - **Description**: Checks for handling of attributes with blank names.
    - **Expected Outcome**: A `ParseException` is thrown for blank attribute names.

12. **testDuplicatedEntities**
    - **File**: `duplicatedEntities.edg`
    - **Description**: Tests the parser's ability to handle duplicated entities.
    - **Expected Outcome**: The number of entities matches the count of unique entities.

13. **testNoConnectors**
    - **File**: `noConnectors.edg`
    - **Description**: Verifies behavior when there are no connectors in the file.
    - **Expected Outcome**: The parser should indicate the presence of connectors.

14. **testOnlyOneEndpoint**
    - **File**: `singleEndpoint.edg`
    - **Description**: Tests the parser's response to a connector with a single endpoint.
    - **Expected Outcome**: A `ParseException` is thrown for invalid connector definitions.

15. **testAttributesAsEndpoints**
    - **File**: `attributesAsEndpoints.edg`
    - **Description**: Verifies that attributes cannot be used as endpoints for relationships.
    - **Expected Outcome**: A `ParseException` is thrown for invalid endpoint usage.

16. **testTablesAsEndpoints**
    - **File**: `tablesAsEndpoints.edg`
    - **Description**: Tests that entities can correctly serve as endpoints for relationships.
    - **Expected Outcome**: Relationships are created successfully.

17. **testFieldBelongsToMultipleTables**
    - **File**: `multiTableField.edg`
    - **Description**: Verifies that fields belonging to multiple tables are correctly identified.
    - **Expected Outcome**: The parser identifies fields associated with multiple tables.

## Usage

To execute these tests, ensure that the specified test files are available in the `EdgeConvertFileParser_files` directory. Use your preferred testing framework (e.g., JUnit) to run the tests and verify the parser's functionality against these scenarios.
