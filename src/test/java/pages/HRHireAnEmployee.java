package pages;

import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;

import lib.Global;
import lib.TestData;
import lib.Utility;

public class HRHireAnEmployee {


	public static String sDesc="";
	public static String strPersonNumber="";
	public static String strActual;

	@FindBy(xpath="(//a[@title='Home'])[2]")
	WebElement imgHome;
	@FindBy(xpath="//a[text()='My Client Groups']")
	WebElement lnkMyClientGroups;
	@FindBy(xpath="//a[text()='New Person']")
	WebElement lnkNewPerson;	
	@FindBy(xpath="//a[@title='Navigator']")
	WebElement lnkNavigator;
	//@FindBy(xpath="//td/a[contains(text(),'New Person')]")
	//WebElement lnkNewPerson;
	//@FindBy(xpath="(//div/a[contains(text(),'New Person')])[2]")
	//WebElement btnNewPerson;
	@FindBy(xpath="//img[@title='Tasks']")
	WebElement imgTasks;
	@FindBy(xpath="//a[text()='Hire an Employee']")
	WebElement lnkHireEmployee;	
	@FindBy(xpath="//label[text()='Hire Action']//following::input[1]//following::a[1]")
	WebElement inputHireActionDrpdw;
	@FindBy(xpath="//tbody/tr/td/ul/li[text()='Hire']")
	WebElement lstHire;
	@FindBy(xpath="//label[text()='Legal Employer']//following::input[1]")
	WebElement edtLegalEmployer;
	@FindBy(xpath="//label[text()='Person Number']//following::input[1]")
	WebElement edtPersonNumber;
	@FindBy(xpath="//label[text()='Last Name']//following::input[1]")
	WebElement edtLastName;
	@FindBy(xpath="//label[text()='First Name']//following::input[1]")
	WebElement edtFirstName;
	@FindBy(xpath="//label[text()='Gender']//following::input[1]")
	WebElement slctGender;
	@FindBy(xpath="//div[@title='Next']")
	WebElement btnNext;	
	@FindBy(xpath="//h1[text()='Email Details']//following::img[@title='Add Row']")
	WebElement btnAddMail;
	@FindBy(xpath="//table[@summary='Email Details']/tbody/tr/td[4]/span/span/span/input[1]")
	WebElement edtEmailType;
	@FindBy(xpath="//table[@summary='Email Details']/tbody/tr/td[5]/span/span/input[1]")
	WebElement edtEmail;	
	@FindBy(xpath="(//label[text()='Business Unit']//following::input[1])[1]")
	WebElement edtBU;	
	@FindBy(xpath="(//a[text()='Manage Jobs'])[1]")
	WebElement lnkManageLocation;	
	@FindBy(xpath="//span[text()='Create']")
	WebElement btnCreate;
	@FindBy(xpath="//label[text()='Effective Start Date']//following::input[1]")
	WebElement inputStartDate;
	@FindBy(xpath="//label[text()='Job Set']//following::input[1]")
	WebElement inputLocationSet;
	@FindBy(xpath="//label[text()='Name']//following::input[1]")
	WebElement inputName;
	@FindBy(xpath="//label[text()='Code']//following::input[1]")
	WebElement inputCode;
	@FindBy(xpath="//label[text()='Address Line 1']//following::input[1]")
	WebElement edtAddressL1;
	@FindBy(xpath="//label[text()='City']//following::input[1]")
	WebElement edtCity;
	@FindBy(xpath="//label[text()='State']//following::input[1]")
	WebElement edtState;
	@FindBy(xpath="//label[text()='Postal Code']//following::input[1]")
	WebElement edtPostalCode;
	@FindBy(xpath="//label[text()='Assignment Status']//following::input[1]")
	WebElement edtAssignmentStatus;
	@FindBy(xpath="//tbody/tr/td/ul/li[text()='Active - Payroll Eligible']")
	WebElement lstActivePayrollEligible;
	@FindBy(xpath="//label[text()='Person Type']//following::input[1]")
	WebElement edtPersonType;
	@FindBy(xpath="//tbody/tr/td/ul/li[text()='Employee']")
	WebElement lstEmployee;
	@FindBy(xpath="//label[text()='Position']//following::input[1]")
	WebElement edtPosition;
	@FindBy(xpath="(//label[text()='Job']//following::input[1])[1]")
	WebElement edtJob;
	@FindBy(xpath="(//label[text()='Grade']//following::input[1])[1]")
	WebElement edtGrade;
	@FindBy(xpath="(//label[text()='Department']//following::input[1])[1]")
	WebElement edtDepartment;
	@FindBy(xpath="(//label[text()='Location']//following::input[1])[1]")
	WebElement edtLocation;	
	@FindBy(xpath="(//label[text()='Salary Basis']//following::input[1])[1]")
	WebElement edtSalaryBasis;
	@FindBy(xpath="(//label[text()='Salary Amount']//following::input[1])[1]")
	WebElement edtSalaryAmount;	
	@FindBy(xpath="//div[@title='Save']")
	WebElement btnSave;
	@FindBy(xpath="(//label[text()='Person Number'])[1]//following::td[1]")
	WebElement GetPersonNumber;	
	@FindBy(xpath="//label[text()='Address Line 1']//following::input[1]")
	WebElement inputAddress;
	@FindBy(xpath="//label[text()='City']//following::input[1]")
	WebElement inputCity;
	@FindBy(xpath="//label[text()='State']//following::input[1]")
	WebElement inputState;
	@FindBy(xpath="(//label[text()='Postal Code']//following::input)[1]")
	WebElement inputPostalCode;	
	@FindBy(xpath="//span[contains(text(),'Sub')]")
	WebElement btnSubmit;	
	@FindBy(xpath="//td[contains(text(),'submitted')]")
	WebElement popUpWinText;	
	@FindBy(xpath="//td[contains(text(),'saved')]")
	WebElement popUpChangesSaved;
	@FindBy(xpath="//button[text()='es']")
	WebElement btnYes;	
	@FindBy(xpath="//div[text()='Confirmation']//following::td[contains(text(),'submitted')]")
	WebElement lnkConfirmation;		
	@FindBy(xpath="(//button[contains(text(),'O')])[1]")
	WebElement btnConfOK;
	@FindBy(xpath="(//button[contains(text(),'O')])[2]")
	WebElement btnOK;
	public HRHireAnEmployee(Utility util) throws Exception{
		//this.driver = driver;    	
		PageFactory.initElements(util.ng_returnDriver(), this);
	}  


	/*----------------------------------------------------------------------------
    Function Name    	: hireAnEmployee
    Description     	: Verify hiring an employee  
    Author				:     
    Date of creation	:
	Date of modification:
    ----------------------------------------------------------------------------*/ 
	public void hireAnEmployee() throws Exception {    	

		if(Global.objErr == "11"){
			return;
		}    
		try {
			TestData td = new TestData ();
			Global.objData = (HashMap) td.readTestData (Global.gTCID, Global.gstrClassName, Global.gstrMethodName);	        	    
			Global.test.log(Status.INFO,"Class and Method : "+ Global.gstrClassName +" . "+Global.gstrMethodName);
			Utility.waitForPageToLoad();
			Utility.waitForSpinner();  	    
			Utility.ng_clickSimply(lnkHireEmployee, "Hire Employee", "HireEmployeeClick");
			Utility.waitForPageToLoad();
			Utility.waitForSpinner();    	    
			Utility.ng_typeAndTab(edtLegalEmployer, "Legal Employer", "LegalEmployerSet");
			Utility.waitForSpinner();     
			int intPersonNum = Utility.ng_RandomNumNineDigits();
			strPersonNumber = Integer.toString(intPersonNum);
			System.out.println("Person Number:"+strPersonNumber);      
			Global.gstrReadfromTestData = false;
			Utility.waitForSpinner();
			Utility.ng_enterText(edtPersonNumber, "Person Number", strPersonNumber);
			Global.gstrReadfromTestData = true;    	    
			Utility.waitForSpinner();
			Global.gstrReadfromTestData = false;
			String strLastName = "LastName" + strPersonNumber;
			Utility.ng_enterText(edtLastName, "Last Name", strLastName);
			Utility.waitForSpinner();
			String strPersonName = "FirstName" + strPersonNumber;
			Utility.ng_enterText(edtFirstName, "First Name", strPersonName);
			Utility.waitForSpinner();
			Global.gstrReadfromTestData = true;     	    
			Utility.ng_enterText(slctGender, "Gender", "GenderSet");    	        	    
			Utility.ng_clickWebElement(btnNext,"Next", "NextClick");
			Utility.waitForPageToLoad();
			Utility.waitForSpinner();    	    
			Utility.ng_enterText(edtAddressL1, "Address Line1", "AddressLine1Set");
			Utility.waitForSpinner();
			Utility.ng_enterText(edtCity, "City", "CitySet");
			Utility.waitForSpinner();
			Utility.ng_enterText(edtState, "State", "StateSet");
			Utility.waitForSpinner();
			Utility.ng_enterText(edtPostalCode, "Postal Code", "PostalCodeSet");
			Utility.waitForSpinner();    	    
			Utility.ng_clickWebElement(btnAddMail, "Add Email", "AddEmailClick");
			Utility.waitForSpinner();
			Utility.ng_enterText(edtEmailType, "Email Type", "EmailTypeSet");    	    
			Utility.waitForSpinner();
			Global.gstrReadfromTestData = false;
			String EmailID = strPersonNumber+"@gmail.com";
			Utility.ng_enterText(edtEmail, "Email", EmailID);
			Utility.waitForSpinner();
			Global.gstrReadfromTestData = true;

			Utility.ng_clickWebElement(btnNext,"Next", "NextClick");
			Utility.waitForPageToLoad();
			Utility.waitForSpinner();

			Utility.ng_typeAndTab(edtBU, "Business Unit", "BUSet");
			Utility.waitForSpinner();

			/*
    	    Utility.ng_clickUsingActions(edtAssignmentStatus, "Assignment Status", "AssignmentStatusClick");
    	    Utility.waitForSpinner();
    	    String AssignmentStatus = Utility.getTestDataValue("AssignmentStatusSet");
    	    WebElement AssgStatus = Utility.driver.findElement(By.xpath("//tbody/tr/td/ul/li[text()='"+AssignmentStatus+"']"));
    	    Utility.ng_clickWebElement(AssgStatus, "Assignment Status", "AssignmentStatusClick");
    	    Utility.waitForSpinner();
			 */

			//Utility.ng_clickUsingActions(edtAssignmentStatus, "Assignment Status", "Click");
			//Utility.waitForSpinner();
			//Utility.ng_clickUsingActions(lstActivePayrollEligible, "Active Payroll Eligible", "Click");
			//Utility.waitForSpinner();

			/*
    		Utility.ng_clickUsingActions(edtPersonType, "Person Type", "Click");
    		Utility.waitForSpinner();
    		Utility.ng_clickUsingActions(lstEmployee, "Employee", "Click");
    		Utility.waitForSpinner();
			 */

			//Utility.ng_typeAndTab(edtPosition, "Position", "GetPosition");
			//Utility.waitForSpinner();

			Utility.ng_typeAndTab(edtJob, "Job", "JobSet");
			Utility.waitForSpinner();
//			Utility.ng_typeAndTab(edtGrade, "Grade", "GradeSet");
//			Utility.waitForSpinner();    		
//			Utility.ng_typeAndTab(edtDepartment, "Department", "DeptSet");
//			Utility.waitForSpinner();
//			Utility.ng_typeAndTab(edtLocation, "Location", "LocationSet"); //SKIP
//			Utility.waitForSpinner();

			Utility.ng_clickWebElement(btnNext,"Next", "NextClick");
			Utility.waitForPageToLoad();
			Utility.waitForSpinner();
//
//			Utility.ng_typeAndTab(edtSalaryBasis, "Salary Basis", "SalaryBasisSet");
//			Utility.waitForSpinner();
//			Utility.ng_enterText(edtSalaryAmount, "Salary Amount", "SalaryAmountSet");
//			Utility.waitForSpinner();

			Utility.ng_clickSimply(btnNext,"Next", "NextClick");
			Utility.ng_waitForElementEnabled(btnSave, 20);
			Utility.waitForSpinner();
			Utility.waitForPageToLoad();

			Utility.ng_clickWebElement(btnSave, "Save", "SaveClick");
			Utility.waitForPageToLoad();
			Utility.waitForSpinner();
			try
			{
				if(popUpChangesSaved.isDisplayed())
				{
					Utility.ng_clickWebElement(btnConfOK, "OK", "OKClick");
				}
			}
			catch(Exception e)
			{
				System.out.println("PopupWindow for save changes did not come up");
				//Global.report.flush();
			}	   


			Utility.ng_getElementText(GetPersonNumber, "Person Number", "PersonNumberGet");
			String PersonNo = Utility.ng_getText(GetPersonNumber);
			Utility.waitForSpinner();

			
			Utility.waitForSpinner();    		
			Utility.ng_clickSimply(btnSubmit, "Submit", "SubmitClick");
			Utility.waitForPageToLoad();
			Utility.waitForSpinner();

			Global.gstrReadfromTestData = true;
			Utility.ng_clickWebElement(btnYes, "Yes", "YesClick");
			Utility.waitForPageToLoad();
			Utility.waitForSpinner();
			Utility.ng_waitForElementEnabled(lnkConfirmation, 30);

			strActual= Utility.ng_getText(lnkConfirmation);
			System.out.println(strActual);	
			Utility.waitForSpinner();
			Utility.ng_assertValueWithConfirmationWin(strActual, "Expected");
			Utility.waitForSpinner();
			Utility.ng_clickWebElement(btnConfOK, "OK", "OKClick");

			//Global.report.flush();
			Global.gstrReadfromTestData = true;
			Utility.waitForSpinner();


		}

		catch (Exception e) {
			Global.objErr = "11";
			Utility.ng_ScriptAbortedLog(e);
		}  
	}
}
