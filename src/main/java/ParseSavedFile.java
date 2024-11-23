
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ParseSavedFile extends EdgeConvertFileParser {
    public static final String SAVE_ID = "EdgeConvert Save File"; //first line of save files should be this

    public ParseSavedFile(File file) {
        super(file);
    }

    @Override
    public void parseFile() throws IOException, ParseException {

        if (parseFile.length() == 0) {
            throw new IllegalArgumentException("File is empty.");
        }

        if (!parseFile.getName().endsWith(".sav")) {
            throw new IllegalArgumentException("Invalid file extension. Only .sav files are allowed.");
        }

        String currentLine;
        EdgeTable tempTable;
        EdgeField tempField;

        // Read tables
        while ((currentLine = br.readLine()) != null) {
            currentLine = currentLine.trim();
            if (currentLine.startsWith("Table: ")) {
                tempTable = parseTable(br, currentLine);
                alTables.add(tempTable);
            } else {
                break; // Exit table parsing loop, move to fields
            }
        }

        // Read fields
        while (currentLine != null && !currentLine.isEmpty()) {
            tempField = parseField(currentLine);
            alFields.add(tempField);
            currentLine = br.readLine();
        }
    }

    private EdgeTable parseTable(BufferedReader br, String currentLine) throws IOException {
        int numFigure = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1)); // Get the table number
        String tableName;
        List<Integer> nativeFields = new ArrayList<>();
        List<Integer> relatedTables = new ArrayList<>();
        List<Integer> relatedFields = new ArrayList<>();

        br.readLine(); // Skip the opening '{'
        currentLine = br.readLine(); // TableName line
        tableName = currentLine.substring(currentLine.indexOf(" ") + 1);

        currentLine = br.readLine(); // NativeFields line
        StringTokenizer stNatFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
        while (stNatFields.hasMoreTokens()) {
            nativeFields.add(Integer.valueOf(stNatFields.nextToken()));
        }

        currentLine = br.readLine(); // RelatedTables line
        StringTokenizer stTables = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
        while (stTables.hasMoreTokens()) {
            relatedTables.add(Integer.valueOf(stTables.nextToken()));
        }

        currentLine = br.readLine(); // RelatedFields line
        StringTokenizer stRelFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
        while (stRelFields.hasMoreTokens()) {
            relatedFields.add(Integer.valueOf(stRelFields.nextToken()));
        }

        br.readLine(); // Skip the closing '}'

        // Create the EdgeTable object
        EdgeTable tempTable = new EdgeTable(numFigure + DELIM + tableName);
        for (int field : nativeFields) {
            tempTable.addNativeField(field);
        }
        for (int table : relatedTables) {
            tempTable.addRelatedTable(table);
        }
        tempTable.makeArrays();
        for (int i = 0; i < relatedFields.size(); i++) {
            tempTable.setRelatedField(i, relatedFields.get(i));
        }

        return tempTable;
    }

    private EdgeField parseField(String currentLine) {
        StringTokenizer stField = new StringTokenizer(currentLine, DELIM);
        int numFigure = Integer.parseInt(stField.nextToken());
        String fieldName = stField.nextToken();

        EdgeField tempField = new EdgeField(numFigure + DELIM + fieldName);
        tempField.setTableID(Integer.parseInt(stField.nextToken()));
        tempField.setTableBound(Integer.parseInt(stField.nextToken()));
        tempField.setFieldBound(Integer.parseInt(stField.nextToken()));
        tempField.setDataType(Integer.parseInt(stField.nextToken()));
        tempField.setVarcharValue(Integer.parseInt(stField.nextToken()));
        tempField.setIsPrimaryKey(Boolean.parseBoolean(stField.nextToken()));
        tempField.setDisallowNull(Boolean.parseBoolean(stField.nextToken()));

        if (stField.hasMoreTokens()) {
            tempField.setDefaultValue(stField.nextToken());
        }

        return tempField;
    }

}
