import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.text.ParseException;
import java.io.IOException;

public class EdgeConvertFileParserTest {
    private EdgeConvertFileParser parser;

    @Before
    public void setUp() {
        parser = new EdgeConvertFileParser(new File("src/test/java/EdgeConvertFileParser_files/basic_valid.edg"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyFile() throws Exception {
        parser = new EdgeConvertFileParser(new File("./EdgeConvertFileParser_files/empty_file.edg"));
        parser.parseEdgeFile();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testWrongExtension() throws Exception{
        parser = new EdgeConvertFileParser(new File("src/test/java/EdgeConvertFileParser_files/wrong_extension.txt"));
        parser.parseEdgeFile();
    }
    
    @Test(expected = ParseException.class)
    public void testMissingConnector() throws Exception {
        parser = new EdgeConvertFileParser(new File("./EdgeConvertFileParser_files/missing_connector.edg"));
        parser.parseEdgeFile();
    }
    
    @Test(expected = ParseException.class)
    public void testInvalidStyle() throws Exception {
        parser = new EdgeConvertFileParser(new File("./EdgeConvertFileParser_files/invalid_style.edg"));
        parser.parseEdgeFile();
    }
    
    @Test(expected = ParseException.class)
    public void testDuplicateFiguresFile() throws Exception{
        parser = new EdgeConvertFileParser(new File("./EdgeConvertFileParser_files/duplicate_figures.edg"));
        parser.parseEdgeFile();
    }

    @Test
    public void testOnlyAttributesNoEntities() throws IOException{
        parser = new EdgeConvertFileParser(new File("./EdgeConvertFileParser_files/attributes_only.edg"));
        parser.parseEdgeFile();
        assertTrue("Should contain entities", parser.getEdgeTables().length > 0);
    }

    @Test
    public void testOnlyEntitiesNoAttributes() throws IOException{
        parser = new EdgeConvertFileParser(new File("./EdgeConvertFileParser_files/entities_only.edg"));
        parser.parseEdgeFile();
        assertTrue("Should contain entities", parser.getEdgeFields().length > 0);
    }

    @Test(expected = ParseException.class)
    public void testNoRelationshipsPresent() throws Exception {
        parser = new EdgeConvertFileParser(new File("./EdgeConvertFileParser_files/relationships.edg"));
        parser.parseEdgeFile();
    }

    @Test(expected = ParseException.class)
    public void testNoEntitiesNorAttributes() throws Exception{
        parser = new EdgeConvertFileParser(new File("./EdgeConvertFileParser_files/noEntities_noAttributes.edg"));
        parser.parseEdgeFile();
    }

    @Test(expected = ParseException.class)
    public void testEntityWithBlankName() throws Exception{
        parser = new EdgeConvertFileParser(new File("./EdgeConvertFileParser_files/blankEntityName.edg"));
        parser.parseEdgeFile();
    }

    @Test(expected = ParseException.class)
    public void testAttributeWithBlankName() throws Exception{
        parser = new EdgeConvertFileParser(new File("./EdgeConvertFileParser_files/blankAttributeName.edg"));
        parser.parseEdgeFile();
    }

}
