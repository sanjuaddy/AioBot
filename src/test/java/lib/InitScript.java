package lib;

import java.util.ArrayList;

import java.util.List;

import org.testng.annotations.BeforeClass;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Field;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class InitScript {	
	/*----------------------------------------------------------------------------
	Function Name    	: readGroupData
	Description     	: Reads the Group Name from database table Groups and stores all the group file name in arrGroupList array
	Input Parameters 	: excelSheetFilePath - excel Sheet File Path
	                    : query - SQL Query
	Return Value    	: arrGroupList
	Author		        : 
	Date of creation	:
	Date of modification:	
	----------------------------------------------------------------------------*/
	public  static List<String> readGroupData(String excelSheetFilePath, String query ) throws Exception {
		List<String> arrGroupList = new ArrayList<String>();		
		try	{
			Recordset rs = getRecordSetUsingFillo(excelSheetFilePath,query);
			if(rs!= null && rs.getCount() > 0) {
				while(rs.next()) {
					String strGroupName = rs.getField(0).value();								
					if(strGroupName!= null && !strGroupName.trim().isEmpty()) {
						arrGroupList.add(strGroupName);
					}
				}
			}
		} catch (FilloException e) {
			System.out.println("Fillo Exception occurred during reading GroupData file" + e.getMessage());
			String strDesc = "Fillo Exception occurred during reading GroupData file" + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");							
			Global.bResult = "False";
			Global.objErr = "11";
		} catch (Exception e) {
			System.out.println("Exception occurred during reading GroupData file" + e.getMessage());
			String strDesc = "Exception occurred during reading GroupData file" + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");							
			Global.bResult = "False";
			Global.objErr = "11";
		}		
		return arrGroupList;
	}

	/*----------------------------------------------------------------------------
	Function Name    	: readGroupSheetData
	Description     	: Reads the Group sheet name from database table Groups and stores all the group file name in Global var and return teh Batch Name
	Input Parameters 	: excelSheetFilePath - excel Sheet File Path
	                    : query - SQL Query
	Return Value    	: strBatchName
	Author		        : 
	Date of creation	:
	Date of modification:	
	----------------------------------------------------------------------------*/
	public  static String readGroupSheetData(String excelSheetFilePath, String query ) throws Exception {		
		String strBatchName=null;
		try	{
			Recordset rs = getRecordSetUsingFillo(excelSheetFilePath,query);
			if(rs!= null && rs.getCount() > 0) {
				while(rs.next()) {

					Global.gTCID = rs.getField("TestID");
					Global.gTCName = rs.getField("Test Title");
					Global.gTCDescription = rs.getField("Test Description");
					Global.gTCGroupName = rs.getField("Module");
					Global.gTCClientTestId = rs.getField("Client TestID");
					Global.gReps = rs.getField("Reps");
                    Global.gRepsNo=Integer.parseInt(Global.gReps);
					strBatchName= rs.getField("Batch File Name");
					if((Global.gTCID!= null && !Global.gTCID.trim().isEmpty())&&(Global.gTCName!= null && !Global.gTCName.trim().isEmpty())&&(Global.gTCDescription!= null && !Global.gTCDescription.trim().isEmpty())&&(strBatchName!= null && !strBatchName.trim().isEmpty()) && (Global.gTCGroupName!= null && !Global.gTCGroupName.trim().isEmpty())) {

					}
					else {	
						Exception e = null;
						throw new Exception("Mandatory Field value null in GroupSheetData: " + Global.gTCID, e);
					}
				}
			}
		} catch (FilloException e) {
			System.out.println("Fillo Exception occurred during reading GroupSheetData file: " + e.getMessage());
			String strDesc = "Fillo Exception occurred during reading GroupSheetData file: " + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");							
			Global.bResult = "False";
			Global.objErr = "11";
		} catch (Exception e) {
			System.out.println("Exception occurred during reading GroupSheetData file : " + e.getMessage());
			String strDesc = "Exception occurred during reading GroupSheetData file : " + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");							
			Global.bResult = "False";
			Global.objErr = "11";
		}		

		return strBatchName;
	}

	/*----------------------------------------------------------------------------
	Function Name    	: readBatchData
	Description     	: Reads the batch Name from database table batch and stores all the batch data in arrBatchList array
	Input Parameters 	: excelSheetFilePath - excel Sheet File Path
	                    : query - SQL Query
	Return Value    	: arrBatchList
	Author		        : 
	Date of creation	:
	Date of modification:	
	----------------------------------------------------------------------------*/
	public  static List<String> readBatchData(String excelSheetFilePath, String query ) throws Exception {
		List<String> arrBatchList = new ArrayList<String>();		
		try	{
			Recordset rs = getRecordSetUsingFillo(excelSheetFilePath,query);
			if(rs!= null && rs.getCount() > 0 && rs.getFieldNames().size() > 0) {
				while(rs.next()) {
					for (int nLoopCount = 2; nLoopCount <= rs.getFieldNames().size()-1; nLoopCount++) {
						String strActionName = rs.getField(nLoopCount).value();	
						if(strActionName!= null && !strActionName.trim().isEmpty()) {
							arrBatchList.add(strActionName);
						}
					}					
				}
			}
		} catch (FilloException e) {
			System.out.println("Fillo Exception occurred during reading BatchSheet file" + e.getMessage());
			String strDesc = "Fillo Exception occurred during reading BatchSheet file" + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");							
			Global.bResult = "False";
			Global.objErr = "11";
		} catch (Exception e) {
			System.out.println("Exception occurred during reading test BatchSheet file" + e.getMessage());
			String strDesc = "Exception occurred during reading test BatchSheet file" + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");							
			Global.bResult = "False";
			Global.objErr = "11";
		}		
		return arrBatchList;
	}

	/*----------------------------------------------------------------------------
	Function Name    	: getRecordSetUsingFillo
	Description     	: Make the Fillo connection and return the recordset
	Input Parameters 	: excelSheetFilePath - excel Sheet File Path
	                    : query - SQL Query
	Return Value    	: rs
	Author		        : 
	Date of creation	:
	Date of modification:	
	----------------------------------------------------------------------------*/
	public static Recordset getRecordSetUsingFillo(String excelFilePath, String query) throws Exception {
		Fillo fillo = new Fillo();
		Connection con = null;
		Recordset rs = null;
		try {
			try {
				con = fillo.getConnection(excelFilePath);
			} catch (FilloException e) {
				System.out.println("Exception in Connection in getRecordSetUsingFillo using Fillo is: " + e.getMessage());				
				String strDesc = "Exception in Connection in getRecordSetUsingFillo using Fillo is: " + e.getMessage();
				Utility.writeHTMLResultLog(strDesc, "fail");							
				Global.bResult = "False";
				Global.objErr = "11";
			}
			try {
				
				rs = con.executeQuery(query);
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
		return rs;
	}

	/*----------------------------------------------------------------------------
	Function Name    	: getConfigurationValuesUsingFillo
	Description     	: Make the Fillo connection and return the recordset
	Input Parameters 	: excelSheetFilePath - excel Sheet File Path
	                    : query - SQL Query
	Return Value    	: rs
	Author		        : 
	Date of creation	:
	Date of modification:	
	----------------------------------------------------------------------------*/
	public static void getConfigurationValuesUsingFillo() throws Exception {
		Fillo fillo = new Fillo();
		Connection con = null;
		Recordset rs = null;
		try {
			try {
				con = fillo.getConnection(Global.gstrControlFilesDir + "AIO_MasterDriver.xlsx");
				String strQuery="Select * from Configuration";
				rs = con.executeQuery(strQuery);
				while(rs.next()){

					Global.gstrEmailUserName = rs.getField("Sender");	
					// Sender's email ID needs to be mentioned
					Global.gstrEmailPassword = rs.getField("Password");		// Sender's password needs to be mentioned
					Global.gstrRecipient = rs.getField("ResultsRecipients");
					Global.gstrBrowser=rs.getField("Browser");
					Global.gSummaryFileNewOld=rs.getField("Summary File Overwrite");
					String value = rs.getField("EnableEmail"); 
					Global.gstrSendEmail=Boolean.parseBoolean(value.toLowerCase());
					String value1 = rs.getField("EnableScreenshots"); //
					Global.gstrScreenShotFlag = Boolean.parseBoolean(value1.toLowerCase());
					Global.gURL=rs.getField("URL");
					Global.gPatchVersion=rs.getField("Patch Version");
					Global.gstrACTResultExcelOverWriteFilePath=rs.getField("Summary File PathName");
					
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


	}


}
