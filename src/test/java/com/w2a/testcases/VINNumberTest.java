package com.w2a.testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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

public class VINNumberTest extends TestBase {
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;
	private int rowNum = 2;

	@BeforeTest
	public void setReport() {

		htmlReporter = new ExtentHtmlReporter("./reports/VINNumberextent.html");

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

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void vINNumberTest(Hashtable<String, String> data, Method m) throws InterruptedException {
		test = extent.createTest("VIN Number Test");
		if (!(TestUtil.isTestRunnable("vINNumberTest", excel))) {

			throw new SkipException("Skipping the test " + "vINNumberTest".toUpperCase() + "as the Run mode is NO");
		}
		if (data.get("runmode").equals("N")) {

			throw new SkipException("Skipping the test case as the Run mode for data set is NO");
		}

//		if(data.get("Runmode").equalsIgnoreCase("N")){
		//	throw new SkipException("Skipping the test case " + "vINNumberTest".toUpperCase() + " as the Run mode is NO");
		
	//	}
		Thread.sleep(10000);
		click("Login_XPATH");
		type("Email_ID", data.get("Email"));
		type("Password_ID", data.get("Password"));
		click("Signin_XPATH");
		click("Home_XPATH");
		click("VINnumber_XPATH");
		type("VinNumber_XPATH", data.get("VIN Number"));
		click("vinSearch_XPATH");
		click("Nav_XPATH");
		click("FindParts_XPATH");
		System.out.println("Enter into the loop");
		if (data.get("Default Category").equalsIgnoreCase("ENGINE")) {
			System.out.println("Entered into the loop");
			click("Engine_XPATH");
			if (data.get("Sub Category").equalsIgnoreCase("Gaskets")) {
				click("Gaskets_XPATH");
				click("Cart_XPATH");
			}
		} else if (data.get("Default Category").equalsIgnoreCase("Maintenance Service Parts")) {
			click("MaintenanceServiceParts_XPATH");
			click("ServiceIntervals_XPATH");
			click("Cart_XPATH");
		} else if (data.get("Default Category").equalsIgnoreCase("Window Cleaning")) {
			click("WindowCleaning_XPATH");
			click("Washer_XPATH");
			click("Cart_XPATH");
		}
		click("Shopcart_XPATH");
		click("Checkout_XPATH");
		Thread.sleep(10000);
		click("FlatRate_XPATH");
		click("Next_XPATH");
		click("Cash_XPATH");
		click("PlaceOrder_XPATH");
		System.out.println("Order Confirmed");
		Thread.sleep(10000);
		WebElement Prem = driver.findElement(By
				.xpath("//*[@id=\"maincontent\"]/div/div/div/div/div[2]/div[2]/div[2]/div[2]/table/tfoot/tr[3]/td[2]"));
		String Total = Prem.getText();
		System.out.println("Total" + Total);
		Thread.sleep(10000);
		WebElement ele = driver.findElement(By.cssSelector(
				"body > div:nth-child(9) > main:nth-child(4) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(4) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > span:nth-child(2)"));
		String Order = ele.getText();
		System.out.println("Order" + Order);
		write(Total, m, 11, rowNum);
		write(Order, m, 12, rowNum);
		rowNum++;
		click("MyAccount_XPATH");
		click("Logout_XPATH");
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
