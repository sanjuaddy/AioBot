package lib;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AppUtility {

	public static WebDriver driver;
	public static WebDriverWait wait;
	public static JavascriptExecutor js;

	public AppUtility() throws Exception {
		this.driver =  Utility.ng_returnDriver();
		wait = new WebDriverWait(driver, 40);
		this.js = (JavascriptExecutor) this.driver;
	}

}