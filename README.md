# AioBot

The test scripts Login check and Create Bot script is placed in src/test/java/pages
The framework is built in such a way that it is dependent upon AIO_MaterDriver.xlsx, where we can change the RunMode as Y or N.
In turn the activities on UI are dependent on two other excel files namely AIO_CommonActions.xlsx under AIO_TestConfiguration folder and TestData files under AIO_TestData folder, where all the test data and actions to be performed on UI are defined for each script.
You can run the script through testng.xml as Run as TestNG suite or you can run pom.xml as maven test.
The results folder is created everytime you run the project in teh project directory itself as for expamle "TestRunResults_12-Jun-20_12-24-04 PM" with the related timestamp.
The summary excel can be found inside it as for example for each run "AIO Test Automation Results Summary_12-Jun-20_12-24-05 PM"
The detailed extent html report with screenshots embedded with each step details for example named as "AIO Test Automation Report_12-Jun-20_12-24-05 PM" can be found in TestResultLog folder under TestRunResults_XXX folder.
And the screenshots additionally are stored in Screenshots folder under TestRunResults_XXX folder.
