package lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ReadWriteExcelFile {
	
	public static String actFile;
	//public static String actFile1;
	public static Map < String, Object[] > resultOverWrite = new TreeMap < String, Object[] >();
	public static Map < String, Object[] > result = new TreeMap < String, Object[] >();
	
	public static Map < String, Object[] > resultMasterDriver = new TreeMap < String, Object[] >();
	
    public Workbook readExcel(String filePath,String fileName) throws IOException {
        //Create a object of File class to open xlsx file
        File file =	new File(filePath+"\\"+fileName);

        //Create an object of FileInputStream class to read excel file
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = null;

        //Find the file extension by spliting file name in substing and getting only extension name
        String fileExtensionName = fileName.substring(fileName.indexOf("."));

        //Check condition if the file is xlsx file
        if(fileExtensionName.equals(".xlsx")) {
            //If it is xlsx file then create object of XSSFWorkbook class
            workbook = new XSSFWorkbook(inputStream);
        }

        //Check condition if the file is xls file
        else if(fileExtensionName.equals(".xls")){
            //If it is xls file then create object of XSSFWorkbook class
            workbook = new HSSFWorkbook(inputStream);
        }

        //Read sheet inside the workbook by its name
        //Sheet  sheet = workbook.getSheet(sheetName);
        // return sheet;
        return workbook;
    }
    
    /*----------------------------------------------------------------------------
    Function Name    	: writeSummaryExcelOverWrite
    Description     	: This will create new summary file and write the file.
    ----------------------------------------------------------------------------*/
    
    
    public static void writeSummaryExcelOverWrite() throws Exception {
		Fillo fillo = new Fillo();
		Connection con = null;
		Recordset rs = null;
		try {
			try {
				
				actFile= Global.gstrACTResultExcelOverWriteFilePath;
			    	 
				// FileOutputStream out = new FileOutputStream( 
			    	//	 new File(actFile));
				
				con = fillo.getConnection(actFile);
				
			} catch (FilloException e) {
				System.out.println("Exception in MasterDriver Connection in getRecordSetUsingFillo using Fillo is: " + e.getMessage());				
				String strDesc = "Exception in MasterDriver Connection in getRecordSetUsingFillo using Fillo is: " + e.getMessage();
				Utility.writeHTMLResultLog(strDesc, "fail");							
				Global.bResult = "False";
				Global.objErr = "11";
			}
			try {
				
				Set < String > keyidM = resultOverWrite.keySet();
				for (String key : keyidM) {
			         
			         Object [] objectArr1 = resultOverWrite.get(key);
			         for (Object obj : objectArr1){
			            
			        	 con.executeUpdate((String)obj);
			            
			         }
			      }
				
			} catch (FilloException e) {
				System.out.println("Exception in RecordSet in getRecordSetUsingFillo using Fillo is: " + e.getMessage());
				String strDesc = "Exception in RecordSet in getRecordSetUsingFillo using Fillo is: " + e.getMessage();
				Utility.writeHTMLResultLog(strDesc, "fail");							
				Global.bResult = "False";
				Global.objErr = "11";
			}
		} finally {
			if (null != con) {
				con.close();
			}
		}
		//deleteTempFile(excelFilePath);
		//return rs;
	} 
    
    /*----------------------------------------------------------------------------
    Function Name    	: writeExcel
    Description     	: This will write in the summary result file.
    ----------------------------------------------------------------------------*/
    
    
    public static void writeExcel() throws IOException {
        //Create a object of File class to open xlsx file
        
    	//Create blank workbook
	      XSSFWorkbook workbook = new XSSFWorkbook();
	      
	      //Create a blank sheet
	      Sheet spreadsheet = workbook.createSheet("Summary");

	      //Create row object
	      Row row;

	      //This data needs to be written (Object[])
	      //Map < String, Object[] > result = new TreeMap < String, Object[] >();	    
	      //Iterate over data and write to sheet
	      Set < String > keyid = result.keySet();
	      int rowid = 0;
	      
	      for (String key : keyid) {
	         row = spreadsheet.createRow(rowid++);
	         Object [] objectArr = result.get(key);
	         int cellid = 0;
	         
	         for (Object obj : objectArr){
	            Cell cell = row.createCell(cellid++);
	            cell.setCellValue((String)obj);
	         }
	      }
	      //Write the workbook in file system
	      actFile= Global.gstrACTResultExcelFilePath+"AIO Test Automation Results Summary"+Global.gstrTimesTamp+".xlsx";
	      FileOutputStream out = new FileOutputStream( 
	    		 new File(actFile));
	      
	      workbook.write(out);
	      out.close();
	      System.out.println("Writesheet.xlsx written successfully");
    } 
    
    
    /*----------------------------------------------------------------------------
    Function Name    	: writeExcel
    Description     	: This will write in the summary result file.
    ----------------------------------------------------------------------------*/
    
    
    public static void writeMasterDriverExcel() throws Exception {
		Fillo fillo = new Fillo();
		Connection con = null;
		Recordset rs = null;
		try {
			try {
				con = fillo.getConnection(Global.gstrControlFilesDir + "AIO_MasterDriver.xlsx");
			} catch (FilloException e) {
				System.out.println("Exception in MasterDriver Connection in getRecordSetUsingFillo using Fillo is: " + e.getMessage());				
				String strDesc = "Exception in MasterDriver Connection in getRecordSetUsingFillo using Fillo is: " + e.getMessage();
				Utility.writeHTMLResultLog(strDesc, "fail");							
				Global.bResult = "False";
				Global.objErr = "11";
			}
			try {
				
				Set < String > keyidM = resultMasterDriver.keySet();
				for (String key : keyidM) {
			         
			         Object [] objectArr1 = resultMasterDriver.get(key);
			         for (Object obj : objectArr1){
			            
			        	 con.executeUpdate((String)obj);
			            
			         }
			      }
				
				
				
				
			} catch (FilloException e) {
				System.out.println("Exception in RecordSet in getRecordSetUsingFillo using Fillo is: " + e.getMessage());
				String strDesc = "Exception in RecordSet in getRecordSetUsingFillo using Fillo is: " + e.getMessage();
				Utility.writeHTMLResultLog(strDesc, "fail");							
				Global.bResult = "False";
				Global.objErr = "11";
			}
		} finally {
			if (null != con) {
				con.close();
			}
		}
		//deleteTempFile(excelFilePath);
		//return rs;
	} 
}
