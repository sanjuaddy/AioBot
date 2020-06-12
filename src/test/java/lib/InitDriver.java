package lib;

import java.io.File;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public class InitDriver
{
    // static variable single_instance of type Singleton
    private static InitDriver driver_instance = null;
    public static WebDriver driver=null;	 
    private InitDriver(String browser) throws IOException {
	    	
        try {
            if (browser.equalsIgnoreCase("Firefox")) {            	
            	
            	System.setProperty("webdriver.gecko.driver", Global.gstrDriverDir+"\\geckodriver.exe");
            	driver = new FirefoxDriver();
            	driver.manage().window().maximize();
            	
            	//WebDriver driver=new FirefoxDriver();	
            	
            	
            } else if (browser.equalsIgnoreCase("Chrome")) {            	
            	/*ChromeOptions options = new ChromeOptions();
            	Map<String, Object> prefs = new HashMap<String, Object>();
            	prefs.put("profile.default_content_setting_values.notifications", 2);
            	options.setExperimentalOption("prefs", prefs);
            	//options.addArguments("--disable-popup-blocking");
            	options.addArguments("chrome.switches","--disable-extensions");
            	options.addArguments("chrome.switches","--disable-notifications");
            	options.addArguments("chrome.switches","--disable-infobars");
            	//options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            	options.addArguments("start-maximized");            	
            	//options.addArguments("test-type");*/
                System.setProperty("webdriver.chrome.driver",Global.gstrChromeDriverDir);
                driver = new ChromeDriver();
                driver.manage().window().maximize();
                
            }
            
            else if (browser.equalsIgnoreCase("IE")) {            	
            	/*ChromeOptions options = new ChromeOptions();
            	Map<String, Object> prefs = new HashMap<String, Object>();
            	prefs.put("profile.default_content_setting_values.notifications", 2);
            	options.setExperimentalOption("prefs", prefs);
            	//options.addArguments("--disable-popup-blocking");
            	options.addArguments("chrome.switches","--disable-extensions");
            	options.addArguments("chrome.switches","--disable-notifications");
            	options.addArguments("chrome.switches","--disable-infobars");
            	//options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            	options.addArguments("start-maximized");            	
            	//options.addArguments("test-type");*/
            	DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            	capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
            	System.setProperty("webdriver.ie.driver", Global.gstrDriverDir+"\\IEDriverServer.exe");
        		driver = new InternetExplorerDriver();
        		driver.manage().window().maximize();
            }
//            Global.gstrTimesTamp = "_"+ Utility.getCurrentDatenTime("dd-MM-yy")+"_"+ Utility.getCurrentDatenTime("H-mm-ss a");
//            Global.filePath = Global.gstrTestResultLogDir + "\\" + Global.gTCName + Global.gstrTimesTamp  +".html";
//            File myFile = new File(Global.filePath);
//            if (! myFile.exists() ) {
//                myFile.createNewFile();
//                Global.report = new ExtentReports(Global.filePath, true);
//                Global.report.loadConfig(new File(Global.gstrExtentConfigDir + "\\extent-config.xml"));
//            }
        } catch (WebDriverException e) {
            System.out.println(e.getMessage());
        }
       // Global.test = Global.report.startTest(Global.gTCName,Global.gTCDescription);
          Global.test = Global.report.createTest(Global.gTCName,Global.gTCDescription);
       
    }
 
    // static method to create instance of Singleton class
    public static InitDriver getInstance(String drivername) throws IOException {
        try {
			if (driver.toString().contains(null)){					
				driver_instance = new InitDriver(drivername);
			}
		} catch (NullPointerException e) {
			
			driver_instance = new InitDriver(drivername);
		}
        catch (NoSuchSessionException e){
        	driver_instance = new InitDriver(drivername);
        }	
        return driver_instance;
    }
}
