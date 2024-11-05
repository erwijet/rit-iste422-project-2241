import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EdgeTableTest {
    @Test
    public void initTest() { // testing EdgeTable constructor
        EdgeTable edgeTable = new EdgeTable("1|Test");
        assertEquals(1, edgeTable.getNumFigure());
        assertEquals("Test", edgeTable.getName());
    }

    @Test(expected = NullPointerException.class)
    public void nullInitTest() { // testing constructor with null value
        new EdgeTable(null);
    }

    @Test
    public void addRelatedTableTest() { // testing addRelatedTables and makeArrays methods
        EdgeTable edgeTable = new EdgeTable("1|Test");
        edgeTable.addRelatedTable(100);
        edgeTable.addRelatedTable(200);
        edgeTable.addRelatedTable(300);
        edgeTable.addRelatedTable(400);
        edgeTable.makeArrays();

        int[] relatedTables = edgeTable.getRelatedTablesArray();
        assertEquals(4, relatedTables.length);
        assertEquals(100, relatedTables[0]);
        assertEquals(200, relatedTables[1]);
        assertEquals(300, relatedTables[2]);
        assertEquals(400, relatedTables[3]);
    }

    @Test
    public void addNativeFieldTest() { // testing addNativeField and makeArrays methods
        EdgeTable edgeTable = new EdgeTable("1|Test");
        edgeTable.addNativeField(100);
        edgeTable.addNativeField(200);
        edgeTable.addNativeField(300);
        edgeTable.addNativeField(400);
        edgeTable.makeArrays();
        
        int[] nativeFields = edgeTable.getNativeFieldsArray();
        assertEquals(4, nativeFields.length);
        assertEquals(100, nativeFields[0]);
        assertEquals(200, nativeFields[1]);
        assertEquals(300, nativeFields[2]);
        assertEquals(400, nativeFields[3]);
    }

    @Test 
    public void emptyArrayTest() { // testing makeArrays when empty
        EdgeTable edgeTable = new EdgeTable("1|Test");
        edgeTable.makeArrays();

        assertEquals(0, edgeTable.getNativeFieldsArray().length);
        assertEquals(0, edgeTable.getRelatedTablesArray().length);
        assertEquals(0, edgeTable.getRelatedFieldsArray().length);
    }

    @Test 
    public void moveFieldUpTest() { // testing moveFieldUp method
        EdgeTable edgeTable = new EdgeTable("1|Test");
        edgeTable.addNativeField(300);
        edgeTable.addNativeField(400);
        edgeTable.makeArrays();
        edgeTable.setRelatedField(0, 500);
        edgeTable.setRelatedField(1, 600);
        
        edgeTable.moveFieldUp(1);
        
        assertEquals(400, edgeTable.getNativeFieldsArray()[0]);
        assertEquals(300, edgeTable.getNativeFieldsArray()[1]);
        assertEquals(600, edgeTable.getRelatedFieldsArray()[0]);
        assertEquals(500, edgeTable.getRelatedFieldsArray()[1]);
    }

    @Test 
    public void moveFieldDownTest() { // testing moveFieldDown method
        EdgeTable edgeTable = new EdgeTable("1|Test");
        edgeTable.addNativeField(300);
        edgeTable.addNativeField(400);
        edgeTable.makeArrays();
        edgeTable.setRelatedField(0, 500);
        edgeTable.setRelatedField(1, 600);
        
        edgeTable.moveFieldDown(0);
        
        assertEquals(400, edgeTable.getNativeFieldsArray()[0]);
        assertEquals(300, edgeTable.getNativeFieldsArray()[1]);
        assertEquals(600, edgeTable.getRelatedFieldsArray()[0]);
        assertEquals(500, edgeTable.getRelatedFieldsArray()[1]);
    }

    @Test 
    public void moveFieldBoundryTest() { // making sure that moving first element up and last element down have no effect
        EdgeTable edgeTable = new EdgeTable("1|Test");
        edgeTable.addNativeField(100);
        edgeTable.addNativeField(200);
        edgeTable.addNativeField(300);
        edgeTable.makeArrays();
        edgeTable.setRelatedField(0, 1);
        edgeTable.setRelatedField(1, 2);
        edgeTable.setRelatedField(2, 3);

        edgeTable.moveFieldUp(0);
        assertEquals(100, edgeTable.getNativeFieldsArray()[0]);
        assertEquals(1, edgeTable.getRelatedFieldsArray()[0]);

        edgeTable.moveFieldDown(2);
        assertEquals(300, edgeTable.getNativeFieldsArray()[2]);
        assertEquals(3, edgeTable.getRelatedFieldsArray()[2]);
    }

    @Test
    public void toStringTest() { // testing toString 
        EdgeTable edgeTable = new EdgeTable("1|Test");
        edgeTable.addNativeField(300);
        edgeTable.addNativeField(400);
        edgeTable.addRelatedTable(100);
        edgeTable.makeArrays();
        
        String expectedOutput = "Table: 1\r\n{\r\nTableName: Test\r\nNativeFields: 300|400\r\nRelatedTables: 100\r\nRelatedFields: 0|0\r\n}\r\n";
        assertEquals(expectedOutput, edgeTable.toString());
    } 
}
