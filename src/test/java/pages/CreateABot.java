package pages;

import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;

import lib.Global;
import lib.TestData;
import lib.Utility;

public class CreateABot {


	public static String sDesc="";
	public static String strPersonNumber="";
	public static String strActual;

	@FindBy(xpath="(//div[contains(text(),'@gmail')])[1]")
	WebElement divTriggerMail;
	@FindBy(xpath="(//div[contains(text(),'@gmail')])[2]")
	WebElement divActionMail;
	@FindBy(xpath="(//span[contains(text(),'New Email')])[1]")
	WebElement spanNewEmail;
	@FindBy(xpath="(//button[@data-toggle='dropdown'])[2]")
	WebElement btnFolderLabelDrop;	
	@FindBy(xpath="//div/span[contains(text(),'INBOX')]")
	WebElement spanInbox;
	@FindBy(xpath="(//span[@class='caret'])[4]")
	WebElement spanActionApp;
	@FindBy(xpath="//div[@title='Send an Email']")
	WebElement divSendEmail;	
	@FindBy(xpath="(//button[@title='Add an output field']/i)[2]")
	WebElement btnFromName;
	@FindBy(xpath="(//span[contains(text(),'From Name')])[2]")
	WebElement spanFromName;
	@FindBy(xpath="(//button[@title='Add an output field']/i)[3]")
	WebElement btnFromAddress;
	@FindBy(xpath="(//span[contains(text(),'From Email')])[3]")
	WebElement spanFromEmail;
	@FindBy(xpath="(//div[@contenteditable='true'])[4]")
	WebElement edtToName;
	@FindBy(xpath="(//div[@contenteditable='true'])[5]")
	WebElement edtToAddresses;
	@FindBy(xpath="(//button[@title='Add an output field']/i)[9]")
	WebElement btnSubject;	
	@FindBy(xpath="(//span[contains(text(),'Subject')])[9]")
	WebElement spanSubject;
	@FindBy(xpath="(//button[@title='Add an output field']/i)[10]")
	WebElement btnEmailBody;	
	@FindBy(xpath="(//span[contains(text(),'Body')])[19]")
	WebElement spanEmailBody;	
	@FindBy(xpath="//li[contains(text(),'My Bots')]/following::a[1]")
	WebElement btnSave;	
	@FindBy(xpath="//span[@class='disc']")
	WebElement spanDisc;	
	@FindBy(xpath="//h3[contains(text(),'Test using live data')]/following::button[1]")
	WebElement btnIMDone;
	@FindBy(xpath="//a[contains(text(),'Go to Bot list')]")
	WebElement lnkBotList;
	
	public CreateABot(Utility util) throws Exception{
		//this.driver = driver;    	
		PageFactory.initElements(util.ng_returnDriver(), this);
	}  


	/*----------------------------------------------------------------------------
    Function Name    	: createBot
    Description     	: Verify creating a Bot 
    Author				:     
    Date of creation	:
	Date of modification:
    ----------------------------------------------------------------------------*/ 
	public void createBot() throws Exception {    	

		if(Global.objErr == "11"){
			return;
		}    
		try {
			TestData td = new TestData ();
			Global.objData = (HashMap) td.readTestData (Global.gTCID, Global.gstrClassName, Global.gstrMethodName);	        	    
			Global.test.log(Status.INFO,"Class and Method : "+ Global.gstrClassName +" . "+Global.gstrMethodName);
			Global.gstrHighlighter = false;
			Utility.waitForPageToLoad();
			Utility.ng_clickWebElement(divTriggerMail, "From Gamail ID", "FromGmailClick");    	    
			Utility.ng_clickWebElement(spanNewEmail, "New Email", "TriggerEventClick");
			Utility.ng_clickWebElement(btnFolderLabelDrop, "Folder Label Dropdown", "FolderLabelClick");	    
			Utility.ng_clickWebElement(spanInbox, "Inbox", "InboxClick");	    
			Utility.ng_clickWebElement(spanActionApp, "Action App", "ActionAppClick");	
			Utility.ng_clickWebElement(divActionMail, "Action Email", "ActionEmailClick");
			Utility.ng_clickWebElement(divSendEmail, "Send An Email", "SendEmailClick");
			Utility.ng_clickWebElement(divSendEmail, "Send An Email", "SendEmailClick");
			Utility.ng_clickWebElement(btnFromName, "From Name Icon", "FromNameClick");
			Utility.ng_clickWebElement(spanFromName, "From Name", "FromNameClick");
			Utility.ng_clickWebElement(btnFromAddress, "From Address Icon", "FromAddressClick");
			Utility.ng_clickWebElement(spanFromEmail, "From Email", "FromEmailClick");
			Utility.ng_typeAndTab(edtToName, "To Name", "ToNameSet");
			Utility.ng_typeAndTab(edtToAddresses, "To Addresses", "ToAddressSet");
			Utility.ng_clickWebElement(btnSubject, "Subject Icon", "SubjectClick");
			Utility.ng_clickWebElement(spanSubject, "Subject", "SubjectClick");
			Utility.ng_clickWebElement(btnEmailBody, "Email Body Icon", "EmailBodyClick");
			Utility.ng_clickSimply(spanEmailBody, "Email Body", "EmailBodyClick");
			Utility.ng_clickWebElement(btnSave,"Save", "SaveClick");
			
			if(btnSave.isDisplayed()){
				Utility.ng_clickWebElement(btnEmailBody, "Email Body Icon", "EmailBodyClick");
				Utility.ng_clickSimply(spanEmailBody, "Email Body", "EmailBodyClick");
				Utility.ng_clickWebElement(btnSave,"Save", "SaveClick");
			}
			Utility.waitForPageToLoad();
			Utility.ng_clickWebElement(spanDisc,"On Off Disc", "OnOffClick");
			Utility.ng_clickWebElement(btnIMDone,"I'm Done", "DoneClick");
			Utility.ng_clickWebElement(lnkBotList,"Go to Bot list", "BotListClick");

		}

		catch (Exception e) {
			Global.objErr = "11";
			Utility.ng_ScriptAbortedLog(e);
		}  
	}
}
