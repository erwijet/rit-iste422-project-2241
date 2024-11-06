import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.stream.Stream;

public class EdgeConvertCreateDDLTest {
    EdgeConvertCreateDDL edgeConvertCreateDDL;
    private EdgeTable[] tables;
    private EdgeField[] fields;


    @Before
    public void setup() {
        tables = Stream.of("12|table1", "10|table2")
                .map(EdgeTable::new)
                .peek(EdgeTable::makeArrays)
                .toArray(EdgeTable[]::new);

        fields = Stream.of("15|field1", "17|field2")
                .map(name -> new EdgeField(name)).toArray(EdgeField[]::new);

        edgeConvertCreateDDL = new EdgeConvertCreateDDLTestImpl(tables, fields);
    }

    @Test
    public void testEdgeTableParse() {
        Arrays.stream(tables).forEach(table -> {
            var found = edgeConvertCreateDDL.getTable(table.getNumFigure());
            assertEquals(table.getName(), found.getName());
        });
    }

    @Test
    public void testEdgeFieldParse() {
        Arrays.stream(fields).forEach(field -> {
            var found = edgeConvertCreateDDL.getField(field.getNumFigure());
            assertEquals(field.getName(), found.getName());
        });
    }

    @Test
    public void givenImpl_whenMultipleEdgeFields_lookUpEdgeFieldByNumFigure() {
        Arrays.stream(fields).forEach(field -> {
            EdgeField found = edgeConvertCreateDDL.getField(field.getNumFigure());
            assertEquals("Field numFigure should match", field.getNumFigure(), found.getNumFigure());
        });
    }

    @Test
    public void givenImpl_whenMultipleEdgeTables_lookUpEdgeTableByNumFigure() {
        Arrays.stream(tables).forEach(table -> {
            EdgeTable found = edgeConvertCreateDDL.getTable(table.getNumFigure());
            assertEquals("Table numFigure should match", table.getNumFigure(), found.getNumFigure());
        });
    }

    @Test
    public void givenImpl_whenTableNotFoundByNumFigure_thenReturnNull() {
        EdgeTable found = edgeConvertCreateDDL.getTable(999); // Non-existent numFigure
        assertNull("Expected null when table not found", found);
    }

    @Test
    public void givenImpl_whenFieldNotFoundByNumFigure_thenReturnNull() {
        EdgeField found = edgeConvertCreateDDL.getField(999); // Non-existent numFigure
        assertNull("Expected null when field not found", found);
    }

    @Test
    public void givenImpl_whenInitializeCalled_thenNumBoundTablesAndMaxBoundSetCorrectly() {
        edgeConvertCreateDDL.initialize();

        // Check numBoundTables values after initialization
        assertEquals(2, edgeConvertCreateDDL.tables.length);
        assertEquals(2, edgeConvertCreateDDL.fields.length);

        assertEquals(2, edgeConvertCreateDDL.numBoundTables.length);
    }

    @Test(expected = NullPointerException.class)
    public void givenNoArgImpl_whenInitializeCalled_thenNPE() {
        edgeConvertCreateDDL = new EdgeConvertCreateDDLTestImpl();
        edgeConvertCreateDDL.initialize();
    }

    @Test(expected = Exception.class)
    public void givenImpl_whenInitializeCalledWithNull_thenExceptionIsThrown() {
        edgeConvertCreateDDL = new EdgeConvertCreateDDLTestImpl(null, null);
    }

    private static class EdgeConvertCreateDDLTestImpl extends EdgeConvertCreateDDL {
        public EdgeConvertCreateDDLTestImpl(EdgeTable[] tables, EdgeField[] fields) {
            super(tables, fields);
        }

        public EdgeConvertCreateDDLTestImpl() {
            super();
        }

        @Override
        public String getDatabaseName() {
            return "TestDatabase";
        }

        @Override
        public String getProductName() {
            return "TestProduct";
        }

        @Override
        public String getSQLString() {
            return "SELECT * FROM Test";
        }

        @Override
        public void createDDL() {
        }
    }
}
