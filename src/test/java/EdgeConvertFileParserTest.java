import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

public class EdgeConvertFileParserTest {
    private ParseEdgeFile parser;

    @Before
    public void setUp() {
        parser = new ParseEdgeFile(new File("src/test/java/EdgeConvertFileParser_files/basic_valid.edg"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyFile() throws Exception {
        parser = new ParseEdgeFile(new File("src/test/java/EdgeConvertFileParser_files/empty_file.edg"));
        parser.parseFile();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testWrongExtension() throws Exception{
        parser = new ParseEdgeFile(new File("src/test/java/EdgeConvertFileParser_files/wrong_extension.txt"));
        parser.parseFile();
    }
    
    @Test(expected = ParseException.class)
    public void testMissingConnector() throws Exception {
        parser = new ParseEdgeFile(new File("src/test/java/EdgeConvertFileParser_files/missing_connector.edg"));
        parser.parseFile();
    }
    
    @Test(expected = ParseException.class)
    public void testInvalidStyle() throws Exception {
        parser = new ParseEdgeFile(new File("src/test/java/EdgeConvertFileParser_files/invalid_style.edg"));
        parser.parseFile();
    }
    
    @Test(expected = ParseException.class)
    public void testDuplicateFiguresFile() throws Exception{
        parser = new ParseEdgeFile(new File("src/test/java/EdgeConvertFileParser_files/duplicate_figures.edg"));
        parser.parseFile();
    }

    @Test
    public void testOnlyAttributesNoEntities() throws IOException, ParseException, IllegalArgumentException{
        parser = new ParseEdgeFile(new File("src/test/java/EdgeConvertFileParser_files/attributes_only.edg"));
        parser.parseFile();
        assertFalse("Should contain entities", parser.getTables().length > 0);
    }

    @Test
    public void testOnlyEntitiesNoAttributes() throws IOException, ParseException{
        parser = new ParseEdgeFile(new File("src/test/java/EdgeConvertFileParser_files/entities_only.edg"));
        parser.parseFile();
        assertFalse("File should contain entities", parser.getFields().length > 0);
    }

    @Test(expected = ParseException.class)
    public void testNoRelationshipsPresent() throws Exception {
        parser = new ParseEdgeFile(new File("src/test/java/EdgeConvertFileParser_files/relationships.edg"));
        parser.parseFile();
    }

    @Test(expected = ParseException.class)
    public void testNoEntitiesNorAttributes() throws Exception{
        parser = new ParseEdgeFile(new File("src/test/java/EdgeConvertFileParser_files/noEntities_noAttributes.edg"));
        parser.parseFile();
    }

    @Test(expected = ParseException.class)
    public void testEntityWithBlankName() throws Exception{
        parser = new ParseEdgeFile(new File("src/test/java/EdgeConvertFileParser_files/blankEntityName.edg"));
        parser.parseFile();
    }

    @Test(expected = ParseException.class)
    public void testAttributeWithBlankName() throws Exception{
        parser = new ParseEdgeFile(new File("src/test/java/EdgeConvertFileParser_files/blankAttributeName.edg"));
        parser.parseFile();
    }

}
