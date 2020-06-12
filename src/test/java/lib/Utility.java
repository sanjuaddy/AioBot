/*============================================================================================
Library File Name    :  Utility
Author               :  
Created date         :  
Description          :  It lists the common utility functions that can be used in the scripts.
============================================================================================*/

package lib;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfSearchElement;
import com.gnostice.pdfone.PdfSearchMode;
import com.gnostice.pdfone.PdfSearchOptions;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.RemoteWebDriver;

// TODO: Auto-generated Javadoc
/**
 * The Class Utility.
 */
public class Utility  {

	/** The driver. */
	public static WebDriver driver;

	/** The cc. */
	static Calendar cc = null;

	/** The home path. */
	public static String homePath = "";

	/** The wait. */
	public static WebDriverWait wait;

	/** The js. */
	public static JavascriptExecutor js;
	public static String parentWindow;
	public static String child_window;

	/**
	 * Instantiates a new utility.
	 *
	 * @param browser the browser
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Utility(String browser ) throws IOException {
		InitDriver initdriver =InitDriver.getInstance(browser);

		this.driver = initdriver.driver;
		wait = new WebDriverWait(driver, 40);
		this.js = (JavascriptExecutor) this.driver;
	}

	/**
	 * Ng return driver.
	 *
	 * @return the web driver
	 * @throws Exception the exception
	 */
	public static WebDriver ng_returnDriver() throws Exception {
		return driver;
	}

	/**
	 * Ng invoke browser.
	 *
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
	Function Name    	: invokeBrowser
	Description     	: This function invokes the application in Browser
	Input Parameters 	: strKey - Paramiter name to get the data value from TestData Table
	                    : objData - Test Data
	Return Value    	: None 
	Author		        : 
	Date of creation	:
	Date of modification:	
	----------------------------------------------------------------------------*/
	public static String ng_invokeBrowser(String strKey) throws Exception {
		String strVal = Global.gURL;           //getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}				
		try {			
			driver.get(strVal);

			if(Global.count==1)
			{

				Global.report.setSystemInfo("Apps", "Automate IO");
				Global.report.setSystemInfo("Version", "R1");	
				Global.gTCInstance=strVal.substring(8, 17);       
				Global.report.setSystemInfo("Instance", Global.gTCInstance);
				Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
				String browserName = cap.getBrowserName().toLowerCase();
				String v = cap.getVersion().toString();
				System.out.println(v);
				String browser=browserName+" "+v;
				Global.report.setSystemInfo("Browser", browser);

				java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
				Global.gTCHostName=localMachine.getHostName();
				String OS = SystemUtils.OS_NAME;
				Global.report.setSystemInfo("OS", OS);
				Global.report.setSystemInfo("Host Name", Global.gTCHostName);

				TimeZone tz = Calendar.getInstance().getTimeZone(); 
				Date date = new Date(); 
				SimpleDateFormat sdf = new SimpleDateFormat("zzz");  
				Global.report.setSystemInfo("Time Zone",tz.getDisplayName() +"&nbsp("+ sdf.format(date) +")&nbsp;&nbsp;&nbsp;&nbsp;");
			}
			Global.count++;
			//waitForPageToLoad();
			String strDesc = "Browser  '" + strVal + "'  Invoked Successfully";
			writeHTMLResultLog(strDesc, "pass");
			Global.bResult = "True";
		} catch (Throwable error) {
			String strDesc = "<span style='color:#ff4d4d;'>Timeout waiting for Page Load Request to complete</span>";
			ng_ResultLogFail(strDesc);
		} 
		return Global.bResult;

	}

	/**
	 * Ng verify page.
	 *
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
	Function Name    	: verifyPage
	Description     	: This function enters a data into a text box
	Input Parameters 	: strLabel - To be printed on extent report
	                    : strKey - Paramiter name to get the data value from TestData Table                        
	Return Value    	: bResult
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_verifyPage(String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}		
		try {
			waitForPageToLoad();			
			String strDesc = "Page '" + strLabel + "' is displayed successfully";
			ng_ResultLogPass(strDesc);
		} catch (Throwable e) {
			String strDesc = "<span style='color:#ff4d4d;'>Page " + strLabel + " is not displayed properly. Error Message : " + e.getMessage()+"</span>";
			ng_ResultLogFail(strDesc);
		} 
		return Global.bResult;
	}

	/**
	 * Ng enter text.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
	Function Name    	: enterText
	Description     	: This function enters a data into a text box
	Input Parameters 	: strObject - Object Name of Edit Box
						: strLabel - To be printed on extent report
	                    : strKey - Paramiter name to get the data value from TestData Table                        
	Return Value    	: bResult
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_enterText(WebElement element, String strLabel, String strKey) throws Exception {

		String strVal = getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}	
		try {
			waitForPageToLoad();
			waitForSpinner();
			ng_waitUntilElementVisible(element,60);			
			//ng_waitUntilElementDisplayed(element,20); //mahesh
			ng_scrollIntoViewElement(element, strLabel);						
			element.click();
			if(StringUtils.isNotEmpty(ng_getTextBoxValue(element, strLabel))) {
				clearTextField(element);
				//ng_waitImplicitly(1);		mahesh    	
				//waitForPageToLoad();
			}	
			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}	

			if (strVal.equalsIgnoreCase("RANDOM")) {
				strVal =""+Utility.ng_RandomNum();
			}
			if (strVal.equalsIgnoreCase("DATE")) {
				strVal = Utility.getCurrentDatenTime("MM-dd-yy");
			}
			if (strVal.equalsIgnoreCase("DATEMONYEAR")) {
				strVal = Utility.getCurrentDatenTime("dd-MM-yyyy");
			}
			element.sendKeys(strVal);
			//waitForPageToLoad();
			String strDesc = "Successfully entered <span style='color:#c3ac39 !important;'>'" + strVal + "'</span> in <span style='font-weight:bold;'>'" + strLabel + "'</span> textbox";    
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			//char specialChar=')';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)=='}' ||exception.charAt(i)==')')
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			//String strDesc = "<span style='color:#ff4d4d;'>WebElement '" + strLabel +  "' is not displayed on the screen." + "</span>"; //"<span style='color:#ff4d4d;'>WebElement " + strLabel +  "' is not displayed on the screen. Error Message : " + e.getMessage()+"</span>"
			ng_ResultLogFail(strDesc);
		}
		return Global.bResult;
	}

	/*----------------------------------------------------------------------------
	Function Name    	: enterText
	Description     	: This function enters a data into a text box
	Input Parameters 	: strObject - Object Name of Edit Box
						: strLabel - To be printed on extent report
	                    : strKey - Paramiter name to get the data value from TestData Table                        
	Return Value    	: bResult
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_enterDate(WebElement element, String strLabel, String strKey) throws Exception {

		String strVal = getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}	
		try {
			waitForPageToLoad();
			waitForSpinner();
			ng_waitUntilElementVisible(element,60);			
			//ng_waitUntilElementDisplayed(element,20); //mahesh
			ng_scrollIntoViewElement(element, strLabel);						
			element.click();
			if(StringUtils.isNotEmpty(ng_getTextBoxValue(element, strLabel))) {
				clearTextField(element);
				ng_waitImplicitly(1);		  	
				//waitForPageToLoad();
			}	
			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}	

			strVal = Utility.getCurrentDatenTime(strVal);
			element.sendKeys(strVal);
			//waitForPageToLoad();
			String strDesc = "Successfully entered <span style='color:#c3ac39 !important;'>'" + strVal + "'</span> in <span style='font-weight:bold;'>'" + strLabel + "'</span> textbox";    
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			//char specialChar=')';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)=='}' ||exception.charAt(i)==')')
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			//String strDesc = "<span style='color:#ff4d4d;'>WebElement '" + strLabel +  "' is not displayed on the screen." + "</span>"; //"<span style='color:#ff4d4d;'>WebElement " + strLabel +  "' is not displayed on the screen. Error Message : " + e.getMessage()+"</span>"
			ng_ResultLogFail(strDesc);
		}
		return Global.bResult;
	}



	/**
	 * Gets the test data value.
	 *
	 * @param strKey the str key
	 * @return the test data value
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
	Function Name    	: getTestDataValue
	Description     	: This function get the TestDataValue
	Input Parameters 	: strKey - Paramiter name to get the data value from TestData Table 
	                    :                        
	Return Value    	: strCellData
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String getTestDataValue(String strKey) throws Exception {
		String strCellData;
		if (Global.gstrReadfromTestData){
			strCellData = (String) Global.objData.get(strKey.toUpperCase());
			if (strCellData!= null) {
				return strCellData;
			}else {
				String strDesc = "<span style='color:#ff4d4d;'>DataValue '" + strKey +  "' not found in Test Data </span>" ;
				ng_ResultLogFail(strDesc);
				//Global.report.endTest(Global.test);
				Global.report.flush();
			}
		}
		else{
			strCellData =  strKey;
		}		
		return strCellData;
	}

	/**
	 * Ng select list.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
	Function Name    	: ng_SelectList
	Description     	: 
	Input Parameters 	: strObject - Object Name of Edit Box
						: strLabel - To be printed on extent report
	                    : strKey - Paramiter name to get the data value from TestData Table                        
	Return Value    	: bResult
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_SelectList(WebElement element,String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);	
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}	
		try {
			//waitForPageToLoad();			
			//explicitWait(element, 20);
			//ng_waitUntilElementDisplayed(element,20);
			//ng_scrollIntoViewElement(element, strLabel);			
			//ng_waitForElementEnabled(element,20);
			//			if(StringUtils.isNotEmpty(ng_getTextBoxValue(element, strLabel))) {
			//				clearTextField(element);
			//		    	ng_waitImplicitly(1);		    	
			//		    	//waitForPageToLoad();
			//			}			
			//element.click();
			//waitForPageToLoad();
			waitForSpinner();
			ng_enterText(element, strLabel, strKey);			
			ng_waitImplicitly(1);  
			element.sendKeys(Keys.ENTER);
			waitForPageToLoad();
			ng_waitImplicitly(2);
			//String triangleloc="//label[text()='"+strLabel+"']/parent::td/following::td//a[contains(@title,'Search: "+strLabel+"')]";

			//String triangleloc = "//a[contains(@title,'Search: "+strLabel+"')]";

			WebElement triangleloc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@title,'Search: "+strLabel+"')]")));			
			//WebElement trianglebox = driver.findElement(By.xpath(triangleloc));			
			triangleloc.click();
			waitForPageToLoad();
			ng_waitImplicitly(1); //mahesh

			//WebElement dropdownselect = driver.findElement(By.xpath("//span[text()='"+strVal+"']"));
			WebElement dropdownselect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='"+strVal+"']")));
			dropdownselect.click();
			waitForPageToLoad();	
			String strDesc = "Value '" + strVal + "' is selected successfully from '" + strLabel + "' list";
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			char specialChar='}';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)==specialChar)
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			ng_ResultLogFail(strDesc);
		} 	
		return Global.bResult;
	}

	/**
	 * Ng drop down.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
	Function Name    	: ng_DropDown
	Description     	: 
	Input Parameters 	: strObject - Object Name of Edit Box
						: strLabel - To be printed on extent report
	                    : strKey - Paramiter name to get the data value from TestData Table                        
	Return Value    	: bResult
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_DropDown(WebElement element,String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);			
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}	
		try {
			waitForPageToLoad();
			waitForSpinner();
			explicitWait(element, 1);
			ng_waitUntilElementDisplayed(element,20);
			ng_scrollIntoViewElement(element, strLabel);			
			ng_waitForElementEnabled(element,5);

			Select objSelect = new Select(element);
			objSelect.selectByVisibleText(strVal);			
			String strDesc = "Value <span style='color:#f7d420 !important;'>'" + strVal + "'</span> is selected successfully from <span style='font-weight:bold;'>'" + strLabel + "'</span> list";
			ng_ResultLogPass(strDesc);
		} catch (Exception e) {
			char specialChar='}';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)==specialChar)
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			ng_ResultLogFail(strDesc);
		} 	
		return Global.bResult;
	}

	/**
	 * Ng select listtable.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
	Function Name    	: ng_SelectListtable
	Description     	: 
	Input Parameters 	: strObject - Object Name of Edit Box
						: strLabel - To be printed on extent report
	                    : strKey - Paramiter name to get the data value from TestData Table                        
	Return Value    	: bResult
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_SelectListtable(WebElement element,String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);		
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}	
		try {
			waitForPageToLoad();	
			waitForSpinner();

			if(StringUtils.isNotEmpty(ng_getTextBoxValue(element, strLabel))) {
				clearTextField(element);
				ng_waitImplicitly(2);		    	
				waitForPageToLoad();

			}					
			ng_enterText(element, strLabel, strKey);
			ng_waitImplicitly(2);
			element.sendKeys(Keys.ENTER);
			ng_waitImplicitly(5);

			String triangleloc="//label[text()='"+strLabel+"']/preceding::td/following::td//a[contains(@title,'Search: "+strLabel+"')][1]";
			//span[text()='Distribution Set']//following::a[contains(@title,'Search: Distribution Set')]

			WebElement trianglebox=driver.findElement(By.xpath(triangleloc));

			trianglebox.click();
			ng_waitImplicitly(3);
			WebElement dropdownselect=driver.findElement(By.xpath("//div[contains(@id,'dropdownPopup::dropDownContent')]//span[text()='"+strVal+"']"));
			dropdownselect.click();

			String strDesc = "Value '" + strVal + "' is selected successfully from '" + strLabel + "' list";
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			char specialChar='}';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)==specialChar)
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			ng_ResultLogFail(strDesc);
		} 	
		return Global.bResult;
	}

	/**
	 * Ng click web element.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
	Function Name    	: clickWebElement
	Description     	: This function clicks the WebElement object
	Input Parameters 	: strObject - Object Name of Web Element
						: strLabel - To be printed on extent report
	                    : strKey - Paramiter name to get the data value from TestData Table
	Return Value    	: bResult
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_clickWebElement(WebElement element, String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {		
			//explicitWait(element,20);

			//ng_waitUntilElementDisplayed(element,20);
			ng_scrollIntoViewElement(element, strLabel);			
			ng_waitForElementEnabled(element,20);

			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}
			String onClickScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('click',true, false);arguments[0].dispatchEvent(evObj);} else if(document.createEventObject){ arguments[0].fireEvent('onclick');}";
			if (element.getAttribute("onclick")!=null) {
				element.click();
			}			
			else {
				js.executeScript(onClickScript,element);
			}	
			//waitForPageToLoad();
			String strDesc = "Successfully clicked on '" + strLabel + "'  WebElement";			
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			//char specialChar='}';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)=='}' || exception.charAt(i)==')')
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			ng_ResultLogFail(strDesc);
		} 								
		return Global.bResult;
	}

	/**
	 * Write HTML result log.
	 *
	 * @param strDescription the str description
	 * @param strPassFail the str pass fail
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
	Function Name       :writeHTMLResultLog
	Description         :This function will create the test Log File
	Input Parameter    	:strDescription - Description to be printed
	                    :intPassFail
	Return Value        :None
	Author		        :
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static void writeHTMLResultLog(String strDescription, String strPassFail) throws Exception {
		if (strPassFail == "pass") {
			Global.test.log(Status.PASS, strDescription);



			String strQuerys="Update Summary Set TestScriptID='"+Global.gTCClientTestId+"',Module='"+Global.gTCGroupName+"',TestTitle='"+Global.gTCName+"',UserName='"+Global.gTCUserName+"',BusinessRole='"+Global.gTCUserBuRole+"',TestResult='Pass',ResultFileName='"+Global.gstrReportFileName+"',TestingInstance='"+Global.gTCInstance +"',PatchVersion='"+Global.gPatchVersion+"',TestMachine='"+ Global.gTCHostName +"'where TestAutomationID='"+Global.gTCID+"'";
			ReadWriteExcelFile.resultOverWrite.put( Global.gTCID, new Object[] {strQuerys});

			ReadWriteExcelFile.result.put( Global.gTCID, new Object[] {
					Global.gTCID,Global.gTCClientTestId,Global.gTCGroupName,Global.gTCName,Global.gTCUserName,Global.gTCUserBuRole, "Pass", Global.gstrReportFileName,Global.gTCInstance,Global.gPatchVersion,Global.gTCHostName});


			String strQuery="Update TestScriptsALL Set Result='Pass' where TestID='"+Global.gTCID+"'";
			ReadWriteExcelFile.resultMasterDriver.put( Global.gTCID, new Object[] {strQuery});



		} else if (strPassFail == "fail") {
			Global.test.log(Status.FAIL, strDescription);

			strDescription=strDescription.replaceAll("\\<.*?>","");
			strDescription=strDescription.replaceAll("&nbsp","");


			String strQuerys="Update Summary Set TestScriptID='"+Global.gTCClientTestId+"',Module='"+Global.gTCGroupName+"',TestTitle='"+Global.gTCName+"',UserName='"+Global.gTCUserName+"',BusinessRole='"+Global.gTCUserBuRole+"',TestResult='Fail',ResultFileName='"+Global.gstrReportFileName+"',TestingInstance='"+Global.gTCInstance +"',PatchVersion='"+Global.gPatchVersion+"',TestMachine='"+ Global.gTCHostName +"'where TestAutomationID='"+Global.gTCID+"'";
			ReadWriteExcelFile.resultOverWrite.put( Global.gTCID, new Object[] {strQuerys});

			ReadWriteExcelFile.result.put( Global.gTCID, new Object[] {
					Global.gTCID,Global.gTCClientTestId,Global.gTCGroupName,Global.gTCName,Global.gTCUserName,Global.gTCUserBuRole, "Fail", Global.gstrReportFileName,Global.gTCInstance,Global.gPatchVersion,Global.gTCHostName,strDescription,});

			String strQuery="Update TestScriptsALL Set Result='Fail' where TestID='"+Global.gTCID+"'";
			ReadWriteExcelFile.resultMasterDriver.put( Global.gTCID, new Object[] {strQuery});

		} else if (strPassFail == "info") {
			Global.test.log(Status.INFO, strDescription);
		}
	}

	/**
	 * Take screen shot and log.
	 *
	 * @param strPassFail the str pass fail
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
	Function Name       :TakeScreenShot
	Description         :This function takes the screen shot of the application
	Input Parameter    	:strDescription - Description to be printed
	Return Value        :None
	Author		        :
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static void takeScreenShotAndLog(String strPassFail) throws Exception {
		if(Global.gstrScreenShotFlag == true) {

			if (strPassFail == "pass") {
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				String tImage = "Run_" + Utility.getCurrentDatenTime("dd-MM-yy") + "_"
						+ Utility.getCurrentDatenTime("H-mm-ss a");
				File destination= new File(Global.gstrScreenshotsDir + "\\" + Global.gTCName + "\\img_" + tImage + ".jpg");	
				FileUtils.copyFile(scrFile,destination);
				MediaEntityModelProvider mediaModel = MediaEntityBuilder.createScreenCaptureFromPath(Global.gstrScreenshotsDir + "\\" + Global.gTCName + "\\img_" + tImage + ".jpg").build();
				Global.test.pass("",mediaModel);
				//Global.test.log(Status.PASS, "Test Passed");


			} else if (strPassFail == "fail") {
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				String tImage = "Run_" + Utility.getCurrentDatenTime("dd-MM-yy") + "_"
						+ Utility.getCurrentDatenTime("H-mm-ss a");
				File destination = new File(Global.gstrScreenshotsDir + "\\" + Global.gTCName + "\\img_" + tImage + ".jpg");
				FileUtils.copyFile(scrFile,destination);
				MediaEntityModelProvider mediaModel = MediaEntityBuilder.createScreenCaptureFromPath(Global.gstrScreenshotsDir + "\\" + Global.gTCName + "\\img_" + tImage + ".jpg").build();
				Global.test.fail("Test step failed", mediaModel);
				//				Global.test.log(Status.FAIL, tImage);
			}
		}
	}

	/**
	 * Gets the current daten time.
	 *
	 * @param format the format
	 * @return the current daten time
	 */
	/*----------------------------------------------------------------------------
	Function Name    	: getCurrentDatenTime
	Description     	: Function to get Current Date and Time
	Input Parameters 	: format
	Return Value    	: Current Time
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String getCurrentDatenTime(String format) {
		Calendar cal = Calendar.getInstance();
		cc = cal;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(cal.getTime());
	}
	/*----------------------------------------------------------------------------
	Function Name    	: getCurrentDatenTime
	Description     	: Function to get Current Date and Time
	Input Parameters 	: format
	Return Value    	: Current Time
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public Date  ng_addMonths(int nbMonths) throws ParseException {
		String format = "MM/dd/yyyy" ;//dd-MMM-yy
		SimpleDateFormat sdf = new SimpleDateFormat(format) ;
		String currentDate=Utility.getCurrentDatenTime("MM/dd/yyyy");
		Date dateAsObj = sdf.parse(currentDate) ;
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateAsObj);
		cal.add(Calendar.MONTH, nbMonths);
		Date dateAsObjAfterAMonth = cal.getTime() ;
		System.out.println(sdf.format(dateAsObjAfterAMonth));
		return dateAsObjAfterAMonth ;
	}



	/**
	 * Gets the object.
	 *
	 * @param key the key
	 * @return the object
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	/*----------------------------------------------------------------------------
	Function Name    	: getObject
	Description     	: Find element BY using object type and value
	Input Parameters 	: key
	Return Value    	: object
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	private static By getObject(String key) throws IOException {
		ReadObject object = new ReadObject();
		Properties allObjects = object.getObjectRepository();
		String obj = allObjects.getProperty(key);
		String[] arrOfStr = obj.split(",", 2);
		String objectType = arrOfStr[0];
		String objectValue = arrOfStr[1];
		// private By getObject(Properties p,String objectName,String objectType) throws
		// Exception{
		// Find by xpath
		if (objectType.equalsIgnoreCase("XPATH")) {
			return By.xpath(objectValue);
		}
		// find by class
		else if (objectType.equalsIgnoreCase("NAME")) {
			return By.name(objectValue);
		}
		// find by name
		else if (objectType.equalsIgnoreCase("CLASSNAME")) {
			return By.className(objectValue);
		}
		// Find by css
		else if (objectType.equalsIgnoreCase("CSSSELECTOR")) {
			return By.cssSelector(objectValue);
		}
		// find by link
		else if (objectType.equalsIgnoreCase("LINK")) {
			return By.linkText(objectValue);
		} else if (objectType.equalsIgnoreCase("TAGNAME")) {
			return By.tagName(objectValue);
		} else if (objectType.equalsIgnoreCase("ID")) {
			return By.id(objectValue);
		}
		// find by partial link
		else if (objectType.equalsIgnoreCase("PARTIALLINK")) {
			return By.partialLinkText(objectValue);
		}
		return null;
	}

	/*----------------------------------------------------------------------------
	Function Name    	: waitForPageToLoad
	Description     	: wait For Page To Load
	Input Parameters 	: None
	Return Value    	: None
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	/**
	 * Wait for page to load.
	 */
	// Wait 
	public static void waitForPageToLoad() {    	
		// Wait for Javascript to load
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};   
		WebDriverWait wait = new WebDriverWait(driver, 5000);
		wait.until(expectation);
		driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);

		for (int i=0; i<25; i++){ 
			try {
				Thread.sleep(1000);				
			} catch (InterruptedException e) {} 
			//To check page ready state.
			if (js.executeScript("return document.readyState").toString().equals("complete")){ 
				break; 
			}   
		}
	}

	/*----------------------------------------------------------------------------
	Function Name    	: ng_getTextBoxValue
	Description     	: Get the text box value
	Input Parameters 	: 
	Return Value    	: String
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/

	/**
	 * Ng get text box value.
	 *
	 * @param element the element
	 * @param elementDescription the element description
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String ng_getTextBoxValue(WebElement element, String elementDescription) throws Exception {
		String attValue = "";
		ng_scrollIntoViewElement(element, elementDescription);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Object o = js.executeScript("return arguments[0].value;", element);
		attValue = (o == null) ? "" : o.toString();        
		return attValue;	  
	}
	
	
	/*----------------------------------------------------------------------------
	Function Name    	: ng_getAttribute
	Description     	: Get any attribute value of Webelement
	Input Parameters 	: 
	Return Value    	: String
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_getAttribute(WebElement element, String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		
		String attrValue=null;
		try {
			waitForPageToLoad();
			waitForSpinner();
			explicitWait(element,20);			
			ng_waitUntilElementDisplayed(element,20);
			ng_scrollIntoViewElement(element, strLabel);			
			ng_waitForElementEnabled(element,20);
			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}
			attrValue=element.getAttribute(strVal);
			String strDesc = "Value of attribute "+strVal+" is "+attrValue+" , taken from '" + strLabel + "'  WebElement";
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			char specialChar='}';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)==specialChar)
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			ng_ResultLogFail(strDesc);
		} 	
		return attrValue;	
	}

	/**
	 * Ng scroll into view element.
	 *
	 * @param element the element
	 * @param elementDescription the element description
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
	Function Name    	: ng_scrollIntoViewElement
	Description     	: 
	Input Parameters 	: 
	Return Value    	: 
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static void ng_scrollIntoViewElement(WebElement element, String elementDescription) throws Exception {
		try {
			if (!element.isDisplayed()) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"+element.getLocation().y+")");
				//((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			}			
		} catch (Exception e) {

		}
	}

	/**
	 * Clear text field.
	 *
	 * @param element the element
	 */
	/*----------------------------------------------------------------------------
	Function Name    	: clearTextField
	Description     	: 
	Input Parameters 	: 
	Return Value    	: 
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static void clearTextField(WebElement element) {
		//element.sendKeys(Keys.HOME, Keys.SHIFT, Keys.END, Keys.BACK_SPACE);
		element.sendKeys(Keys.END, Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
		clearTextFieldMulLines(element);
	}

	/**
	 * Clear text field mul lines.
	 *
	 * @param element the element
	 */
	/*----------------------------------------------------------------------------
	Function Name    	: clearTextFieldMulLines
	Description     	: 
	Input Parameters 	: 
	Return Value    	: 
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static void clearTextFieldMulLines(WebElement element) {
		element.sendKeys(Keys.CONTROL, Keys.HOME);
		element.sendKeys(Keys.CONTROL, Keys.SHIFT, Keys.END);
		element.sendKeys(Keys.BACK_SPACE);
	}

	/*----------------------------------------------------------------------------
	Function Name    	: ng_waitImplicitly
	Description     	: Method for waiting a certain amount of time.
	Input Parameters 	: 
	Return Value    	: 
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/

	/**
	 * Ng wait implicitly.
	 *
	 * @param time the time
	 */
	public static void ng_waitImplicitly(int time) {

		long startTime = 0;
		long endTime = 0;
		startTime = System.currentTimeMillis();
		for (int i = 0; i < time; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		endTime = System.currentTimeMillis();		
	}

	/**
	 * Ng click using actions.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
	Function Name    	: ng_clickUsingActions
	Description     	: Method clicks on a specific element using actions.
	Input Parameters 	: 
	Return Value    	: 
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_clickUsingActions(WebElement element, String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {
			waitForPageToLoad();
			waitForSpinner();
			explicitWait(element,20);			
			ng_waitUntilElementDisplayed(element,20);
			ng_scrollIntoViewElement(element, strLabel);			
			ng_waitForElementEnabled(element,20);
			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}
			Actions builder = new Actions(driver);
			builder.moveToElement(element).click().build().perform();	
			waitForPageToLoad();           

			String strDesc = "Successfully clicked on '" + strLabel + "'  WebElement";
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			char specialChar='}';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)==specialChar)
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			ng_ResultLogFail(strDesc);
		} 	
		return Global.bResult;		
	}

	/**
	 * Ng select window.
	 *
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
  	Function Name    	: ng_selectWindow
  	Description     	: Selects a particular Window on the basis of the parameters passed.
  	Input Parameters 	: 
  	Return Value    	: 
  	Author		        : 
  	Date of creation	:
	Date of modification:
  	----------------------------------------------------------------------------*/
	public static String ng_selectWindow(String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		String windowId = null;
		try {			
			waitForPageToLoad();
			//ng_waitImplicitly(5);

			driver.switchTo().activeElement();
			windowId = driver.getWindowHandle();
			driver.switchTo().window(windowId);
			//waitForPageToLoad();
			String strDesc = "Window has switched to: " + windowId;
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			String strDesc = "<span style='color:#ff4d4d;'>Failed to switched to: " + windowId + " Error Message : " + e.getMessage()+"</span>";
			ng_ResultLogFail(strDesc);
		}					
		return strVal;
	}

	/**
	 * Ng select frame.
	 *
	 * @param element the element
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
  	Function Name    	: ng_selectFrame
  	Description     	: Selects a particular Frame on the basis of the parameters passed.
  	Input Parameters 	: 
  	Return Value    	: 
  	Author		        : 
  	Date of creation	:
	Date of modification:
  	----------------------------------------------------------------------------*/
	public static String ng_selectFrame(WebElement element,String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		String windowId = null;
		try {			
			waitForPageToLoad();
			//ng_waitImplicitly(5);

			driver.switchTo().frame(element);

			//waitForPageToLoad();
			String strDesc = "Switched for Frame";
			ng_ResultLogPass(strDesc);


		} catch (Exception e) {
			String strDesc = "<span style='color:#ff4d4d;'>Failed to switched to frame : " + e.getMessage()+"</span>";
			ng_ResultLogFail(strDesc);
		}					
		return strVal;
	}

	/**
	 * Ng type and tab.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
  	Function Name    	: ng_typeAndTab
  	Description     	: ng_typeAndTab
  	Input Parameters 	: 
  	Return Value    	: 
  	Author		        :
  	Date of creation	:
	Date of modification: 
  	----------------------------------------------------------------------------*/
	public static String ng_typeAndTab(WebElement element, String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);					 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {
			//waitForSpinner();
			ng_enterText(element, strLabel, strKey);
			ng_sendTab(element, strLabel);
			//takeScreenShotAndLog("pass");		   
			Global.bResult = "True";
		} catch (Exception e) {			
			Global.bResult = "False";
			Global.objErr = "11";
			//takeScreenShotAndLog("fail");	
		}		
		return Global.bResult;
	}

	/**
	 * Clear text and tab.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
  	Function Name    	: clearTextAndTab
  	Description     	: clearTextAndTab
  	Input Parameters 	: 
  	Return Value    	: 
  	Author		        : 
  	Date of creation	:
	Date of modification:
  	----------------------------------------------------------------------------*/
	public static String clearTextAndTab(WebElement element, String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);   	 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {
			//waitForPageToLoad();
			waitForSpinner();
			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}
			//ng_waitUntilElementDisplayed(element,20);
			//ng_scrollIntoViewElement(element, strLabel);
			//ng_waitForElementEnabled(element,20);
			element.clear();
			//clearTextField(element);
			ng_sendTab(element, strLabel);
			//waitForPageToLoad();
			String strDesc = "Cleared text for " + strLabel + "' textbox";
			writeHTMLResultLog(strDesc, "pass");
			Global.bResult = "True";
		} catch (Exception e) {	
			char specialChar='}';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)==specialChar)
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			ng_ResultLogFail(strDesc);
		} 				
		return Global.bResult;
	}


	/**
	 * Ng send tab.
	 *
	 * @param ele the ele
	 * @param description the description
	 */
	/*----------------------------------------------------------------------------
  	Function Name    	: ng_sendTab
  	Description     	: ng_typeAndTab
  	Input Parameters 	: 
  	Return Value    	: 
  	Author		        : 
  	Date of creation	:
	Date of modification:
  	----------------------------------------------------------------------------*/
	public static void ng_sendTab(WebElement ele, String description) {
		try {
			ele.sendKeys(Keys.TAB);
			waitForPageToLoad();	        									
		} catch (Exception e) {

		}		
	}

	/**
	 * Ng get element text.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
  	Function Name    	: ng_getElementText
  	Description     	: ng_getElementText
  	Input Parameters 	: 
  	Return Value    	: 
  	Author		        : 
  	Date of creation	:
	Date of modification:
  	----------------------------------------------------------------------------*/
	public static String ng_getElementText(WebElement element, String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);  				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {			
			waitForPageToLoad();

			//explicitWait(element,20);
			WebElement objElement = wait.until(ExpectedConditions.visibilityOf(element));
			//ng_waitUntilElementDisplayed(objElement,20);
			//ng_scrollIntoViewElement(objElement, strLabel);			

			if(Global.gstrHighlighter == true) {
				highLighterMethod(objElement);
			}
			String value = objElement.getText();
			//String strDesc = "<span style='color:#4CAF50;'> Successfully verified the text <span style='color:#1ff3f5;'>'"+ value +"'</span>&nbsp <span style='color:#4CAF50;'> for &nbsp'" + strLabel + "'  WebElement </span>";
			String strDesc = "Retrieved text <span style='font-weight:bold;'>'"+ value +"'</span>&nbsp for &nbsp'" + strLabel + "'  WebElement";
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			char specialChar='}';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)==specialChar)
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			ng_ResultLogFail(strDesc);
		} 		
		return Global.bResult;
	}

	/**
	 * Explicit wait.
	 *
	 * @param elementID the element ID
	 * @param timeout the timeout
	 */
	/*----------------------------------------------------------------------------
   	Function Name    	: explicitWait
   	Description     	: explicit Wait
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static void explicitWait(WebElement elementID, long timeout) {	
		long startTime = 0;
		long endTime = 0;
		By locator = null;
		try {
			locator = getByFromWebElement(elementID);


			final By locator_Final = locator;

			startTime = System.currentTimeMillis();
			elementID = (new WebDriverWait(driver, timeout))
					.until(new ExpectedCondition<WebElement>() {
						@Override
						public WebElement apply(WebDriver wd) {
							return wd.findElement(locator_Final);
						}
					});
			try {
				//driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
				wait.until(ExpectedConditions.visibilityOf(elementID));			
				//wait.until(ExpectedConditions.visibilityOfElementLocated(locator_Final));
				//wait.until(ExpectedConditions.presenceOfElementLocated(locator_Final));
				wait.until(ExpectedConditions.elementToBeClickable(locator_Final));									
			} catch (TimeoutException te) {
				System.out.println("Time out exception for wait visibility: " + te.getMessage());
			}
			endTime = System.currentTimeMillis();			
		} catch (Exception e) {
			endTime = System.currentTimeMillis();			
		}
	}

	/**
	 * Gets the by from web element.
	 *
	 * @param elementID the element ID
	 * @return the by from web element
	 */
	/*----------------------------------------------------------------------------
   	Function Name    	: getByFromWebElement
   	Description     	: get By From WebElement
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static By getByFromWebElement(WebElement elementID) {
		By byLocator = null;
		String allStr = getNameFromObjectID(elementID);
		String elementPath = allStr.split(":")[1].trim();
		String locatorName = allStr.split(":")[0].trim();
		if (elementPath.endsWith("]")) {
			elementPath = elementPath.substring(0, elementPath.length() - 1)
					.trim();
		}
		if (locatorName.indexOf(" ") != -1
				&& !locatorName.equals("partial link text")) {
			String partOne = locatorName.split(" ")[0];
			String partTwo = locatorName.split(" ")[1];
			partTwo = Character.toUpperCase(partTwo.charAt(0))
					+ partTwo.substring(1);
			locatorName = partOne + partTwo;
		} else if (locatorName.equals("partial link text")) {
			locatorName = "partialLinkText";
		}
		try {
			switch (locatorName) {
			case "className":
				byLocator = By.className(elementPath);
				break;
			case "cssSelector":
				byLocator = By.cssSelector(elementPath);
				break;
			case "id":
				byLocator = By.id(elementPath);
				break;
			case "linkText":
				byLocator = By.linkText(elementPath);
				break;
			case "name":
				byLocator = By.name(elementPath);
				break;
			case "partialLinkText":
				byLocator = By.partialLinkText(elementPath);
				break;
			case "tagName":
				byLocator = By.tagName(elementPath);
				break;
			case "xpath":
				byLocator = By.xpath(elementPath);
				break;
			default:
				break;
			}
		} catch (NoSuchElementException elEx) {
			elEx.printStackTrace();
		}
		return byLocator;
	}

	/**
	 * Gets the name from object ID.
	 *
	 * @param elementID the element ID
	 * @return the name from object ID
	 */
	public static String getNameFromObjectID(WebElement elementID) {
		return elementID.toString().split("->")[1].trim();
	}

	/**
	 * Ng click element using JS.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
   	Function Name    	: ng_clickElementUsingJS
   	Description     	: Method clicks on a specific element using JS.
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static String ng_clickElementUsingJS(WebElement element, String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey); 				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {
			waitForPageToLoad();
			waitForSpinner();
			explicitWait(element,20);
			ng_waitUntilElementDisplayed(element,20);
			ng_scrollIntoViewElement(element, strLabel);			
			ng_waitForElementEnabled(element,20);
			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			waitForPageToLoad();
			String strDesc = "Successfully clicked on '" + strLabel + "'  WebElement";
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			char specialChar='}';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)==specialChar)
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			ng_ResultLogFail(strDesc);
		}		
		return Global.bResult;	
	}

	/**
	 * Click item.
	 *
	 * @param strItenVal the str iten val
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
   	Function Name    	: clickItem
   	Description     	: clickItem - Application Utility
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        :
   	Date of creation	:
	Date of modification: 
   	----------------------------------------------------------------------------*/
	public static String clickItem(String strItenVal) throws Exception {		
		try {			
			Utility.waitForPageToLoad();
			WebElement element = driver.findElement(By.xpath("//td//span[text()='"+ strItenVal +"']"));
			explicitWait(element,20);
			ng_scrollIntoViewElement(element, strItenVal);
			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}
			element.click();
			Utility.waitForPageToLoad();

			String strDesc = "Successfully clicked on item : " + strItenVal;
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			String strDesc = "<span style='color:#ff4d4d;'>Failed to click on " + strItenVal + " Error Message : " + e.getMessage()+"</span>";
			ng_ResultLogFail(strDesc);
		}
		return Global.bResult;
	}

	/**
	 * Logout finally.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
   	Function Name    	: logoutFinally
   	Description     	: logoutFinally
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static String logoutFinally() throws Exception {	
		//WebDriverWait wait = new WebDriverWait(driver, 20);
		try {						
			Utility.waitForPageToLoad();						

			WebElement elmUserName = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a//span[contains(@class,'xiq')]"))));		    
			highLighterMethod(elmUserName);			
			elmUserName.click();

			Utility.waitForPageToLoad();
			//ng_waitImplicitly(5);						

			WebElement elmSignOut = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(text(),'Sign Out')]"))));			
			highLighterMethod(elmSignOut);			
			elmSignOut.click();
			Utility.waitForPageToLoad();
			//ng_waitImplicitly(5);

			WebElement eleWarning = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[text()='Yes']"))));
			highLighterMethod(eleWarning);
			if (eleWarning != null) {
				eleWarning.click();
				Utility.waitForPageToLoad();
				//ng_waitImplicitly(5);
			}		    						

			WebElement elmConfirm = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[@id='Confirm']"))));			 			
			highLighterMethod(elmConfirm);			
			elmConfirm.click();
			Utility.waitForPageToLoad();
			driver.quit();
		} catch (Exception e) {
			driver.quit();
		}
		return Global.bResult;	
	}

	/**
	 * High lighter method.
	 *
	 * @param element the element
	 */
	/*----------------------------------------------------------------------------
   	Function Name    	: highLighterMethod
   	Description     	: highLighterMethod
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification: border: 2px solid red;
   	----------------------------------------------------------------------------*/
	public static void highLighterMethod(WebElement element){
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow;');", element);
	}

	/**
	 * Click warning.
	 *
	 * @param element the element
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
   	Function Name    	: clickWarning
   	Description     	: clickWarning
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static void clickWarning(WebElement element) throws Exception {

		try {
			waitForPageToLoad();
			explicitWait(element,20);
			ng_scrollIntoViewElement(element, "");
			ng_waitImplicitly(2);
			ng_waitForElementEnabled(element,20);
			if (element != null) {
				element.click();
			}
		} catch (Exception e) {
			e.getMessage();
		}

	}

	/**
	 * Quitdriver.
	 */
	/*----------------------------------------------------------------------------
   	Function Name    	: quitdriver
   	Description     	: quitdriver
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static void quitdriver()
	{
		driver.close();		
		System.out.println("Driver is closed");	
		try 
		{
			driver.quit();//It will dispose the driver too.
		}
		catch(Exception ex)
		{
			System.out.println("Unable to Quit driver\nIt may be already prefectly closed\n:Error:"+ex.getMessage());
		}
		finally
		{
			String processName="chromedriver.exe";
			String OsName=System.getProperty("os.name");
			System.out.println("Your Test was run on OS:"+OsName);
			if(OsName.toLowerCase().contains("windows"))
			{
				if(isProcessStillExist(processName))
					killProcess(processName);
			}
			else
			{
				//TODO for Linux or Other OS
			}
		}

	}
	
	public static boolean isProcessStillExist(String processName)
	{
		processName=processName.toLowerCase();
		try 
		{
			 Process processList = Runtime.getRuntime().exec("tasklist");
			 BufferedReader buffrdr = new BufferedReader(new InputStreamReader(
					 processList.getInputStream()));
			 String processDetails;
			 while ((processDetails = buffrdr.readLine()) != null)
			 {
			 // System.out.println(processDetails);
			  if(processDetails.toLowerCase().contains(processName))
			  {
				  Runtime.getRuntime().exec("taskkill /F /IM " + processName);
				  return true;
			  }
			 }
		}
		catch(Exception exp)
		{
			System.out.println("Error while getting process");
		}
		System.out.println("Happy to announce that your process is already stopped!!!");
		return false;
	}
	
	public static void killProcess(String processName)
	{
		try
		{
			Runtime.getRuntime().exec("taskkill /F /IM " + processName);
			System.out.println("Your process is closed forcefully");
		}
		catch(Exception ex)
		{
			System.out.println("Sorry I can not kill your process\n Error:"+ex.toString());
		}
	}

	/**
	 * Ng wait for element enabled.
	 *
	 * @param ele the ele
	 * @param sTime the s time
	 */
	/*----------------------------------------------------------------------------
   	Function Name    	: ng_waitForElementEnabled
   	Description     	: Wait until element is enabled for particular number of seconds
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static void ng_waitForElementEnabled(WebElement ele, int sTime) {
		//ng_waitImplicitly(1);
		for (int k = 0; k <= sTime; k++) {
			ng_waitImplicitly(1);
			if (ele.isEnabled()) {
				break;
			}
		}
	}

	/**
	 * Ng wait until element displayed.
	 *
	 * @param ele the ele
	 * @param sTime the s time
	 */
	/*----------------------------------------------------------------------------
   	Function Name    	: ng_waitUntilElementDisplayed
   	Description     	: Wait until element is displayed for particular number of seconds
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static void ng_waitUntilElementDisplayed(WebElement ele, int sTime) {
		//ng_waitImplicitly(1);
		for (int k = 0; k <= sTime; k++) {
			ng_waitImplicitly(1);
			if (ele.isDisplayed()) {
				break;
			}
		}
	}

	/**
	 * Ng wait until element visible.
	 *
	 * @param ele the ele
	 * @param sTime the s time
	 */
	/*----------------------------------------------------------------------------
   	Function Name    	: ng_waitUntilElementVisible
   	Description     	: Wait until element is Visible for particular number of seconds
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static void ng_waitUntilElementVisible(WebElement ele, int sTime) {
		wait.until(ExpectedConditions.visibilityOf(ele));
	}

	/**
	 * Ng random alpha num.
	 *
	 * @param strChar the str char
	 * @param length the length
	 * @return the string
	 */
	/*----------------------------------------------------------------------------
   	Function Name    	: ng_waitUntilElementVisible
   	Description     	: Wait until element is Visible for particular number of seconds
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static String ng_RandomAlphaNum (String strChar,int length){
		String alphabet = 
				new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"); //9
		int n = alphabet.length(); //10

		String result = new String(); 
		Random r = new Random(); //11

		for (int i=0; i<length; i++) //12
			result = result + alphabet.charAt(r.nextInt(n)); //13

		return strChar + result;
	}


	/**
	 * Ng random num.
	 *
	 * @return the int
	 */
	/*----------------------------------------------------------------------------
   	Function Name    	: ng_waitUntilElementVisible
   	Description     	: Wait until element is Visible for particular number of seconds
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static int ng_RandomNum (){

		int range = (100000 - 1) + 1;     
		int a= (int)(Math.random() * range) + 1;

		return a;
	}

	/**
	 * Ng random number with nine digits.
	 *
	 * @return the int
	 */
	/*----------------------------------------------------------------------------
   	Function Name    	: ng_RandomNumLRG
   	Description     	: Generates random number of 9 digits
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static int ng_RandomNumNineDigits (){

		Random rand = new Random();
		int l = rand.nextInt(999999999);
		return l;
	}


	/**
	 * Ng get digits from string.
	 *
	 * @param strValue the str value
	 * @return the string
	 */
	/*----------------------------------------------------------------------------
   	Function Name    	: getDigitsFromString
   	Description     	: Wait until element is Visible for particular number of seconds
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static String ng_getDigitsFromString(String strValue){
		String str = strValue.trim();
		String digits="";
		for (int i = 0; i < str.length(); i++) {
			char chrs = str.charAt(i);              
			if (Character.isDigit(chrs))
				digits = digits+chrs;
		}
		return digits;
	}

	/**
	 * Ng drop down by index.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
		Function Name    	: ng_DropDownByIndex
		Description     	: 
		Input Parameters 	: strObject - Object Name of Edit Box
							: strLabel - To be printed on extent report
		                    : strKey - Paramiter name to get the data value from TestData Table                        
		Return Value    	: bResult
		Author		        : 
		Date of creation	:
		Date of modification:
		----------------------------------------------------------------------------*/
	public static String ng_DropDownByIndex(WebElement element,String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);			
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}	
		try {
			waitForPageToLoad();
			//explicitWait(element, 20);
			ng_scrollIntoViewElement(element, strLabel);	
			Select objSelect = new Select(element);
			int index=Integer.parseInt(strVal);
			objSelect.selectByIndex(index);		
			String strDesc = "Value <span style='color:#c3ac39 !important;'>'" + strLabel + "'</span> is selected successfully from '" + strLabel + "' list";
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			char specialChar='}';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)==specialChar)
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			ng_ResultLogFail(strDesc);
		} 	
		return Global.bResult;
	}


	/**
	 * Ng click ok.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
		Function Name    	: clickWebElement
		Description     	: This function clicks the WebElement object
		Input Parameters 	: strObject - Object Name of Web Element
							: strLabel - To be printed on extent report
		                    : strKey - Paramiter name to get the data value from TestData Table
		Return Value    	: bResult
		Author		        : 
		Date of creation	:
		Date of modification:
		----------------------------------------------------------------------------*/
	public static String ng_clickOk(WebElement element, String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {
			/*waitForPageToLoad();
				explicitWait(element,20);
				ng_waitUntilElementDisplayed(element,20);
				ng_scrollIntoViewElement(element, strLabel);			
				ng_waitForElementEnabled(element,20);
				if(Global.gstrHighlighter == true) {
					highLighterMethod(element);
				}*/
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			//waitForPageToLoad();
			String strDesc = "Successfully clicked on '" + strLabel + "'  WebElement.";
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			char specialChar='}';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)==specialChar)
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			ng_ResultLogFail(strDesc);
		}			
		return Global.bResult;
	}


	/**
	 * Ng get element text pop up.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
	  	Function Name    	: ng_getElementText_PopUp
	  	Description     	: ng_getElementText
	  	Input Parameters 	: 
	  	Return Value    	: 
	  	Author		        : 
	  	Date of creation	:
		Date of modification:
	  	----------------------------------------------------------------------------*/
	public static String ng_getElementText_PopUp(WebElement element, String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);  				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {			
			//waitForPageToLoad();		
			//explicitWait(element,20);mahesh
			//WebElement objElement = wait.until(ExpectedConditions.visibilityOf(element));
			//ng_waitUntilElementDisplayed(objElement,20);
			//ng_scrollIntoViewElement(objElement, strLabel);			
			String value=(String) ((JavascriptExecutor) driver).executeScript("return arguments[0].text;", element);


			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}
			//String value = objElement.getText();
			String strDesc = "Successfully get the text "+ value +" for '" + strLabel + "'  WebElement.";
			writeHTMLResultLog(strDesc, "pass");
			takeScreenShotAndLog("pass");
			Global.bResult = "True";					
		} catch (Exception e) {
			char specialChar='}';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)==specialChar)
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			ng_ResultLogFail(strDesc);
		} 		
		return Global.bResult;
	}

	/**
	 * Ng click simply.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
		Function Name    	: ng_clickSimply
		Description     	: This function clicks the WebElement object
		Input Parameters 	: strObject - Object Name of Web Element
							: strLabel - To be printed on extent report
		                    : strKey - Paramiter name to get the data value from TestData Table
		Return Value    	: bResult
		Author		        : 
		Date of creation	:
		Date of modification:
		----------------------------------------------------------------------------*/
	public static String ng_clickSimply(WebElement element, String strLabel, String strKey) throws Exception {
		String strVal = Utility.getTestDataValue(strKey);
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {		
			Utility.waitForPageToLoad();
			//waitForSpinner();
			if(Global.gstrHighlighter == true) {
				Utility.highLighterMethod(element);
			}			
			element.click();	
			Utility.waitForPageToLoad();
			String strDesc = "Successfully clicked on '" + strLabel + "'  WebElement";			
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			//char specialChar='}';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)=='}' ||exception.charAt(i)==')' )
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			ng_ResultLogFail(strDesc);
		} 								
		return Global.bResult;
	}
	
	/**
	 * Ng enter text.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
	Function Name    	: ng_enterTextWithoutClick
	Description     	: This function enters a data into a text box without a click
	Input Parameters 	: strObject - Object Name of Edit Box
						: strLabel - To be printed on extent report
	                    : strKey - Paramiter name to get the data value from TestData Table                        
	Return Value    	: bResult
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_enterTextWithoutClick(WebElement element, String strLabel, String strKey) throws Exception {

		String strVal = getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}	
		try {
			waitForPageToLoad();
			Utility.waitForSpinner();
			ng_waitUntilElementVisible(element,60);			
			//ng_waitUntilElementDisplayed(element,20); //mahesh
			ng_scrollIntoViewElement(element, strLabel);						
			//element.click();
			if(StringUtils.isNotEmpty(ng_getTextBoxValue(element, strLabel))) {
				clearTextField(element);
				//ng_waitImplicitly(1);		mahesh    	
				//waitForPageToLoad();
			}	
			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}	

			if (strVal.equalsIgnoreCase("RANDOM")) {
				strVal =""+Utility.ng_RandomNum();
			}
			if (strVal.equalsIgnoreCase("DATE")) {
				strVal = Utility.getCurrentDatenTime("MM-dd-yy");
			}
			if (strVal.equalsIgnoreCase("DATEMONYEAR")) {
				strVal = Utility.getCurrentDatenTime("dd-MM-yyyy");
			}
			element.sendKeys(strVal);
			//waitForPageToLoad();
			String strDesc = "Successfully entered <span style='color:#c3ac39 !important;'>'" + strVal + "'</span> in <span style='font-weight:bold;'>'" + strLabel + "'</span> textbox";    
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			//char specialChar=')';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)=='}' ||exception.charAt(i)==')')
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			//String strDesc = "<span style='color:#ff4d4d;'>WebElement '" + strLabel +  "' is not displayed on the screen." + "</span>"; //"<span style='color:#ff4d4d;'>WebElement " + strLabel +  "' is not displayed on the screen. Error Message : " + e.getMessage()+"</span>"
			ng_ResultLogFail(strDesc);
		}
		return Global.bResult;
	}

	/**
	 * Ng enter text direct.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
		Function Name    	: ng_enterTextDirect
		Description     	: This function enters a data into a text box
		Input Parameters 	: strObject - Object Name of Edit Box
							: strLabel - To be printed on extent report
		                    : strKey - Paramiter name to get the data value from TestData Table                        
		Return Value    	: bResult
		Author		        : 
		Date of creation	:
		Date of modification:
		----------------------------------------------------------------------------*/
	public static String ng_enterTextDirect(WebElement element, String strLabel, String strKey) throws Exception {

		String strVal = getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}	
		try {
			waitForPageToLoad();
			ng_scrollIntoViewElement(element, strLabel);						
			clearTextField(element);	
			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}	
			element.sendKeys(strVal);
			//waitForPageToLoad();
			String strDesc = "Successfully entered <span style='color:#c3ac39 !important;'>'" + strVal + "'</span> in <span style='font-weight:bold;'>'" + strLabel + "'</span> textbox";
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			char specialChar='}';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)==specialChar)
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			ng_ResultLogFail(strDesc);
		}
		return Global.bResult;
	}

	/**
	 * Ng type and enter.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
		Function Name    	: ng_SelectListDirect
		Description     	: 
		Input Parameters 	: strObject - Object Name of Edit Box
							: strLabel - To be printed on extent report
		                    : strKey - Paramiter name to get the data value from TestData Table                        
		Return Value    	: bResult
		Author		        : 
		Date of creation	:
		Date of modification:
		----------------------------------------------------------------------------*/
	public static String ng_TypeAndEnter(WebElement element,String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);	
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}	
		try {
			waitForSpinner();
			ng_enterText(element, strLabel, strKey);			
			ng_waitImplicitly(1);  
			element.sendKeys(Keys.ENTER);
			element.sendKeys(Keys.ENTER);
			waitForPageToLoad();	
			Global.bResult = "True";
		} catch (Exception e) {
			char specialChar='}';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)==specialChar)
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			ng_ResultLogFail(strDesc);

		} 	
		return Global.bResult;
	}

	/**
	 * Ng enter text pwd.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
		Function Name    	: ng_enterTextPwd
		Description     	: This function enters a data into password text box and in reports it hides the string
		Input Parameters 	: strObject - Object Name of Edit Box
							: strLabel - To be printed on extent report
		                    : strKey - Paramiter name to get the data value from TestData Table                        
		Return Value    	: bResult
		Date of creation	:
		Date of modification:
		----------------------------------------------------------------------------*/
	public static String ng_enterTextPwd(WebElement element, String strLabel, String strKey) throws Exception {

		String strVal = getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}	
		try {
			waitForPageToLoad();
			//ng_scrollIntoViewElement(element, strLabel);						
			//clearTextField(element);	
			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}	
			element.sendKeys(strVal);
			//waitForPageToLoad();
			String strDesc = "Successfully entered  <span style='color:#c3ac39 !important;'>'" + "****" + "'</span> in <span style='font-weight:bold;'>'" + strLabel + "'</span> textbox";
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			char specialChar='}';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)==specialChar)
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			ng_ResultLogFail(strDesc);
		}
		return Global.bResult;
	}

	/**
	 * Ng assert value.
	 *
	 * @param strActual the str actual
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
		Function Name    	: ng_assertValue
		Description     	: It comapares the actual and expected  result
		Date of creation	:
		Date of modification:
		----------------------------------------------------------------------------*/
	public static String ng_assertValue(String strActual, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);                                                              
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {                                        
			if(strActual.equalsIgnoreCase(strVal)) {
				String strDesc = "<span style='color:#4CAF50;'>Successfully verified value :</span><span style='font-weight:bold;'>&nbsp Expected&nbsp - &nbsp</span> "+ "<span style='color:#1ff3f5;'> '"+strActual+"'</span> " +" <span style='font-weight:bold;'>&nbsp Actual&nbsp - &nbsp</span> <span style='color:#1ff3f5;'>'" + strVal+"'</span>";
				ng_ResultLogPass(strDesc);

			} else {
				String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Test step verification failed: &nbsp</span><span style='font-weight:bold;'>&nbsp Expected&nbsp-&nbsp </span><span style='color:#ff4d4d;'>'" + strVal+"'</span><span style='font-weight:bold;'> Actual&nbsp-&nbsp </span><span style='color:#ff4d4d;'>'"+ strActual +"'</span>";
				ng_ResultLogFail(strDesc);
			}
		} catch (Exception e) {
			String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Test step verification failed &nbsp'"+ strActual +"'&nbsp with expected qauntity value &nbsp'" + strVal+"'</span>";
			ng_ResultLogFail(strDesc);
		}                              
		return Global.bResult;
	}
	/*----------------------------------------------------------------------------
    Function Name       : ng_assertValue_ByContains
    Description         : ng_assertValue_ByContains
    Date of creation    :
    Date of modification:
    ----------------------------------------------------------------------------*/	

	/**
	 * Ng assert value by contains.
	 * @param strExpected the str expected
	 * @param strActual the str actual
	 * @param strKey the str key
	 * @param label the label
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String ng_assertValue_ByContains(String stExpected,String strAct) throws Exception {
		String strExpected = getTestDataValue(stExpected);  

		String str = strAct;

		String strActual = str.replaceAll("[0-9]+", "######");

		System.out.println(strActual);


		try {        

			if(strActual.contains(strExpected)) {
				String strDesc ="<span style='color:#4CAF50;'>Successfully verified value :</span><span style='font-weight:bold;'>&nbsp Expected&nbsp - &nbsp</span> "+ "<span style='color:#1ff3f5;'> '"+strExpected+"'</span> " +" <span style='font-weight:bold;'>&nbsp Actual&nbsp - &nbsp</span> <span style='color:#1ff3f5;'>'" + strAct+"'</span>";
				ng_ResultLogPass(strDesc);

			} else {
				String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Test step verification failed: &nbsp</span><span style='font-weight:bold;'>&nbsp Expected&nbsp-&nbsp </span><span style='color:#ff4d4d;'>'" + strExpected+"'</span><span style='font-weight:bold;'> Actual&nbsp-&nbsp </span><span style='color:#ff4d4d;'>'"+ strAct +"'</span>";
				ng_ResultLogFail(strDesc);
			}
		} catch (Exception e) {
			String strDesc =  "<span style='font-weight:bold;color:#ff4d4d;'>Test step verification failed &nbsp'"+ strAct +"'&nbsp with expected qauntity value &nbsp'" + strExpected+"'</span>";
			ng_ResultLogFail(strDesc);
		}                              
		return Global.bResult;
	}
	/*----------------------------------------------------------------------------
  	Function Name    	: ng_switchWindow
  	Description     	: Selects a particular Window on the basis of the parameters passed.
  	Input Parameters 	: 
  	Return Value    	: 
  	Author		        : 
  	Date of creation	:
	Date of modification:
  	----------------------------------------------------------------------------*/
	public static String ng_switchWindow(String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		String windowId = null;
		try {

			parentWindow=driver.getWindowHandle();
			System.out.println(driver.switchTo().window(parentWindow).getTitle());
			Set<String>s1=driver.getWindowHandles();

			// Now we will iterate using Iterator
			Iterator<String> I1= s1.iterator();

			while(I1.hasNext())
			{

				child_window=I1.next();

				// Here we will compare if parent window is not equal to child window then we            will close

				if(!parentWindow.equals(child_window))
				{
					driver.switchTo().window(child_window);

					System.out.println(driver.switchTo().window(child_window).getTitle());
					windowId=driver.switchTo().window(child_window).getTitle();
				}

			}
			//waitForPageToLoad();
			String strDesc = "Window has switched to: " + windowId;
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			String strDesc = "<span style='color:#ff4d4d;'>Failed to switched to: " + windowId + " Error Message : " + e.getMessage()+"</span>";
			ng_ResultLogFail(strDesc);
		}					
		return strVal;
	}	
	/**
	 * Ng type and tab.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
  	Function Name    	: ng_typeAndTabWithColor
  	Description     	: ng_typeAndTabWithColor
  	Input Parameters 	: 
  	Return Value    	: 
  	Author		        :
  	Date of creation	:
	Date of modification: 
  	----------------------------------------------------------------------------*/
	public static String ng_typeAndTabWithColor(WebElement element, String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);					 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {
			//waitForPageToLoad();
			ng_enterTextWithColor(element, strLabel, strKey);
			ng_sendTab(element, strLabel);
			//waitForPageToLoad();		   
			Global.bResult = "True";
		} catch (Exception e) {			
			Global.bResult = "False";
			Global.objErr = "11";
		}		
		return Global.bResult;
	}
	/**
	 * Ng enter text.
	 *
	 * @param element the element
	 * @param strLabel the str label
	 * @param strKey the str key
	 * @return the string
	 * @throws Exception the exception
	 */
	/*----------------------------------------------------------------------------
	Function Name    	: enterTextWithColor
	Description     	: This function enters a data into a text box
	Input Parameters 	: strObject - Object Name of Edit Box
						: strLabel - To be printed on extent report
	                    : strKey - Paramiter name to get the data value from TestData Table                        
	Return Value    	: bResult
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_enterTextWithColor(WebElement element, String strLabel, String strKey) throws Exception {

		String strVal = getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}	
		try {
			waitForPageToLoad();
			ng_waitUntilElementVisible(element,20);			
			//ng_waitUntilElementDisplayed(element,20); //mahesh
			ng_scrollIntoViewElement(element, strLabel);						
			element.click();
			if(StringUtils.isNotEmpty(ng_getTextBoxValue(element, strLabel))) {
				clearTextField(element);
				//ng_waitImplicitly(1);		mahesh    	
				//waitForPageToLoad();
			}	
			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}	

			if (strVal.equalsIgnoreCase("RANDOM")) {
				strVal = Utility.ng_RandomAlphaNum("J",5);
			}
			element.sendKeys(strVal);
			//waitForPageToLoad();
			String strDesc = "Successfully entered <span style='color:#4CAF50;'>'" + strVal + "'</span> in '" + strLabel + "' textbox";
			writeHTMLResultLog(strDesc, "pass");
			Global.bResult = "True";
		} catch (Exception e) {
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' textbox does not exist. Error Message : " + e.getMessage()+"</span>";
			ng_ResultLogFail(strDesc);
		}
		return Global.bResult;
	}


	/*----------------------------------------------------------------------------
    Function Name       : ng_pressF12
    Description         : ng_pressF12
    Date of creation    :
    Date of modification:
    ----------------------------------------------------------------------------*/	
	public static void ng_pressF12() throws Exception {

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_F12);



	}

	/*----------------------------------------------------------------------------
    Function Name       : chkFileDownload
    Description         : chkFileDownload
    Date of creation    :
    Date of modification:
    ----------------------------------------------------------------------------*/
	public static String chkFileDownload(String DownloadPath, String xFileName, String strKey )throws Exception{
		String strVal = getTestDataValue(strKey);  
		String downloadPath = getTestDataValue(DownloadPath);
		String csvFileName =xFileName;
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try
		{   
			String filePath =	downloadPath + System.getProperty("file.separator") + csvFileName;
			File file = new File(filePath);
			if (file.exists()) {
				System.out.println(filePath);
				//File file = new File(filePath);
				if (!csvFileName.isEmpty() || csvFileName != null) {
					String strDesc = "<span style='color:#4CAF50;'>Report output successfully generated &nbsp : &nbsp</span><span style='font-weight:bold;'>Folder Path &nbsp-&nbsp</span><span style='color:#1ff3f5;'>'"+downloadPath+"'</span><span style='font-weight:bold;'>&nbsp File Name &nbsp-&nbsp </span> <span style='color:#1ff3f5;'> '"+file.getName()+"'</span> ";
					ng_ResultLogPass(strDesc);

				} else {
					String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Failed to download report file &nbsp"+"</span>";
					ng_ResultLogFail(strDesc);
				}
			}
		}

		catch (Exception e) {
			String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Failed to download report file &nbsp"+ e.getMessage()+"</span>";
			ng_ResultLogFail(strDesc);
		}                              
		return Global.bResult;
	}


	/*----------------------------------------------------------------------------
    Function Name       : getRowCountInCSV
    Description         : getRowCountInCSV
    Date of creation    :
    Date of modification:
    ----------------------------------------------------------------------------*/
	public static String getRowCountInCSV(String downloadPath, String csvFileName) throws Exception {

		try {

			String filePath =	downloadPath + System.getProperty("file.separator") + csvFileName;
			System.out.println("getRowCountInCSV file name-"+filePath);
			File file = new File(filePath);
			if (file.exists()) {

				Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath)); // or sample.xls
				Sheet sheet = null;
				Row row = null;
				XSSFCell cell = null;
				String rowCountInSheet="";
				String colCountInSheet="";
				String Data="";

				Global.excelSceetCount=workbook.getNumberOfSheets();
				for(int i=1; i<Global.excelSceetCount;i++)
				{
					sheet = workbook.getSheetAt(i);
					row = sheet.getRow(0);
					int colNum = row.getLastCellNum();
					System.out.println("Total Number of Columns in the excel is : "+colNum);
					int rowNum = sheet.getLastRowNum()+1;
					System.out.println("Total Number of Rows in the excel is : "+rowNum);
					Data=Data+"Sheet Name-"+workbook.getSheetName(i)+"Record Count-"+rowNum;
				}

				Global.excelSceetCount=Global.excelSceetCount-2;
				System.out.println("Number Of Sheets" + Global.excelSceetCount);
				String strDesc = "<span style='color:#4CAF50;'>Successfully get the number of Sheets in a file &nbsp : &nbsp</span> <span style='color:#1ff3f5;'> '"+Data+"'</span> ";
				ng_ResultLogPass(strDesc);

			} else {
				String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Failed to Download Report File &nbsp"+"</span>";
				ng_ResultLogFail(strDesc);
			}

		}
		catch (IOException e) {
			String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Failed to Download Report File &nbsp"+ e.getMessage()+"</span>";
			ng_ResultLogFail(strDesc);
		}

		return Global.bResult;
	}

	/*----------------------------------------------------------------------------
    Function Name       : getSheetCountInCSV
    Description         : getSheetCountInCSV
    Date of creation    :
    Date of modification:
    ----------------------------------------------------------------------------*/
	public static String getSheetCountInCSV(String DownloadPath, String xFileName, String strKey ) throws Exception {
		String strVal = getTestDataValue(strKey);  
		String downloadPath = getTestDataValue(DownloadPath);
		String csvFileName = getTestDataValue(xFileName);
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}


		int lineNumberCount = 0;
		try {
			if (!csvFileName.isEmpty() || csvFileName != null) {
				String filePath =	downloadPath + System.getProperty("file.separator") + csvFileName;
				System.out.println(filePath);
				File file = new File(filePath);
				if (file.exists()) {
					/*System.out.println("File found :" +csvFileName);
					FileReader fr = new FileReader(file);
					LineNumberReader linenumberreader = new LineNumberReader(fr);
					while (linenumberreader.readLine() != null) {
						lineNumberCount++;
					}
					//To remove the header
					lineNumberCount=lineNumberCount-1;*/

					Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath)); // or sample.xls
					Global.excelSceetCount=workbook.getNumberOfSheets();
					Global.excelSceetCount=Global.excelSceetCount;
					System.out.println("Number Of Sheets" + Global.excelSceetCount);
					//Sheet sheet = workbook.getSheetAt(2);
					//System.out.println("Number Of Rows:" + sheet.getLastRowNum());


					String strDesc = "<span style='color:#4CAF50;'>Number of Sheets/Tabs in the downloaded report file &nbsp : &nbsp</span> <span style='color:#1ff3f5;'> '"+Global.excelSceetCount+"'</span> ";
					ng_ResultLogPass(strDesc);

				} else {
					String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Failed to get the sheet count in report file &nbsp"+"</span>";
					ng_ResultLogFail(strDesc);
				}
			}
		}
		catch (IOException e) {
			String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>failed to download report file &nbsp"+ e.getMessage()+"</span>";
			ng_ResultLogFail(strDesc);
		}

		return Global.bResult;
	}


	/*----------------------------------------------------------------------------
    Function Name       : GetExcelSheetNames
    Description         : GetExcelSheetNames
    Date of creation    :
    Date of modification:
    ----------------------------------------------------------------------------*/

	public static String getRecordsCountInCSV(String DownloadPath, String xFileName, String strKey ) throws Exception {
		String strVal = getTestDataValue(strKey);  
		String downloadPath = getTestDataValue(DownloadPath);
		String csvFileName = getTestDataValue(xFileName);
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		int lineNumberCount = 0;
		try {
			if (!csvFileName.isEmpty() || csvFileName != null) {
				String filePath =	downloadPath + System.getProperty("file.separator") + csvFileName;
				System.out.println(filePath);
				File file = new File(filePath);
				if (file.exists()) {
					System.out.println("File found :" +csvFileName);
					FileReader fr = new FileReader(file);
					LineNumberReader linenumberreader = new LineNumberReader(fr);
					while (linenumberreader.readLine() != null) {
						lineNumberCount++;
					}
					//To remove the header
					lineNumberCount=lineNumberCount-1;
					System.out.println("Total number of lines found in csv : " + (lineNumberCount-297));
					String strDesc = "<span style='color:#4CAF50;'>Total cells count in the report file&nbsp : &nbsp</span> <span style='color:#1ff3f5;'> '"+(lineNumberCount-297)+"'</span> ";
					ng_ResultLogPass(strDesc);
					linenumberreader.close();
				} else {

					String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Failed to get the number count in a report file &nbsp"+"</span>";
					ng_ResultLogFail(strDesc);
				}
			}
		}
		catch (IOException e) {
			String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Failed to get the number count in a report file &nbsp"+ e.getMessage()+"</span>";
			ng_ResultLogFail(strDesc);
		}

		return Global.bResult;

	}

	/*----------------------------------------------------------------------------
    Function Name       : getNumberOfEntries
    Description         : getNumberOfEntries
    Date of creation    :
    Date of modification:
    ----------------------------------------------------------------------------*/	

	public static int getNumberOfEntries(String id) {

		String entriesTxt = driver.findElement(By.id(id)).getText().trim();
		String[] aEntriesText = entriesTxt.split(" ");
		String totalEntriesText = aEntriesText[aEntriesText.length-2];
		return Integer.parseInt(totalEntriesText);
	}

	/*----------------------------------------------------------------------------
    Function Name       : Handle_Dynamic_Webtable
    Description         : Handle_Dynamic_Webtable
    Date of creation    :
    Date of modification:
    ----------------------------------------------------------------------------*/	

	public static String Handle_Dynamic_Webtable(WebElement ele) throws Exception {
		try {
			//To locate table.
			int columns_count=0;
			WebElement mytable = ele;              //driver.findElement(By.xpath("//table[contains(@id,'960_tableBody')]"));
			//To locate rows of table.
			List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
			//To calculate no of rows In table.
			int rows_count = rows_table.size();

			//Loop will execute till the last row of table.
			for (int row=0; row<rows_count; row++){
				//To locate columns(cells) of that specific row.
				List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
				//To calculate no of columns(cells) In that specific row.
				columns_count = Columns_row.size();
				//System.out.println("Number of cells In Row "+row+" are "+columns_count);

				//Loop will execute till the last cell of that specific row.
				for (int column=0; column<columns_count; column++){
					//To retrieve text from that specific cell.
					String celtext = Columns_row.get(column).getText();
					//System.out.println("Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);		
				}
				System.out.println("--------------------------------------------------");
			}  
			String strDesc = "<span style='color:#4CAF50;'>Successfully get the number of row and column in a report &nbsp : &nbsp</span> <span style='color:#1ff3f5;'> '"+rows_count+"'&nbsp &nbsp '"+columns_count+"'</span> ";
			ng_ResultLogPass(strDesc);


		}

		catch (IOException e) {
			String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>failed to download report &nbsp"+ e.getMessage()+"</span>";
			ng_ResultLogFail(strDesc);
		}

		return Global.bResult;
	}
	/*----------------------------------------------------------------------------
    Function Name       : Handle_Dynamic_Webtable2
    Description         : Handle_Dynamic_Webtable2
    Date of creation    :
    Date of modification:
    ----------------------------------------------------------------------------*/	
	public static String Handle_Dynamic_Webtable2() throws Exception {
		try {
			//List  col = driver.findElements(By.xpath(".//*[@id=\"leftcontainer\"]/table/thead/tr/th"));
			///int columns_count=col.size();
			//System.out.println("No of cols are : " +col.size()); 
			//No.of rows 
			List  rows = driver.findElements(By.xpath("//*[@id=\"960_tableBody\"]/tbody/tr[1]/td[1]")); 
			int rows_count=rows.size();
			System.out.println("No of rows are : " + rows.size());

			String strDesc = "<span style='color:#4CAF50;'>Successfully get the number of row and column in a report &nbsp : &nbsp</span> <span style='color:#1ff3f5;'> '"+rows_count+"'&nbsp &nbsp '"+"'</span> ";//columns_count
			ng_ResultLogPass(strDesc);


		}

		catch (IOException e) {
			String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>failed to download report &nbsp"+ e.getMessage()+"</span>";
			ng_ResultLogFail(strDesc);
		}

		return Global.bResult;
	}


	/*----------------------------------------------------------------------------
    Function Name       : GetExcelSheetNames
    Description         : GetExcelSheetNames
    Date of creation    :
    Date of modification:
    ----------------------------------------------------------------------------*/

	public static String getRowCountColumnCountInFile(String DownloadPath, String xFileName, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);  
		String downloadPath = getTestDataValue(DownloadPath);
		String csvFileName = getTestDataValue(xFileName);

		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		//int lineNumberCount = 0;
		try {

			String filePath =	downloadPath + System.getProperty("file.separator") + csvFileName;
			Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath)); // or sample.xls
			int sheetIndex=Integer.valueOf(strVal);
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			int rowNum=sheet.getLastRowNum();
			System.out.println("Number Of Rows:" + sheet.getLastRowNum());
			Row row = sheet.getRow(0);

			if(rowNum==0)
			{

				int colNum = 0;
				System.out.println("Total Number of Columns in the excel is : "+colNum);
				String strDesc = "<span style='color:#4CAF50;'>Sheet Name&nbsp-&nbsp</span><span style='color:#1ff3f5;'>'"+workbook.getSheetName(sheetIndex)+"'</span><span style='color:#4CAF50;'>&nbsp Total Number of Row Count&nbsp-&nbsp </span><span style='color:#1ff3f5;'>'"+rowNum+"'</span> <span style='color:#4CAF50;'>&nbsp Total Number of Column Count &nbsp-&nbsp</span><span style='color:#1ff3f5;'>'"+colNum+"'</span>";
				ng_ResultLogPass(strDesc);
			}
			else
			{
				int colNum = row.getLastCellNum();
				System.out.println("Total Number of Columns in the excel is : "+colNum);
				String  strDesc = "<span style='color:#4CAF50;'>Sheet Name&nbsp-&nbsp</span><span style='color:#1ff3f5;'>'"+workbook.getSheetName(sheetIndex)+"'</span><span style='color:#4CAF50;'>&nbsp Total Number of Row Count&nbsp-&nbsp </span><span style='color:#1ff3f5;'>'"+rowNum+"'</span> <span style='color:#4CAF50;'>&nbsp Total Number of Column Count &nbsp-&nbsp</span><span style='color:#1ff3f5;'>'"+colNum+"'</span>";
				writeHTMLResultLog(strDesc, "pass");
				takeScreenShotAndLog("pass");
				Global.bResult = "True";
			}


		}



		catch (IOException e) {
			String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Failed to get the row and column count in a report file &nbsp"+ e.getMessage()+"</span>";
			ng_ResultLogFail(strDesc);
		}

		return Global.bResult;
	}




	/*----------------------------------------------------------------------------
    Function Name       : getPDFPageCount
    Description         : getPDFPageCount
    Date of creation    :
    Date of modification:
    ----------------------------------------------------------------------------*/

	public static String getPDFPageCount(String DownloadPath, String xFileName, String strKey ) throws Exception {
		String strVal = getTestDataValue(strKey);  
		String downloadPath = getTestDataValue(DownloadPath);
		String csvFileName =xFileName;
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		int lineNumberCount = 0;
		try {
			if (!csvFileName.isEmpty() || csvFileName != null) {
				String filePath =	downloadPath + System.getProperty("file.separator") + csvFileName;
				System.out.println(filePath);
				File file = new File(filePath);
				if (file.exists()) { 
					PdfDocument doc = new PdfDocument();

					// Load an existing document
					doc.load(filePath);
					// Get page count and display it on console output
					Global.pdfPageCount=doc.getPageCount();
					System.out.println("Number of pages in .pdf is " +Global.pdfPageCount);
					String strDesc = "<span style='color:#4CAF50;'>Total number of pages in the PDF file&nbsp : &nbsp</span> <span style='color:#1ff3f5;'> '"+Global.pdfPageCount+"'</span> ";
					ng_ResultLogPass(strDesc);
					// Close document
					doc.close();  


				}
			} else {

				String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Failed to get the page number count in a report file &nbsp"+"</span>";
				ng_ResultLogFail(strDesc);
			}
		}

		catch (IOException e) {
			String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Failed to get the page number count in a report file &nbsp"+ e.getMessage()+"</span>";
			ng_ResultLogFail(strDesc);
		}

		return Global.bResult;

	}


	/*----------------------------------------------------------------------------
    Function Name       : getPDFPageCount
    Description         : getPDFPageCount
    Date of creation    :
    Date of modification:
    ----------------------------------------------------------------------------*/

	public static String getLinesCount(String DownloadPath, String xFileName, String strKey ) throws Exception {
		String strVal = getTestDataValue(strKey);  
		String downloadPath = getTestDataValue(DownloadPath);
		String csvFileName = getTestDataValue(xFileName);
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		int lineNumberCount = 0;
		try {
			if (!csvFileName.isEmpty() || csvFileName != null) {
				String filePath =	downloadPath + System.getProperty("file.separator") + csvFileName;
				System.out.println(filePath);
				File file = new File(filePath);
				if (file.exists()) {
					System.out.println("File found :" +csvFileName);
					FileReader fr = new FileReader(file);
					LineNumberReader linenumberreader = new LineNumberReader(fr);
					while (linenumberreader.readLine() != null) {
						lineNumberCount++;
					}
					//To remove the header
					lineNumberCount=lineNumberCount-1;
					System.out.println("Total number of lines found in PDF : " + (lineNumberCount-297));
					String strDesc = "<span style='color:#4CAF50;'>Total number of lines in report file&nbsp : &nbsp</span> <span style='color:#1ff3f5;'> '"+lineNumberCount+"'</span> ";
					ng_ResultLogPass(strDesc);
					linenumberreader.close();
				} else {

					String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Failed to get the number count in a report file &nbsp"+"</span>";
					ng_ResultLogFail(strDesc);
				}
			}
		}
		catch (IOException e) {
			String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Failed to get the number count in a report file &nbsp"+ e.getMessage()+"</span>";
			ng_ResultLogFail(strDesc);
		}

		return Global.bResult;

	}
	/*----------------------------------------------------------------------------
	Function Name    	: ng_displayBusinessRole
	Description     	: This function Display Message		
	Return Value    	: bResult
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_displayBusinessRole(String strKey) throws Exception
	{
		String strVal = getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		String strDesc = "<span style='color:#f7d420 !important;'>Successfully logged in with business role <span style='font-weight:bold;'>'"+ strVal +"'</span></span>";			
		ng_ResultLogPass(strDesc);
		return Global.bResult;
		//Project Manager
		//Payables Supervisor
	}
	/*----------------------------------------------------------------------------
	Function Name    	: ng_assertValueWithConfirmationWin
	Description     	: It comapares the actual and expected  result
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_assertValueWithConfirmationWin(String strActual, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);
		String expected=strActual;
		expected=expected.replaceAll("\\d", "#");
		System.out.println(expected);
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {                                        
			if(strActual.toLowerCase().contains(strVal.toLowerCase())) {

				String strDesc = "<span style='color:#4CAF50;'>Successfully verified value :</span><span style='font-weight:bold;'>&nbsp Expected&nbsp - &nbsp</span> "+ "<span style='color:#1ff3f5;'> '" +expected+ "'</span> " +" <span style='font-weight:bold;'>&nbsp Actual&nbsp - &nbsp</span> <span style='color:#1ff3f5;'>'" + strActual+"'</span>";
				ng_ResultLogPass(strDesc);

			} else {
				String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Test step verification failed: &nbsp</span><span style='font-weight:bold;'>&nbsp Expected&nbsp-&nbsp </span><span style='color:#ff4d4d;'> '" +strVal+ "'</span>"+"<span style='font-weight:bold;'>&nbsp Actual&nbsp-&nbsp </span><span style='color:#ff4d4d;'>'"+ strActual +"'</span>";
				ng_ResultLogFail(strDesc);
			}
		} catch (Exception e) {

			String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Test step verification failed &nbsp'"+ strActual +"'&nbsp with expected qauntity value &nbsp'" + strKey+"'</span>";
			ng_ResultLogFail(strDesc);
		}                              
		return Global.bResult;
	}

	/*----------------------------------------------------------------------------
	Function Name    	: ng_getElementTextFromPopupWin
	Description     	: ng_getElementText
	Input Parameters 	: 
	Return Value    	: 
	Author		        : 
	Date of creation	:
Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_getElementTextFromPopupWin(WebElement element, String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);  				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {			
			waitForPageToLoad();	
			waitForSpinner();
			//explicitWait(element,20);
			WebElement objElement = wait.until(ExpectedConditions.visibilityOf(element));
			//ng_waitUntilElementDisplayed(objElement,20);
			//ng_scrollIntoViewElement(objElement, strLabel);			

			if(Global.gstrHighlighter == true) {
				highLighterMethod(objElement);
			}
			String value = objElement.getText();
			String strDesc = "<span style='color:#4CAF50;'> Successfully verified pop-up window</span> <span style='color:#1ff3f5;'>'"+ value +"'</span>";
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			char specialChar='}';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)==specialChar)
				{
					break;
				}
			}

			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			ng_ResultLogFail(strDesc);
		} 		
		return Global.bResult;
	}



	/*----------------------------------------------------------------------------
	Function Name    	: ng_RandomNumSkipLogic
	Description     	: 
	Input Parameters 	: 
	Return Value    	: 
	Author		        : 
	Date of creation	:
Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_RandomNumSkipLogic(String strKey) throws Exception{
		String strVal = getTestDataValue(strKey);  				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		int range = (100000 - 1) + 1;     
		int a= (int)(Math.random() * range) + 1;
		String s=String.valueOf(a);
		return s;
	}



	/*----------------------------------------------------------------------------
Function Name    	: ng_selectMultipleElementInRow
Description     	: SelectMultipleElementInRow
Input Parameters 	: 
Return Value    	: 
Author		        : 
Date of creation	:
Date of modification:
----------------------------------------------------------------------------*/
	public static String ng_selectMultipleElementInRow(WebElement element1, WebElement element2, String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);  				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {			
			waitForPageToLoad();			
			explicitWait(element1,20);
			//WebElement objElement = wait.until(ExpectedConditions.visibilityOf(element1));
			//ng_waitUntilElementDisplayed(objElement,20);
			ng_scrollIntoViewElement(element1, strLabel);			

			if(Global.gstrHighlighter == true) {
				highLighterMethod(element1);
			}

			Actions actions = new Actions(driver);
			actions.keyDown(Keys.CONTROL)
			.click(element1)
			.click(element2)
			.keyUp(Keys.CONTROL)
			.build()
			.perform();

			String strDesc = "Successfully clicked on '" + strLabel + "'  WebElement";
			ng_ResultLogPass(strDesc);

		} catch (Exception e) {
			char specialChar='}';
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)==specialChar)
				{
					break;
				}
			}

			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			ng_ResultLogFail(strDesc);
		} 		
		return Global.bResult;
	}

	/*----------------------------------------------------------------------------
Function Name       : getTextFormFile
Description         : Check text present in a file or not
Date of creation    :
Date of modification:
----------------------------------------------------------------------------*/

	public static String ng_getTextFormFile(String DownloadPath, String xFileName, String strKey, String pageNum ) throws Exception {
		String strVal = getTestDataValue(strKey);  
		String downloadPath = getTestDataValue(DownloadPath);
		String csvFileName =xFileName;
		String pageNo= getTestDataValue(pageNum);

		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		int lineNumberCount = 0;
		try {
			if (!csvFileName.isEmpty() || csvFileName != null) {
				String filePath =	downloadPath + System.getProperty("file.separator") + csvFileName;
				System.out.println(filePath);
				File file = new File(filePath);
				if (file.exists()) {


					int i, n;
					PdfSearchElement pseResult;

					// Load a PDF document 
					PdfDocument doc = new PdfDocument();
					doc.load(filePath);

					// Obtain all instances of the word "alcohol" in page 
					ArrayList lstSearchResults1 = 
							(ArrayList) doc.search(strVal,
									pageNo,
									PdfSearchMode.LITERAL,
									PdfSearchOptions.NONE);
					// Close the document
					doc.close();

					// Iterate through all search results
					n = lstSearchResults1.size();        
					for (i = 0; i < n; i++) {
						pseResult = (PdfSearchElement) lstSearchResults1.get(i);
						// Print search results to console output
						System.out.println("Found \"" + 
								pseResult.getMatchString()  + 
								"\" in page #" + 
								pseResult.getPageNum() + 
								" text \"" + 
								pseResult.getLineContainingMatchString()  + 
								"\"" );

						String strDesc = "<span style='color:#4CAF50;'> Successfully verified text &nbsp</span> <span style='color:#1ff3f5;'>"+pseResult.getLineContainingMatchString()+ "</span>"+"&nbsp<span style='color:#4CAF50;'>in the file</span>";
						writeHTMLResultLog(strDesc, "pass");
					}

					//String strDesc = "<span style='color:#4CAF50;'>"+strVal+"String Present In File"+" In Page Number"+pseResult.getPageNum()+"Text"+pseResult.getLineContainingMatchString()+ "</span>";
					//writeHTMLResultLog(strDesc, "pass");
					takeScreenShotAndLog("pass");
					Global.bResult = "True";	


				} else {

					String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Failed to get the text in the report file &nbsp"+"</span>";
					ng_ResultLogFail(strDesc);
				}
			}
		}
		catch (IOException e) {
			String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Failed to get the text in file &nbsp"+ e.getMessage()+"</span>";
			ng_ResultLogFail(strDesc);
		}

		return Global.bResult;

	}


	/*----------------------------------------------------------------------------
  	Function Name    	: ng_getText
  	Description     	: to get element text
  	Input Parameters 	: 
  	Return Value    	: 
  	Author		        : 
  	Date of creation	:
	Date of modification:
  	----------------------------------------------------------------------------*/
	public static String ng_getText(WebElement element) throws Exception {
		//String strVal = getTestDataValue(strKey); 
		String value="";
		if ((Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {			
			waitForPageToLoad();		
			WebElement objElement = wait.until(ExpectedConditions.visibilityOf(element));
			//ng_waitUntilElementDisplayed(objElement,20);
			//ng_scrollIntoViewElement(objElement, strLabel);			
			if(Global.gstrHighlighter == true) {
				highLighterMethod(objElement);
			}
			value = objElement.getText();

		} catch (Exception e) {


			String strDesc = "<span style='color:#ff4d4d;'>'" + "' WebElement does not exist.<span style='font-weight:bold;'></span>";
			System.out.println(e.getMessage());
			ng_ResultLogFail(strDesc);

		} 		
		return value;
	}


	/*----------------------------------------------------------------------------
  	Function Name    	: ng_ResultLogFail
  	Description     	: Result Log Fail
  	Input Parameters 	: 
  	Return Value    	: 
  	Author		        : 
  	Date of creation	:
	Date of modification:
  	----------------------------------------------------------------------------*/
	public static void ng_ResultLogFail(String strDesc) throws Exception {
		try {			

			Utility.writeHTMLResultLog(strDesc, "fail");
			Utility.takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";

		} catch (Exception e) {
			System.out.println(e.getMessage());	
		} 		

	}


	/*----------------------------------------------------------------------------
  	Function Name    	: ng_ResultLogFail
  	Description     	: Result Log Fail
  	Input Parameters 	: 
  	Return Value    	: 
  	Author		        : 
  	Date of creation	:
	Date of modification:
  	----------------------------------------------------------------------------*/
	public static void ng_ResultLogPass(String strDesc) throws Exception {
		try {			

			writeHTMLResultLog(strDesc, "info");
			takeScreenShotAndLog("pass");
			Global.bResult = "True";

		} catch (Exception e) {
			System.out.println(e.getMessage());	
		} 		

	}	


	/*----------------------------------------------------------------------------
  	Function Name    	: ng_ScriptAbortedLog
  	Description     	: Script aborted unexpectedly, add exception details in log.
  	Input Parameters 	: 
  	Return Value    	: 
  	Author		        : 
  	Date of creation	:
	Date of modification:
  	----------------------------------------------------------------------------*/
	public static void ng_ScriptAbortedLog(Exception e1) throws Exception {
		try {			

			char specialChar='}';
			String exception=e1.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)==specialChar)
				{
					break;
				}
			}
			String strDesc = "<span style='color:#ff4d4d;'>'" + "' Script aborted unexpectedly..<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			Utility.ng_ResultLogFail(strDesc);

		} catch (Exception e) {
			System.out.println(e.getMessage());	
		} 		

	}

	/*----------------------------------------------------------------------------
   	Function Name    	: waitForSpinner
   	Description     	: waitForSpinner
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static void waitForSpinner() throws InterruptedException {

		// By findBy = By.cssSelector("fusePlus-container x1lq p_AFMaximized");
		try {
			//WebElement findBy=driver.findElement(By.cssSelector(".x1lq.p_AFMaximized"));
			//By findBy = By.cssSelector(".x1lq.p_AFMaximized");
			By findBy = By.xpath("//body[contains(@class,'AFMaximized')]");
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
			String bottom_cssValue = element.getAttribute("style");
			//System.out.println("CSS Value - "+bottom_cssValue);
			while(bottom_cssValue.equals("cursor: wait;"))
			{
				bottom_cssValue = element.getAttribute("style");
				//System.out.println("CSS Value In Loop -"+bottom_cssValue);
				Thread.sleep(1); 
			}

		}catch(Exception e) 
		{System.out.println("Spinner Error Msg"+e.getMessage());}
	}
	
	/*----------------------------------------------------------------------------
   	Function Name    	: waitForSpinner
   	Description     	: waitForSpinner
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static void waitSpinnerForBPMWindow() throws InterruptedException {

		// By findBy = By.cssSelector("fusePlus-container x1lq p_AFMaximized");
		try {
			//WebElement findBy=driver.findElement(By.cssSelector(".x1lq.p_AFMaximized"));
			By findBy = By.cssSelector(".x122.p_AFMaximized");
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
			String bottom_cssValue = element.getAttribute("style");
			// System.out.println("CSS Value - "+bottom_cssValue);
			while(bottom_cssValue.equals("cursor: wait;"))
			{
				bottom_cssValue = element.getAttribute("style");
				// System.out.println("CSS Value In Loop -"+bottom_cssValue);
				Thread.sleep(1); 
			}

		}catch(Exception e) 
		{System.out.println("Spinner Error Msg"+e.getMessage());}
	}
	
	/*----------------------------------------------------------------------------
  	Function Name    	: ng_CheckDataInTextBox
  	Description     	: to check if element have data
  	Input Parameters 	: 
  	Return Value    	: 
  	Author		        : 
  	Date of creation	:
	Date of modification:
  	----------------------------------------------------------------------------*/
	public static String ng_CheckDataInTextBox(WebElement element) throws Exception {
		//String strVal = getTestDataValue(strKey); 
		String value="";
		if ((Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {			
			waitForPageToLoad();		
			WebElement objElement = wait.until(ExpectedConditions.visibilityOf(element));					
			if(Global.gstrHighlighter == true) {
				highLighterMethod(objElement);
			}
			value = objElement.getText();
			for(int i=0;i<5;i++)
			{
			if(value!=null)
			{
					break;
			}
			else
			{
				Utility.ng_waitImplicitly(1);
				wait.until(ExpectedConditions.visibilityOf(element));	
			}
			}

		  } catch (Exception e) {
			String strDesc = "<span style='color:#ff4d4d;'>'" + "' WebElement does not exist.<span style='font-weight:bold;'></span>";
			System.out.println(e.getMessage());
			ng_ResultLogFail(strDesc);

		 } 		
		return null;
	}
	/*----------------------------------------------------------------------------
	Function Name    	: ng_DropDownSearchAndSelect
	Description     	: This function is to click on search and select the value		
	Return Value    	: 
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_WebTableWaitForMinRows(WebElement ele, int rowCount,String strKey) throws Exception
	{
		String strVal = getTestDataValue(strKey );
		String retVal  = "";
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) 
		{
			return String.valueOf(true);
		}

		boolean found = false;
		int trialInd = 0;		
		Global.setMaxAttemptsTo(Global.HALF_MIN_ATTEMPTS);
		while(trialInd<=Global.ATTEMPTS && !found)
		{
			try
			{	
						
				ele = explicitWait2(ele, Global.MIN_WAIT_TIME_S);				
				List<WebElement> rows = ele.findElements(By.tagName("tr"));
				if(rows!=null && rows.size()>=rowCount)
				{
					retVal = "" + rows.size();
					found=true;
					System.out.println("Number of rows found:" + rows.size());
					break;
				}
			}
			catch(Exception e)
			{
				System.out.println("Trial " + (trialInd+1) + " ");
				try 
				{
					Thread.sleep(Global.MIN_WAIT_TIME_MS);

				} 
				catch (Exception ie) 
				{
					ie.printStackTrace();
				}
			}
			trialInd++;
		}
		
		Global.setDefaultMaxAttempts();
		if(!found)
		{
			try {
				throw new Exception("Expected number of rows did not appear in the table, kindly check the results.");
			}
			catch (Exception e) {
				printError(e);
			}
		}
			return retVal;
	}

	/*----------------------------------------------------------------------------
	Function Name    	: ng_DropDownSearchAndSelect
	Description     	: This function is to click on search and select the value		
	Return Value    	: 
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_DropDownSimpleSearchAndSelect(String srchCol,String strKey) throws Exception
	{
		String strVal = getTestDataValue(strKey ); 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) 
		{
			return String.valueOf(true);
		}

		try
		{


			String openSearchXPath="((//label[text()='"+srchCol+"']/following::input)[1]/following::a)[1]";
			//WebElement btnOpenSearch=driver.findElement(By.xpath(openSearchXPath));
			WebElement btnOpenSearch =explicitWait(By.xpath(openSearchXPath), Global.MIN_WAIT_TIME_S);

			String xpath = btnOpenSearch.getAttribute("id");
			xpath = xpath.substring(0,xpath.lastIndexOf("::"));
			ng_clickSimply(btnOpenSearch, srchCol, strKey);

			String lnkSearchXPath ="//div[@id='"+xpath+"::dropdownPopup::content']//a[text()='Search...']";
			//WebElement lnkSearchLOV=driver.findElement(By.xpath(lnkSearchXPath));
			WebElement lnkSearchLOV = explicitWait(By.xpath(lnkSearchXPath),Global.MAX_WAIT_TIME_S);
			ng_clickWebElement(lnkSearchLOV, srchCol, strKey);

			String edtPopupSearchFieldXPath = "//div[contains(@id,'popup-container')]//label[text()='"+srchCol+"']/following::input[1] ";
			//WebElement edtPopupSearchField=driver.findElement(By.xpath(edtPopupSearchFieldXPath));
			WebElement edtPopupSearchField=explicitWait(By.xpath(edtPopupSearchFieldXPath),Global.MAX_WAIT_TIME_S);
			ng_enterText(edtPopupSearchField, srchCol, strKey);

			String btnPopupSearchXPath = "//div[contains(@id,'popup-container')]//button[text()='Search']";
			//WebElement btnPopupSearch=driver.findElement(By.xpath(btnPopupSearchXPath));
			WebElement btnPopupSearch=explicitWait(By.xpath(btnPopupSearchXPath),Global.MAX_WAIT_TIME_S);
			ng_clickWebElement(btnPopupSearch, "Search", strKey);

			String tdSearchResultsXPath = "((//div[contains(@id,'popup-container')]//table[contains(@summary,'This table contains')])[1]/parent::div/following-sibling::div//tr//td)[1]";
			//WebElement tdSearchResults=driver.findElement(By.xpath(tdSearchResultsXPath));
			WebElement tdSearchResults=explicitWait(By.xpath(tdSearchResultsXPath),Global.MAX_WAIT_TIME_S);
			ng_clickWebElement(tdSearchResults, "Search Results", strKey);

			String btnPopupOKXPath = "//div[contains(@id,'popup-container')]//button[text()='OK']";
			//WebElement btnPopupOK=driver.findElement(By.xpath(btnPopupOKXPath));
			WebElement btnPopupOK=explicitWait(By.xpath(btnPopupOKXPath),Global.MAX_WAIT_TIME_S);
			ng_clickWebElement(btnPopupOK, "OK", strKey);

			String edtSearchXPath="//label[text()='"+srchCol+"']/preceding::td/following::input[1]";			
			//WebElement edtSearch=driver.findElement(By.xpath(edtSearchXPath));
			WebElement edtSearch = explicitWait(By.xpath(edtSearchXPath), Global.MIN_WAIT_TIME_S);

		}
		catch(Exception e)
		{
			printError(e);
		}
		return "";
	}
	/*----------------------------------------------------------------------------
   	Function Name    	: explicitWait
   	Description     	: explicit Wait
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static WebElement explicitWait(By by, long timeout) throws Exception {	

		long startTime = 0;
		long endTime = 0;
		int trialInd = 0;
		boolean status = false;
		By locator = null;
		WebElement element = null;

		startTime = System.currentTimeMillis();

		WebDriverWait wait = new WebDriverWait(driver, Global.MAX_WAIT_TIME_S);
		while( !status && trialInd<Global.ATTEMPTS)
		{
			try 
			{				
				wait = new WebDriverWait(driver, Global.MAX_WAIT_TIME_S);
				element = wait.until(ExpectedConditions.elementToBeClickable(by));	
				status = true;
				break;
			}
			catch(Exception e)
			{
				System.out.println("Trial "+ (trialInd+1) + " failed to wait for element.");
				try
				{
					Thread.sleep(Global.MIN_WAIT_TIME_MS);
					trialInd++;
				}
				catch(Exception ie)
				{
					System.out.println("Un expected error check with administrator.");
				}
			}
		}
		if(!status)
		{
			throw new Exception("Object not found");
		}
		endTime = System.currentTimeMillis();	
		return element;

	}
	public static void printError( Exception e) throws Exception
	{
		String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'>Failed to get the text in file &nbsp"+ e.getMessage()+"</span>";
		//e.printStackTrace();
		writeHTMLResultLog(strDesc, "fail");
		takeScreenShotAndLog("fail");
		Global.bResult = "False";
		Global.objErr = "11";		
	}
	/*----------------------------------------------------------------------------
	Function Name    	: ng_ClickRefreshButtonAndFetchStatus
	Description     	: This function Display status of the Process		
	Return Value    	: 
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_ClickRefreshButtonAndFetchProcStatus(WebElement RefrshButton,WebElement lnkProcessStatus,String strLabel) throws Exception
	{	
		
		if (Global.objErr == "11") {
			return String.valueOf(true);
		}
		int counter=0;
		String status="";	

		try {
			Utility.waitForPageToLoad();				
			Utility.waitForSpinner();
			if(Global.gstrHighlighter == true) {
				highLighterMethod(lnkProcessStatus);
			}
			Global.gstrReadfromTestData = false;
			for(int i=0;i<50;i++)
			{
				Utility.waitForSpinner();
				Utility.ng_clickWebElement(RefrshButton, "Refresh", "RefreshClick");					
				Utility.waitForSpinner();
				status=lnkProcessStatus.getText();
				Utility.waitForSpinner();
				if(status.equalsIgnoreCase("Warning")||status.equalsIgnoreCase("Error")||status.equalsIgnoreCase("Succeeded"))
				{
					System.out.println(status);					
					break;			
				}				
				Utility.ng_waitImplicitly(Global.Loop_Control_Wait);					
				counter++;
				System.out.println("Number of time loop ran="+counter);
			}
			Global.gstrReadfromTestData = true;
			System.out.println("status");
			Utility.waitForSpinner();
			Utility.ng_getElementText(lnkProcessStatus, "Process Status", "GetProcessStatus");
			Utility.waitForSpinner();
			Utility.ng_assertValue(status,"ExpProcessStatus");
			String strDesc = "Successfully clicked on '" + "Process Status" + "'  WebElement";			
			Utility.writeHTMLResultLog(strDesc, "pass");
			takeScreenShotAndLog("pass");			
			Global.bResult = "True";


		}
		catch(Exception e)
		{
			String exception=e.getMessage();
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)=='}' || exception.charAt(i)==')')
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			ng_ResultLogFail(strDesc);
		}

		return Global.bResult;
	}

	/*----------------------------------------------------------------------------
  	Function Name    	: ng_getNumberAtAnyPosition
  	Description     	: to get Number at any position from Long String.  
  	----------------------------------------------------------------------------*/
	public static String ng_getNumberAtAnyPosition(String getString,int indx) throws Exception {		
		List<String> ListOfNumbers=new ArrayList<String>();
		if ((Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {									
			int strLength =getString.length();
			String FinalString="";
			if(getString!=null)
			{
			if(getString.charAt(strLength-1)=='.')
			FinalString=getString.substring(0, strLength-1);
			System.out.println("Final string="+FinalString);			
			String StrWthNumAndNullValue=FinalString.replaceAll("[a-zA-Z]"," ").trim();
			String[] NubLstWithNullValue=StrWthNumAndNullValue.split(" ");		

			for(int i=0;i<NubLstWithNullValue.length;i++)
			{
				if(NubLstWithNullValue[i].equals(""))
					continue;
				else
					ListOfNumbers.add(NubLstWithNullValue[i]);							
			}			
		  }
							
		    } catch (Exception e) {		
			
			String strDesc = "<span style='color:#ff4d4d;'>'" + "' Number does not exist.<span style='font-weight:bold;'></span>";
			System.out.println(e.getMessage());
			ng_ResultLogFail(strDesc);
			
		} 		
		return ListOfNumbers.get(indx);
	}
	/*----------------------------------------------------------------------------
	Function Name    	: ng_printResultLogMessage
	Description     	: This function is to print result confirmation message in the log file		
	Return Value    	: 
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static void ng_printResultLogMessage(String strKey) throws Exception
	{
		if(Global.objErr=="11")
		{

		}
		else
		{
			Global.test.log(Status.INFO,"<span style='color:#4CAF50;'>"+strKey+"</span>");
		}
		//Project Manager
		//Payables Supervisor
	}
	public static boolean objExists(By by) throws Exception
	{
		boolean found = true;
		
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, Global.MAX_WAIT_TIME_S);
			wait = new WebDriverWait(driver, Global.MAX_WAIT_TIME_S);
			WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(by));			
		}
		catch(Exception e)
		{
			found = false;
		}
		return found;
	}
	/*----------------------------------------------------------------------------
	Function Name    	: clickWebElement
	Description     	: This function clicks the WebElement object
	Input Parameters 	: strObject - Object Name of Web Element
						: strLabel - To be printed on extent report
	                    : strKey - Paramiter name to get the data value from TestData Table
	                    : optional - there are cases where we want to have a click on an optional object.
	Return Value    	: bResult
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_clickWebElement(WebElement element, String strLabel, String strKey,boolean optional) throws Exception {
		String strVal = getTestDataValue(strKey);
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		if(optional) Global.setMaxAttemptsToMin();
		try {		
			waitForPageToLoad();
			element = explicitWait2(element,20);

			ng_scrollIntoViewElement(element, strLabel);			
			ng_waitForElementEnabled(element,20);

			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}
			String onClickScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('click',true, false);arguments[0].dispatchEvent(evObj);} else if(document.createEventObject){ arguments[0].fireEvent('onclick');}";
			if (element.getAttribute("onclick")!=null) {
				element.click();
			}			
			else {
				js.executeScript(onClickScript,element);
			}	
			waitForPageToLoad();
			String strDesc = "Successfully clicked on '" + strLabel + "'  WebElement";			
			writeHTMLResultLog(strDesc, "pass");
			takeScreenShotAndLog("pass");			
			Global.bResult = "True";
		} catch (Exception e) {
			//char specialChar='}';
			String exception=e.getMessage();
			if(optional)
			{
				String strDesc = "Skipped click on '" + strLabel + "'  WebElement as it may or may not appear";			
				writeHTMLResultLog(strDesc, "pass");
				takeScreenShotAndLog("pass");			
				Global.bResult = "True";
				Global.setDefaultMaxAttempts();
				return Global.bResult;
			}
			String FinalException="";
			for(int i=0;i<exception.length();i++)
			{
				FinalException=FinalException+exception.charAt(i);
				if(exception.charAt(i)=='}' || exception.charAt(i)==')')
				{
					break;
				}
			}				
			String strDesc = "<span style='color:#ff4d4d;'>'" + strLabel + "' WebElement does not exist.<span style='font-weight:bold;'> Error Message </span>:"+FinalException+"</span>";
			//String strDesc = "<span style='color:#ff4d4d;'>WebElement '" + strLabel +  "' is not displayed on the screen." + "</span>"; //"<span style='color:#ff4d4d;'>WebElement " + strLabel +  "' is not displayed on the screen. Error Message : " + e.getMessage()+"</span>"
			Utility.writeHTMLResultLog(strDesc, "fail");
			//Global.test.log(LogStatus.INFO,"<span style='font-weight:bold;color:#1ff3f5;'>Test Execution Finished</span>");
			Utility.takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		} 							
		Global.setDefaultMaxAttempts();
		return Global.bResult;
	}
	/*----------------------------------------------------------------------------
   	Function Name    	: explicitWait
   	Description     	: explicit Wait
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static WebElement explicitWait2(WebElement elementID, long timeout) throws Exception {	

		long startTime = 0;
		long endTime = 0;
		int trialInd = 0;
		boolean status = false;
		By locator = null;

		startTime = System.currentTimeMillis();

		WebDriverWait wait = new WebDriverWait(driver, Global.MAX_WAIT_TIME_S);

		while( !status && trialInd<Global.ATTEMPTS)
		{
			try 
			{
				locator = getByFromWebElement(elementID);
				wait = new WebDriverWait(driver, Global.MAX_WAIT_TIME_S);
				elementID = wait.until(ExpectedConditions.elementToBeClickable(locator));	
				status = true;
				break;
			}
			catch(Exception e)
			{
				System.out.println("Trial "+ (trialInd+1) + " failed to wait for element.");
				try
				{
					Thread.sleep(Global.MIN_WAIT_TIME_MS);
					trialInd++;
				}
				catch(Exception ie)
				{
					System.out.println("Un expected error check with administrator.");
				}
			}
		}
		if(!status)
		{
			throw new Exception("Object not found");
		}
		endTime = System.currentTimeMillis();	
		return elementID;

	}
	
	public static void ng_scrollByPixelsFromObject(WebElement element,int pixels) throws Exception {
		try {
				int startLoc = 0;
				if(element.isDisplayed())
					startLoc = element.getRect().getY() + element.getRect().getHeight();
					
				((JavascriptExecutor) driver).executeScript("window.scrollBy(0,"+(startLoc+pixels)+")");
				Thread.sleep(Global.MIN_WAIT_TIME_MS);
		} catch (Exception e) {

		}
	}	
}
