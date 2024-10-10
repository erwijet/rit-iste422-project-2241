import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class EdgeTable {
   private int numFigure;
   private String name;
   private ArrayList alRelatedTables, alNativeFields;
   private int[] relatedTables, relatedFields, nativeFields;

   static Logger logger = LogManager.getLogger();
   
   public EdgeTable(String inputString) {
      StringTokenizer st = new StringTokenizer(inputString, EdgeConvertFileParser.DELIM);
      numFigure = Integer.parseInt(st.nextToken());
      name = st.nextToken();
      alRelatedTables = new ArrayList();
      alNativeFields = new ArrayList();

      logger.info("Initializing");
   }
   
   public int getNumFigure() {
      logger.debug("Getting numFigure");
      return numFigure;
   }
   
   public String getName() {
      logger.debug("Getting name");
      return name;
   }
   
   public void addRelatedTable(int relatedTable) {
      logger.debug("Adding " + relatedTable + " to related tables");
      alRelatedTables.add(new Integer(relatedTable));
   }
   
   public int[] getRelatedTablesArray() {
      logger.debug("Getting relatedTables");
      return relatedTables;
   }
   
   public int[] getRelatedFieldsArray() {
      logger.debug("Getting relatedFields");
      return relatedFields;
   }
   
   public void setRelatedField(int index, int relatedValue) {
      logger.debug("Setting relatedField @" + index + " to " + relatedValue);
      relatedFields[index] = relatedValue;
   }
   
   public int[] getNativeFieldsArray() {
      logger.debug("Getting nativeFields");
      return nativeFields;
   }

   public void addNativeField(int value) {
      logger.debug("Adding " + value + " to nativeFields");
      alNativeFields.add(new Integer(value));
   }

   public void moveFieldUp(int index) { //move the field closer to the beginning of the list
      logger.debug("Moving field @" + index + " up");
      if (index == 0) {
         return;
      }
      int tempNative = nativeFields[index - 1]; //save element at destination index
      nativeFields[index - 1] = nativeFields[index]; //copy target element to destination
      nativeFields[index] = tempNative; //copy saved element to target's original location
      int tempRelated = relatedFields[index - 1]; //save element at destination index
      relatedFields[index - 1] = relatedFields[index]; //copy target element to destination
      relatedFields[index] = tempRelated; //copy saved element to target's original location
   }
   
   public void moveFieldDown(int index) { //move the field closer to the end of the list
      logger.info("Moving field @" + index + " down");

      if (index == (nativeFields.length - 1)) {
         return;
      }
      int tempNative = nativeFields[index + 1]; //save element at destination index
      nativeFields[index + 1] = nativeFields[index]; //copy target element to destination
      nativeFields[index] = tempNative; //copy saved element to target's original location
      int tempRelated = relatedFields[index + 1]; //save element at destination index
      relatedFields[index + 1] = relatedFields[index]; //copy target element to destination
      relatedFields[index] = tempRelated; //copy saved element to target's original location
   }

   public void makeArrays() { //convert the ArrayLists into int[]
      logger.debug("Making arrays...");
      Integer[] temp;
      temp = (Integer[])alNativeFields.toArray(new Integer[alNativeFields.size()]);
      nativeFields = new int[temp.length];
      for (int i = 0; i < temp.length; i++) {
         nativeFields[i] = temp[i].intValue();
      }
      
      temp = (Integer[])alRelatedTables.toArray(new Integer[alRelatedTables.size()]);
      relatedTables = new int[temp.length];
      for (int i = 0; i < temp.length; i++) {
         relatedTables[i] = temp[i].intValue();
      }
      
      relatedFields = new int[nativeFields.length];
      for (int i = 0; i < relatedFields.length; i++) {
         relatedFields[i] = 0;
      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("Table: " + numFigure + "\r\n");
      sb.append("{\r\n");
      sb.append("TableName: " + name + "\r\n");
      sb.append("NativeFields: ");
      for (int i = 0; i < nativeFields.length; i++) {
         sb.append(nativeFields[i]);
         if (i < (nativeFields.length - 1)){
            sb.append(EdgeConvertFileParser.DELIM);
         }
      }
      sb.append("\r\nRelatedTables: ");
      for (int i = 0; i < relatedTables.length; i++) {
         sb.append(relatedTables[i]);
         if (i < (relatedTables.length - 1)){
            sb.append(EdgeConvertFileParser.DELIM);
         }
      }
      sb.append("\r\nRelatedFields: ");
      for (int i = 0; i < relatedFields.length; i++) {
         sb.append(relatedFields[i]);
         if (i < (relatedFields.length - 1)){
            sb.append(EdgeConvertFileParser.DELIM);
         }
      }
      sb.append("\r\n}\r\n");
      
      return sb.toString();
   }
}
