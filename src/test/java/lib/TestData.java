package lib;


import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Recordset;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestData {	
	/*----------------------------------------------------------------------------
	Function Name    	: readTestData
	Description     	: Reads the TestData from database table and stores all the TC data in objMap
	Input Parameters 	: id - TC id
	                    : strExcelName - test data excel file name
	                    : strSheetName - excel sheet name
	Return Value    	: objMap
	Author		        : 
	Date of creation	:
	Date of modification:	
	----------------------------------------------------------------------------*/
    public Object readTestData(String id, String strExcelName, String strSheetName) throws IOException {    	   
    	String strTDQuery = "Select * from "+ strSheetName +" where TestID='"+ id +"'";    	  	
    	Map<String, Object> objMap = new HashMap<String, Object> ();    	
    	objMap.clear();
		try	{
			Recordset rs = InitScript.getRecordSetUsingFillo(Global.gstrTestDataDir + "TestData_" + strExcelName + ".xlsx",strTDQuery);
			if(rs!= null && rs.getCount() > 0 && rs.getFieldNames().size() > 0) {
				while(rs.next()) {
					for (int nLoopCount = 1; nLoopCount <= rs.getFieldNames().size()-1; nLoopCount++) {
						String strKeyName = rs.getField(nLoopCount).name();	
						String strValName = rs.getField(nLoopCount).value();	
						if(strKeyName!= null && !strKeyName.trim().isEmpty() && strValName!= null && !strValName.trim().isEmpty()) {
							objMap.put(strKeyName, strValName);
						}
					}					
				}
			}
		} catch (FilloException e) {
			System.out.println("Fillo Exception occurred during reading regression data file" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception occurred during reading regression data file" + e.getMessage());
		}		
		return objMap;
    }
}
