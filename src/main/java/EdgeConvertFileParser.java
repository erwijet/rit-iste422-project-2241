
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class EdgeConvertFileParser {

    protected File parseFile;
    protected FileReader fr;
    protected BufferedReader br;

    // Be specific about types
    protected ArrayList<EdgeTable> alTables;
    protected ArrayList<EdgeField> alFields;
    protected ArrayList<EdgeConnector> alConnectors;

    public static final String DELIM = "|";
    static Logger logger = LogManager.getLogger();

    public EdgeConvertFileParser(File constructorFile) {
        alTables = new ArrayList<>();
        alFields = new ArrayList<>();
        alConnectors = new ArrayList<>();
        parseFile = constructorFile;
        this.openFile(parseFile);

        logger.info("Initialized EdgeConvertFileParser for " + constructorFile);
    }

    public EdgeTable[] getTables() {
        logger.debug("Getting tables");
        return alTables.toArray(EdgeTable[]::new);
    }

    public EdgeField[] getFields() {
        logger.debug("Getting fields");
        return alFields.toArray(EdgeField[]::new);
    }

    public EdgeConnector[] getConnectors() {
        logger.debug("Getting connectors");
        return alConnectors.toArray(EdgeConnector[]::new);
    }

    private void openFile(File inputFile) {
        logger.info("Opening file: " + inputFile);
        try {
            fr = new FileReader(inputFile);
            br = new BufferedReader(fr);
        } // try
        catch (FileNotFoundException fnfe) {
            logger.warn("Cannot find \"" + inputFile.getName() + "\".");
        } // catch FileNotFoundException

    } // openFile()

    public abstract void parseFile() throws IOException, ParseException;
} // EdgeConvertFileHandler

