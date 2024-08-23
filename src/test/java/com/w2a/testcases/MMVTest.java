package com.w2a.testcases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Hashtable;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class MMVTest extends TestBase {
	
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;
	private int rowNum = 1;

	@BeforeTest
	public void setReport() {

		htmlReporter = new ExtentHtmlReporter("./reports/MMVextent.html");

		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setDocumentTitle("ASV Automation Reports");
		htmlReporter.config().setReportName("ASV Automation Test Results");
		htmlReporter.config().setTheme(Theme.STANDARD);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		extent.setSystemInfo("Automation Tester", "Bala Kullayappa");
		extent.setSystemInfo("Orgainzation", "Serole Technologies");
		//extent.setSystemInfo("Build No", "W2A-1234");


	}

	@AfterTest
	public void endReport() {

		extent.flush();
	}
	
	
	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")
	public void mMVTest(Hashtable<String,String> data, Method m) throws InterruptedException{
		test = extent.createTest("MMV Test");
		if (!(TestUtil.isTestRunnable("mMVTest", excel))) {

			throw new SkipException("Skipping the test " + "mMVTest".toUpperCase() + "as the Run mode is NO");
		}
		if(data.get("Runmode").equalsIgnoreCase("N")){
			throw new SkipException("Skipping the test case " + "mMVTest".toUpperCase() + " as the Run mode is NO");
		
		}
		Thread.sleep(10000);
		click("Login_XPATH");
		type("Email_ID",data.get("Email"));
		type("Password_ID",data.get("Password"));
		click("SignIn_XPATH");
		click("Home_XPATH");
		click("MMV_XPATH");
		select("Make_CSS", data.get("Make"));
		Actions action = new Actions(driver);
		Thread.sleep(2000);
        action.sendKeys(Keys.TAB).perform();
        Thread.sleep(2000);
        action.sendKeys(Keys.ENTER).perform();
        Thread.sleep(2000);
        action.sendKeys(Keys.ARROW_DOWN).perform();
        Thread.sleep(2000);
        action.sendKeys(Keys.ENTER).perform();
        Thread.sleep(2000);
        action.sendKeys(Keys.TAB).perform();
        Thread.sleep(2000);
        action.sendKeys(Keys.ENTER).perform();
        Thread.sleep(2000);
        action.sendKeys(Keys.ARROW_DOWN).perform();
        Thread.sleep(2000);
        action.sendKeys(Keys.ENTER).perform();
		click("MMVSearch_XPATH");
	    click("Vehicledetails_XPATH");
	    click("FindParts_XPATH");
	    if(data.get("Default Category").equalsIgnoreCase("ENGINE")) {
	    	 click("Cat_XPATH");
	    	 click("SubCat_XPATH");
	    	 System.out.println("Selected subcategory");
	    }
	    click("Cart_XPATH");
		click("Shopcart_XPATH");
		click("Checkout_XPATH");
		Thread.sleep(10000);
		click("FlatRate_XPATH");
		click("Next_XPATH");
		click("Cash_XPATH");
		click("PlaceOrder_XPATH");
		Thread.sleep(10000);
		System.out.println("Order Confirmed");
		WebElement Premium = driver.findElement(By.xpath("//*[@id=\"maincontent\"]/div/div/div/div/div[2]/div[2]/div[2]/div[2]/table/tfoot/tr[3]/td[2]"));
		String TotalPremium = Premium.getText();
		WebElement element = driver.findElement(By.cssSelector(
				"body > div:nth-child(9) > main:nth-child(4) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(4) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > span:nth-child(2)"));
		String OrderNum = element.getText();
		write(TotalPremium, m, 11, rowNum);
		write(OrderNum, m, 12, rowNum);
		rowNum++;
		Thread.sleep(10000);
		click("MyAccount_XPATH");
		click("Logout_XPATH");
		Thread.sleep(10000);
     
	}

@AfterMethod
public void tearDown(ITestResult result) {

	if (result.getStatus() == ITestResult.FAILURE) {
		
		
		String excepionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		test.fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
				+ "</font>" + "</b >" + "</summary>" + excepionMessage.replaceAll(",", "<br>") + "</details>"
				+ " \n");
		
		try {

			TestUtil.captureScreenshot();
			test.fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
					MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.screenshotName)
							.build());
		} catch (IOException e) {

		}
		String failureLogg = "TEST CASE FAILED";
		Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
		test.log(Status.FAIL, m);
		
		

	} else if (result.getStatus() == ITestResult.SKIP) {

		String methodName = result.getMethod().getMethodName();

		String logText = "<b>" + "TEST CASE: - " + methodName.toUpperCase() + "  SKIPPED" + "</b>";

		Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		test.skip(m);

	} else if (result.getStatus() == ITestResult.SUCCESS) {

		String methodName = result.getMethod().getMethodName();

		String logText = "<b>" + "TEST CASE: - " + methodName.toUpperCase() + "  PASSED" + "</b>";

		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		test.pass(m);

	}

}
}