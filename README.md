
# EdgeConvertFileParserTest

This project tests the `EdgeConvertFileParser` class, which is responsible for parsing `.edg` files and extracting entities, attributes, and relationships from them. The test suite verifies that `EdgeConvertFileParser` handles various scenarios, such as invalid file formats, missing connectors, empty files, and more.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- JUnit 4 for unit testing

### Running the Tests

Run the tests using JUnit in your preferred IDE, or with the following command if using the command line:
```bash
gradle test
```
Replace `XX` with your version of JUnit.

## Tests Overview

### `setUp()`

The `setUp` method initializes the `EdgeConvertFileParser` with a valid `.edg` file before each test, providing a base for the tests to work with.

---

### Individual Test Cases

#### 1. **`testEmptyFile`**
   - **Description**: Verifies that an empty `.edg` file throws an `IllegalArgumentException`.
   - **Expected Outcome**: The parser should identify the file as empty and throw an exception.

#### 2. **`testWrongExtension`**
   - **Description**: Checks if a file with an invalid extension (e.g., `.txt`) throws an `IllegalArgumentException`.
   - **Expected Outcome**: The parser should throw an exception, as it expects only `.edg` files.

#### 3. **`testMissingConnector`**
   - **Description**: Ensures that files missing required connectors throw a `ParseException`.
   - **Expected Outcome**: The parser should recognize the missing connectors and throw an exception.

#### 4. **`testInvalidStyle`**
   - **Description**: Validates that files with an invalid style configuration throw a `ParseException`.
   - **Expected Outcome**: The parser should detect the invalid style and throw an exception.

#### 5. **`testDuplicateFiguresFile`**
   - **Description**: Checks for handling of duplicate figure entries in a file.
   - **Expected Outcome**: The parser should throw a `ParseException` if it encounters duplicate figures.

#### 6. **`testOnlyAttributesNoEntities`**
   - **Description**: Tests a file that contains only attributes but no entities.
   - **Expected Outcome**: The parser should successfully parse and ensure entities are extracted correctly.

#### 7. **`testOnlyEntitiesNoAttributes`**
   - **Description**: Verifies handling of a file with only entities and no attributes.
   - **Expected Outcome**: The parser should parse the file and ensure attributes are extracted correctly.

#### 8. **`testNoRelationshipsPresent`**
   - **Description**: Ensures a `ParseException` is thrown when no relationships are present in the file.
   - **Expected Outcome**: The parser should throw an exception when relationships are absent.

#### 9. **`testNoEntitiesNorAttributes`**
   - **Description**: Tests a file that lacks both entities and attributes.
   - **Expected Outcome**: The parser should throw a `ParseException` due to missing data.

#### 10. **`testEntityWithBlankName`**
   - **Description**: Checks for entities with blank names and verifies they throw a `ParseException`.
   - **Expected Outcome**: The parser should throw an exception for entities with invalid names.

#### 11. **`testAttributeWithBlankName`**
   - **Description**: Verifies that attributes with blank names throw a `ParseException`.
   - **Expected Outcome**: The parser should throw an exception for attributes with invalid names.

---

