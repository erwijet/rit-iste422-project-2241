
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class ParseEdgeFile extends EdgeConvertFileParser {

    public static final String EDGE_ID = "EDGE Diagram File"; //first line of .edg files should be this

    public ParseEdgeFile(File file) {
        super(file);
    }

    private String readBlock(BufferedReader br, String startingLine) throws IOException {
        StringBuilder block = new StringBuilder();
        block.append(startingLine).append(System.lineSeparator());

        String line;
        while ((line = br.readLine()) != null) {
            block.append(line.trim()).append(System.lineSeparator());
            if (line.trim().equals("}")) { // End of block
                break;
            }
        }

        return block.toString();
    }

    @Override
    public void parseFile() throws ParseException, IOException, IllegalArgumentException {
        if (parseFile.length() == 0) {
            throw new IllegalArgumentException("File is empty.");
        }

        if (!parseFile.getName().endsWith(".edg")) {
            throw new IllegalArgumentException("Invalid file extension. Only .edg files are allowed.");
        }

        String line;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("Figure") || line.startsWith("Connector")) {
                String block = readBlock(br, line); // Read the block based on the starting line
                if (line.startsWith("Figure")) {
                    parseFigure(block);
                } else if (line.startsWith("Connector")) {
                    parseConnector(block);
                }
            } else if (line.startsWith("Relation")) {
                throw new ParseException("File should not contain relations", 0);
            }
        }

        // Validate presence of entities or attributes
        if (alTables.isEmpty() && alFields.isEmpty()) {
            throw new ParseException("No entities or attributes found in the file", -1);
        }

    }

    private void parseFigure(String block) throws ParseException {
        String[] lines = block.split(System.lineSeparator());
        String figureId = lines[0].split(" ")[1]; // Extract Figure ID
        String style = null;
        String text = null;

        for (String line : lines) {
            line = line.trim();

            if (line.startsWith("Style")) {
                style = extractQuotedString(line, "Style");
            } else if (line.startsWith("Text")) {
                text = extractQuotedString(line, "Text");
                // Validation for blank names
    if (text.trim().isEmpty()) {
        throw new ParseException("Entity or Attribute name cannot be blank for Figure ID: " + figureId, -1);
    }
            }
        }

        if (style == null || text == null) {
            throw new ParseException("Incomplete figure block for Figure ID: " + figureId, -1);
        }

        

        if (style.equalsIgnoreCase("Entity")) {
            if (isDup(figureId)) {
                logger.warn("Duplicate table detected for Figure ID: " + figureId);
                throw new ParseException("Duplicate Figure ID: " + figureId, -1);
            }
            alTables.add(new EdgeTable(figureId + DELIM + style + DELIM + text)); // Add as Entity
        } else if (style.equalsIgnoreCase("Attribute")) {
            alFields.add(new EdgeField(figureId + DELIM + style + DELIM + text)); // Add as Attribute
        }

    }

    private void parseConnector(String block) throws ParseException {
        int numConnector = -1;
        int endPoint1 = -1;
        int endPoint2 = -1;
        String endStyle1 = null;
        String endStyle2 = null;

        String[] lines = block.split(System.lineSeparator());

        //  Get Connector ID
        try {
            numConnector = Integer.parseInt(lines[0].substring(lines[0].indexOf(" ") + 1).trim());
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid Connector number", 0);
        }

        if (!lines[1].trim().equals("{")) {
            throw new ParseException("Expected '{' after Connector declaration", 0);
        }

        // Parse Figure1, Figure2, End1, and End2
        for (int i = 2; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.equals("}")) {
                break; // End of the connector block
            }
            if (line.startsWith("Figure1")) {
                endPoint1 = parseFigureNumber(line, "Figure1");
            } else if (line.startsWith("Figure2")) {
                endPoint2 = parseFigureNumber(line, "Figure2");
            } else if (line.startsWith("End1")) {
                endStyle1 = parseEndStyle(line, "End1");
            } else if (line.startsWith("End2")) {
                endStyle2 = parseEndStyle(line, "End2");
            }
        }

        // Validate parsed data
        if (endPoint1 == -1 || endPoint2 == -1 || endStyle1 == null || endStyle2 == null) {
            throw new ParseException("Incomplete Connector entry: missing required fields", 0);
        }

        alConnectors.add(new EdgeConnector(numConnector + DELIM + endPoint1 + DELIM + endPoint2 + DELIM + endStyle1 + DELIM + endStyle2));
    }

    private int parseFigureNumber(String line, String label) throws ParseException {
        try {
            return Integer.parseInt(line.substring(line.indexOf(" ") + 1).trim());
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid figure number in " + label, 0);
        }
    }

    private String parseEndStyle(String line, String label) throws ParseException {
        int start = line.indexOf("\"") + 1;
        int end = line.lastIndexOf("\"");
        if (start == 0 || end == -1 || start >= end) {
            throw new ParseException("Invalid end style in " + label, 0);
        }
        return line.substring(start, end);
    }

    private boolean isDup(String figureId) {
        for (EdgeTable table : alTables) {
            if (table.getNumFigure() == Integer.parseInt(figureId)) {
                return true;
            }
        }
        return false;
    }

    // Utility method to extract the string between quotes
private String extractQuotedString(String line, String field) throws ParseException {
    int start = line.indexOf("\"");
    int end = line.lastIndexOf("\"");

    // Check if quotes exist and if start/end indices are valid
    if (start == -1 || end == -1 || start >= end) {
        throw new ParseException("Invalid " + field + " field: Missing or malformed quotes.", 0);
    }

    return line.substring(start + 1, end);
}
}
