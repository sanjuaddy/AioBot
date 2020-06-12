package lib;

import java.io.File;



import java.util.HashMap;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

//import com.relevantcodes.extentreports.ExtentReports;
//import com.relevantcodes.extentreports.ExtentTest;



public class Global {
	public static String gURL;
    public static String gTCName;
    public static String gTCID;
    public static String gTCGroupName;
    public static String gTCDescription;
    public static String gTCClientTestId;
    public static String gTCUserBuRole;
    public static String gTCUserName;
    public static String gReps;
    public static int gRepsNo;
    public static String gTCInstance;
    public static String gTCHostName;
	public static String gPatchVersion;								   
    public static String bResult;
    public static String gSummaryFileNewOld;
    public static String objErr = "0";
    public static String objSoftAssert ="true";  // true for 
    public static String filePath;
    public static String gstrComponentName;
    public static ExtentReports report;
    public static ExtentTest test;
    public static ExtentHtmlReporter htmlReports;
    public static String gstrTestNgXmlLocation = System.getProperty ("user.dir")+ File.separator + "AIO_TestConfiguration"+File.separator+"testng.xml";
    public static String gstrControlFilesDir = System.getProperty ("user.dir")+ File.separator;
    public static String gstrBatchFilesDir = System.getProperty ("user.dir") + File.separator+"AIO_TestConfiguration"+File.separator;
    public static String gstrTestDataDir = System.getProperty ("user.dir") + File.separator+"AIO_TestData"+File.separator;

    public static String gstrTestResultLogDir;
    //public static String gstrTestResultLogDir = System.getProperty ("user.dir")+ File.separator + "TestRunResults"+File.separator+"TestResultLog";
    public static String gstrScreenshotsDir;
    //public static String gstrScreenshotsDir = System.getProperty ("user.dir")+ File.separator + "TestRunResults"+File.separator+"Screenshots";
    public static String gstrACTResultExcelFilePath;
    public static String gstrACTResultExcelOverWriteFilePath;
    
    public static String gstrExtentConfigDir = System.getProperty ("user.dir")+ File.separator + "AIO_TestConfiguration";
    public static String gstrChromeDriverDir = System.getProperty ("user.dir") + File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"Selenium_Jars"+File.separator+"chromedriver_win32"+File.separator+"chromedriver.exe";
    public static String gstrDriverDir = System.getProperty ("user.dir") + File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"Selenium_Jars"+File.separator+"chromedriver_win32";

    //public static String gstrChromeDriverDir = System.getProperty ("user.dir") + File.separator+"Resources"+File.separator+"Selenium_Jars"+File.separator+"chromedriver_win32"+File.separator+"chromedriver.exe";
    //public static String gstrDriverDir = System.getProperty ("user.dir") + File.separator+"Resources"+File.separator+"Selenium_Jars"+File.separator+"chromedriver_win32";
    public static HashMap objData;
    public static String gstrTimesTamp;
    public static String gstrClassName;
    public static String gstrMethodName;    
    public static Boolean gstrHighlighter = true;
    
    public static String downloadPath = "C:\\Users\\skadhikarla\\Downloads";
    public static int excelSceetCount;
    public static int pdfPageCount;
    
    //TestData controller  - from TestData excel file on variable      
    public static Boolean gstrReadfromTestData = true;  //true : TestData,   false : Variable
       
    public static String gstrEmailUserName;
    public static String gstrEmailPassword;
    public static String gstrRecipient;
    public static String gstrBrowser;
    public static int count=1;
    //public static int rowid=0;     
    public static String gstrReportFileName;
    
    public static Boolean gstrScreenShotFlag;
    //Email Configuraion
    public static Boolean gstrSendEmail;			//true : send email, false : won't send
	public static String gstrEmailMode = "gmail";	//outlook , gmail
	
	public static Boolean waitImplicitlyFlag=false;
	public static int MIN_ATTEMPTS = 12;
	public static int MAX_ATTEMPTS = 70;
	public static int ATTEMPTS = 50;
	public static int HALF_MIN_ATTEMPTS= MIN_ATTEMPTS/2;
	public static int HALF_MAX_ATTEMPTS= MAX_ATTEMPTS/2;
	
	public static int MIN_WAIT_TIME_MS = 800;
	public static int MIN_WAIT_TIME_S = 2;
	public static int MAX_WAIT_TIME_MS = 2500;
	public static int MAX_WAIT_TIME_S =2;
	public static int ATTEMPTS_FOR_2_MINS = 120;
	
	public static int WAIT_FOR_PAGE_DEF = 120;
	public static int WAIT_FOR_PAGE_MAX = 280;
	public static int WAIT_FOR_PAGE_MIN = 70;
	public static int WAIT_FOR_PAGE = 60;
	public static int Loop_Control_Wait = 6;
	
	public static int randSize = 3;
	
	public static void setMaxPageWait()
	{
		setPageWaitTime(WAIT_FOR_PAGE_MAX);
	}	
	public static void setMinPageWait()
	{
		setPageWaitTime(WAIT_FOR_PAGE_MIN);
	}
	public static void setDefaultPageWait()
	{
		setPageWaitTime(WAIT_FOR_PAGE_DEF);
	}
	public static void setPageWaitTime(int timeInSeconds)
	{
		WAIT_FOR_PAGE  = timeInSeconds;
	}
	public static void readDataFromXL()
	{
		gstrReadfromTestData = true;
	}
	public static void donotReadDataFromXL()
	{
		gstrReadfromTestData = false;
	}
	
	public static void setMaxAttemptsTo(int attempts)
	{
		ATTEMPTS = attempts;
	}
	
	public static void setMaxAttemptsToMin()
	{
		setMaxAttemptsTo(MIN_ATTEMPTS);
	}
	
	
	public static void setDefaultMaxAttempts()
	{
		setMaxAttemptsTo(MAX_ATTEMPTS);
	}
       
}
