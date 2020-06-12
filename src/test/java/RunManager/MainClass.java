package RunManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

import lib.Global;

public class MainClass {
	//@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		   TestNG testng = new TestNG();
		   List<String> lst = new ArrayList<String>();
		   lst.add(Global.gstrTestNgXmlLocation); 
		   testng.setTestSuites(lst);
		   testng.run();
		}

}
