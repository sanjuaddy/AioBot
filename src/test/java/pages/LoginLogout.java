package pages;

import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aventstack.extentreports.Status;

import lib.Global;
import lib.TestData;
import lib.Utility;

public class LoginLogout {    
	
	
	@FindBy(xpath="//a[contains(text(),'Login')]")
    WebElement lnkLogin; 
	@FindBy(xpath="//a[contains(text(),'LOGIN')]")
    WebElement lnkLogIn; 
	@FindBy(xpath="//input[@id='email']")
    WebElement edtUserName;    
    @FindBy(xpath="//input[@id='password']")
    WebElement edtPassword;    
    @FindBy(xpath="//button[@type='submit']")
    WebElement btnLogIn;    
    @FindBy(xpath="//img[@class='profile-image']")
    WebElement imgProfile;      
    @FindBy(xpath="//a[contains(text(),'Logout')]")
    WebElement lnkLogOut;        
    @FindBy(xpath="//a[contains(text(),'Create a Bot')]")
    WebElement lnkCreateBot;
    
    
  //HR
	@FindBy(xpath="//a[text()='My Client Groups']")
	WebElement lnkClientGroup;
	@FindBy(xpath="//a[text()='Workforce Structures']")
	WebElement lnkWorkStructure;
	@FindBy(xpath="//a[text()='New Person']")
	WebElement lnkNewPerson;
	@FindBy(xpath="(//a[text()='Person Management'])[1]")
	WebElement lnkPersonManagement;
	
    
    public LoginLogout(Utility util) throws Exception{
        PageFactory.initElements(util.ng_returnDriver(), this);        
    }
    /*----------------------------------------------------------------------------
    Function Name    	: applicationURL
    Description     	: This function used to launch the application URL.    
    Author				:  
    Date of creation	:
	Date of modification:   
    ----------------------------------------------------------------------------*/        
    public void instanceURL( ) throws Exception {    	
        if(Global.objErr == "11"){
            return;
        }
    	try {    	
    		Global.test.log(Status.INFO,"<span style='font-weight:bold;color:#6dd8ca;'>Test Execution Started</span>");
    		Global.test.log(Status.INFO,"Class and Method : "+ Global.gstrClassName +" . "+Global.gstrMethodName);
    		Utility.ng_invokeBrowser("URL");
			
			Utility.ng_waitImplicitly(2);
		} catch (Exception e) {
			Utility.ng_ScriptAbortedLog(e);			
		}		    
    }
    
    /*----------------------------------------------------------------------------
    Function Name    	: login
    Description     	: This function used to login to the application. 
    Author				:  
    Date of creation	:
	Date of modification:   
    ----------------------------------------------------------------------------*/ 
    public void login() throws Exception {
        if(Global.objErr == "11"){
            return;
        }   
    	try {
			TestData td = new TestData ();
			Global.objData = (HashMap) td.readTestData (Global.gTCID, Global.gstrClassName, Global.gstrMethodName);	    			
			Global.test.log(Status.INFO,"Class and Method : "+ Global.gstrClassName +" . "+Global.gstrMethodName);    
			Utility.waitForPageToLoad();
			Utility.ng_clickSimply(lnkLogin,"Login","LogInClick");
			Utility.waitForPageToLoad();
			Utility.ng_clickSimply(lnkLogIn,"LOGIN","LogInClick");
			Utility.ng_verifyPage("Login","LoginCheck");
			Global.gTCUserName = Utility.getTestDataValue("UserNameSet");
			Utility.ng_enterTextDirect(edtUserName,"User Name","UserNameSet");
			Utility.ng_enterTextPwd(edtPassword,"Password","PasswordSet");
			Utility.ng_clickSimply(btnLogIn,"LOGIN","LogInClick");	
			
		} catch (Exception e) {
			Utility.ng_ScriptAbortedLog(e);				
		}     
    }
    
    /*----------------------------------------------------------------------------
    Function Name    	: logout
    Description     	: This function used to logout the application.  
    Author				:  
    Date of creation	:
	Date of modification:
    ----------------------------------------------------------------------------*/
    public void logout() throws Exception {
		if(Global.objErr == "11"){			
		    return;
		} 
		try {
			Global.test.log(Status.INFO,"Class and Method : "+ Global.gstrClassName +" . "+Global.gstrMethodName);
			Utility.waitForPageToLoad();
			Global.donotReadDataFromXL();
			Utility.ng_clickWebElement(imgProfile, "User Name", "UserNameClick");
			Utility.ng_clickUsingActions(lnkLogOut,"Logout","SignOffClick");	
			Global.setMaxAttemptsToMin();
			Global.setDefaultMaxAttempts();
			Global.readDataFromXL();
			Utility.driver.quit();
		} catch (Exception e) {
			Utility.ng_ScriptAbortedLog(e);				
		}		   	    
	}
    
    /*----------------------------------------------------------------------------
    Function Name    	: GoToHome
    Description     	: This function used to navigate to home page.     
    Author				:
    Date of creation	:
	Date of modification:
    ----------------------------------------------------------------------------*/ 
    public void homePage() throws Exception {    	
        if(Global.objErr == "11"){
            return;
        }    
        try {
        	TestData td = new TestData ();
    	    Global.objData = (HashMap) td.readTestData (Global.gTCID, Global.gstrClassName, Global.gstrMethodName);	    
    	    Global.test.log(Status.INFO,"Class and Method : "+ Global.gstrClassName +" . "+Global.gstrMethodName);
    	    
    	    String strVal = Utility.getTestDataValue("RoleGet");
    	    Global.gTCUserBuRole=strVal;
     	 	Global.test.log(Status.INFO,"<span style='color:#c3ac39;'>Successfully logged in with business role</span><span style='font-weight:bold;color:#c3ac39';> '"+strVal+"'</span>");
    	    Utility.waitForPageToLoad();
            Utility.ng_verifyPage("Recommended Bots","HomeCheck");//lnkNavigator1
            Utility.waitForPageToLoad();
            Utility.ng_clickWebElement(lnkCreateBot,"Create a Bot","CreateBot");
            Utility.waitForPageToLoad();
            Utility.ng_waitImplicitly(5);
            
            
		} catch (Exception e) {
			Utility.ng_ScriptAbortedLog(e);			
		}       
    } 
    
        
}
