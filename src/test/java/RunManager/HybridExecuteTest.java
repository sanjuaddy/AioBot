package RunManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import org.testng.annotations.Test;
import org.apache.commons.lang3.SystemUtils;
import org.testng.annotations.Parameters;
import lib.Global;
import lib.InitDriver;
import lib.Utility;
import lib.SendReportInEmail;
import lib.InitScript;
import lib.ReadWriteExcelFile;
/*----------------------------------------------------------------------------
Function Name    	: HybridExecuteTest - Run Manager
Description     	: This will control the execution.
Author		        : AIO Team

----------------------------------------------------------------------------*/
public class HybridExecuteTest  {  
	static {
		Global.gstrTimesTamp=Utility.getCurrentDatenTime("dd-MMM-yy")+"_"+ Utility.getCurrentDatenTime("H-mm-ss a");
		
		Global.gstrTestResultLogDir="C:\\AutomateIO\\TestRunResults_"+Global.gstrTimesTamp+"\\TestResultLog";
		Global.gstrScreenshotsDir="C:\\AutomateIO\\TestRunResults_"+Global.gstrTimesTamp+"\\Screenshots";
		Global.gstrACTResultExcelFilePath="C:\\AutomateIO\\TestRunResults_"+Global.gstrTimesTamp+File.separator;
	}
	
    @Test
    @Parameters({ "browser" })
    public void runManager(String browser) throws Exception {    	    	 
        try { 
        	
        	Path path = Paths.get(Global.gstrTestResultLogDir);
            if (!Files.exists(path)) {
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                    //fail to create directory
                    e.printStackTrace();
                }
            }    
            Global.gstrScreenshotsDir=Global.gstrScreenshotsDir+"\\Screenshots_"+Utility.getCurrentDatenTime("dd-MMM-yy")+"_"+ Utility.getCurrentDatenTime("H-mm-ss a");
            Path scrnpath = Paths.get(Global.gstrScreenshotsDir);
            if (!Files.exists(scrnpath)) {
                try {
                    Files.createDirectories(scrnpath);
                } catch (IOException e) {
                    //fail to create directory
                    e.printStackTrace();
                }
            } 	
        	
        	
        	InitScript.getConfigurationValuesUsingFillo();
        	browser=Global.gstrBrowser;
        	
        	//write excel related change
        	
        		ReadWriteExcelFile.result.put( "1", new Object[] {
	         "TestAutomationID","TestScriptID","Module","TestTitle","UserName", "BusinessRole","TestResult", "ResultFileName","TestingInstance","PatchVersion","TestMachine","ErrorMessage"});
	    	
        	
        	//Create HTML file
        	Global.gstrTimesTamp = "_"+ Utility.getCurrentDatenTime("dd-MMM-yy")+"_"+ Utility.getCurrentDatenTime("H-mm-ss a"); //H-mm-ss a
        	Global.gstrReportFileName="AIO Test Automation Report" + Global.gstrTimesTamp  +".html";
        	Global.filePath = Global.gstrTestResultLogDir + File.separator + Global.gstrReportFileName;
            File myFile = new File(Global.filePath);
            if (! myFile.exists() ) {
                myFile.createNewFile();
                Global.htmlReports= new ExtentHtmlReporter(Global.filePath);
                Global.report = new ExtentReports();
                
                Global.report.attachReporter(Global.htmlReports);
              //css related changes
                
                
                
               // Global.report.setSystemInfo("Time Zone",tz.getDisplayName() +"&nbsp("+ sdf.format(date) +")&nbsp;&nbsp;&nbsp;&nbsp;" + tz.getID());

                Global.htmlReports.loadXMLConfig(new File(Global.gstrExtentConfigDir + File.separator + "extent-config.xml"));
            }  
            
            
            //Reading email and screenshot flag from master driver
              
            
            //Read Groups
            String strGroupQuery = "Select * from Groups where Run='Y'";
            List<String> arrGroupList = InitScript.readGroupData(Global.gstrControlFilesDir + "AIO_MasterDriver.xlsx",strGroupQuery);            
            for(String strGroupName : arrGroupList) {            	
            	String strGroupSheetQuery = "Select * from "+ strGroupName +" where Run='Y'";            
                List<String> arrTCList = InitScript.readGroupData(Global.gstrControlFilesDir + "AIO_MasterDriver.xlsx",strGroupSheetQuery);
                
                
                
                for(String strTCID : arrTCList) {                 	
                	strGroupSheetQuery = "Select * from "+ strGroupName +" where TestID='"+strTCID+"'";            
                	String strBatchName = InitScript.readGroupSheetData(Global.gstrControlFilesDir + "AIO_MasterDriver.xlsx",strGroupSheetQuery);
                	//Driver initialization
                	
                	System.out.println("Global.gRepsNo="+Global.gRepsNo);
                	for(int i=Global.gRepsNo; i>=1;i--){
                	Utility util = new Utility(browser);
                	Global.test.assignCategory(Global.gTCGroupName);
               
                
                	//Read Batch
                	String strBatchQuery = "Select * from CommonActions where BatchFileName='"+ strBatchName +"'";
                	List<String> arrBatchList = InitScript.readBatchData(Global.gstrBatchFilesDir + "AIO_CommonActions.xlsx",strBatchQuery);   
                	for(String strComponentName : arrBatchList) {
                		Global.gstrComponentName = strComponentName;
                		if (Global.gstrComponentName != "") {
							Global.gstrClassName = Global.gstrComponentName.split("\\.")[0];
							Global.gstrMethodName = Global.gstrComponentName.split("\\.")[1];
							Class<?> cls = Class.forName("pages." + Global.gstrClassName);
							Object clsInstance = (Object) cls.getConstructor(Utility.class).newInstance(util);
							Method method = cls.getMethod(Global.gstrMethodName);	
							method.invoke(clsInstance);		//Call components														
						}                		
                	}
					if(Global.objErr == "11") {												
						Utility.logoutFinally();
						Global.test.log(Status.INFO,"<span style='font-weight:bold;color:#1ff3f5;'>Test Execution Finished</span>");
						//Global.report.endTest(Global.test);
						
						Global.report.flush();																
				        Global.objErr = "0";
						Global.bResult = "True";
						Global.gstrReadfromTestData = true;
						
						if(Global.gSummaryFileNewOld.contains("false"))
						{
							System.out.println("Summary File OverWrite="+Global.gSummaryFileNewOld);
							ReadWriteExcelFile.writeExcel();
						}
						else
						{
							ReadWriteExcelFile.writeSummaryExcelOverWrite();
							
						}
						ReadWriteExcelFile.writeMasterDriverExcel();
						//InitDriver.driver.quit();
						
					} 
					
					
					
					
					else {	
						
						
						Global.test.log(Status.INFO,"<span style='font-weight:bold;color:#6dd8ca;'>Test Execution Finished</span>");
						//Global.report.endTest(Global.test);
						Global.gstrReadfromTestData = true;
						Global.report.flush();
				        Thread.sleep(500);
						
						if(Global.gSummaryFileNewOld.contains("false"))
						{
							
							System.out.println("Summary File OverWrite="+Global.gSummaryFileNewOld);
							ReadWriteExcelFile.writeExcel();
						}
						else
						{
							ReadWriteExcelFile.writeSummaryExcelOverWrite();
						}
						ReadWriteExcelFile.writeMasterDriverExcel();
						
				       /// InitDriver.driver.quit();
				        
				       
					}
                	}
				}																			
			}
				
			 
		} catch (Exception e) {
			String strDesc = "There is an issue in Runmanager. Please check... " + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");
			Utility.takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		} finally {
			  
	        if (Global.gstrSendEmail == true) {
	        	if ((Global.gstrEmailMode).equalsIgnoreCase("gmail")){
	        		SendReportInEmail.sendGmailReport();	        		
	        	}else {
	        		SendReportInEmail.sendOutlookReport();
	        	}	        	
	        }
	        

		}        
    }

}
