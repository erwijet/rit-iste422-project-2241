import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.StringTokenizer;

public class EdgeField {
   private int numFigure, tableID, tableBound, fieldBound, dataType, varcharValue;
   private String name, defaultValue;
   private boolean disallowNull, isPrimaryKey;
   private static String[] strDataType = {"Varchar", "Boolean", "Integer", "Double"};
   public static final int VARCHAR_DEFAULT_LENGTH = 1;

   static Logger logger = LogManager.getLogger();
   public EdgeField(String inputString) {
      StringTokenizer st = new StringTokenizer(inputString, EdgeConvertFileParser.DELIM);
      numFigure = Integer.parseInt(st.nextToken());
      name = st.nextToken();
      tableID = 0;
      tableBound = 0;
      fieldBound = 0;
      disallowNull = false;
      isPrimaryKey = false;
      defaultValue = "";
      varcharValue = VARCHAR_DEFAULT_LENGTH;
      dataType = 0;

      logger.info("Initialized EdgeField from '" + inputString + "'");
   }
   
   public int getNumFigure() {
      logger.debug("Getting numFigure");
      return numFigure;
   }
   
   public String getName() {
      logger.debug("Getting name");
      return name;
   }
   
   public int getTableID() {
      logger.debug("Getting tableID");
      return tableID;
   }

   public void setTableID(int value) {
      logger.debug("Setting tableID to " + value);
      tableID = value;
   }
   
   public int getTableBound() {
      logger.debug("Getting tableBound");
      return tableBound;
   }
   
   public void setTableBound(int value) {
      logger.debug("Setting tableBound to " + value);
      tableBound = value;
   }

   public int getFieldBound() {
      logger.debug("Getting fieldBound");
      return fieldBound;
   }
   
   public void setFieldBound(int value) {
      logger.debug("Setting fieldBound to " + value);
      fieldBound = value;
   }

   public boolean getDisallowNull() {
      logger.debug("Getting disallowNull");
      return disallowNull;
   }
   
   public void setDisallowNull(boolean value) {
      logger.debug("Setting disallowNull to " + value);
      disallowNull = value;
   }
   
   public boolean getIsPrimaryKey() {
      logger.debug("Getting isPrimaryKey");
      return isPrimaryKey;
   }
   
   public void setIsPrimaryKey(boolean value) {
      logger.debug("Setting isPrimaryKey to " + value);
      isPrimaryKey = value;
   }
   
   public String getDefaultValue() {
      logger.debug("Getting defaultValue");
      return defaultValue;
   }
   
   public void setDefaultValue(String value) {
      logger.debug("Setting defaultValue to " + value);
      defaultValue = value;
   }
   
   public int getVarcharValue() {
      logger.debug("Getting varcharValue");
      return varcharValue;
   }
   
   public void setVarcharValue(int value) {
      logger.debug("Setting varcharValue to " + value);

      if (value > 0) {
         varcharValue = value;
      }
   }
   public int getDataType() {
      logger.debug("Getting dataType");
      return dataType;
   }
   
   public void setDataType(int value) {
      logger.debug("Setting dataType to " + value);
      if (value >= 0 && value < strDataType.length) {
         dataType = value;
      }
   }
   
   public static String[] getStrDataType() {
      logger.debug("Getting strDataType");
      return strDataType;
   }
   
   public String toString() {
      return numFigure + EdgeConvertFileParser.DELIM +
      name + EdgeConvertFileParser.DELIM +
      tableID + EdgeConvertFileParser.DELIM +
      tableBound + EdgeConvertFileParser.DELIM +
      fieldBound + EdgeConvertFileParser.DELIM +
      dataType + EdgeConvertFileParser.DELIM +
      varcharValue + EdgeConvertFileParser.DELIM +
      isPrimaryKey + EdgeConvertFileParser.DELIM +
      disallowNull + EdgeConvertFileParser.DELIM +
      defaultValue;
   }
}
