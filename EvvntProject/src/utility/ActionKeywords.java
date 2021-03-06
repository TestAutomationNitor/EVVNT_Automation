package utility;

import static test.java.DriverScriptTest.OR;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import config.Constants;
import test.java.DriverScriptTest;

public class ActionKeywords {
	
	public static WebDriver driver = null;
	static WebDriverWait wait = null;
	public static String path;
	
	public static void openBrowser(String object, String data, String sDescription, ReportBuilder builder) {
		Log.info("Opening Browser");
		try {
			if (data.equals("Mozilla")) {
				driver = new FirefoxDriver();
				
				Log.info("Mozilla browser started");
				path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
				builder.DisplayResult("Pass", sDescription, "Mozilla browser started",path);
			} else if (data.equals("IE")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\Drivers\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				
				Log.info("IE browser started");
				path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
				builder.DisplayResult("Pass", sDescription, "IE browser started",path);
			} else if (data.equals("Chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");
				driver = new ChromeDriver();
				
				Log.info("Chrome browser started");
				path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
				builder.DisplayResult("Pass", sDescription, "Chrome browser started",path);
			}

			int implicitWaitTime = (10);
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
			
			
			
		} catch (Exception e) {
			Log.info("Not able to open the Browser --- " + e.getMessage());
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Fail", "Launch browser", "Not able to open the Browser --- " + e.getMessage(),path);
			DriverScriptTest.bResult = false;
			
		}
	}

	/*public static void setDeviceSize(String object, String data, ReportBuilder builder) {

		String[] parts = data.split(",");
		String part1 = parts[0].trim();
		String part2 = parts[1].trim();

		int width = Integer.parseInt(part1);
		int height = Integer.parseInt(part2);
		Dimension size = new Dimension(width, height);
		driver.manage().window().setSize(size);
	}*/

	public static void navigate(String object, String data, String sDescription,ReportBuilder builder) {
		try {
			Log.info("Navigating to URL");
			
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get(Constants.URL);
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Pass", sDescription, "Navigated to URL", path);
			
		} catch (Exception e) {
			Log.info("Not able to navigate --- " + e.getMessage());
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Fail", sDescription, "Not able to navigate --- " + e.getMessage(),path);
			DriverScriptTest.bResult = false;
			e.printStackTrace();
			
		}
	}

	public static void click(String object, String data,String sDescription, ReportBuilder builder) {
		try {
			Log.info("Clicking on Webelement " + object);
			
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Pass", sDescription, "Clicked",path);
			
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage());
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Fail", sDescription, "Not able to click --- " + e.getMessage(),path);
			DriverScriptTest.bResult = false;
			
		}
	}

	public static void input(String object, String data, String sDescription,ReportBuilder builder) {
		try {
			Log.info("Entering the text in " + object);
			
			WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
			element.click();
			element.clear();
			element.sendKeys(data);
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Pass", sDescription, "Text entered",path);
			
		} catch (Exception e) {
			Log.error("Not able to Enter text --- " + e.getMessage());
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Fail", sDescription, "Not able to Enter text --- " + e.getMessage(),path);
			DriverScriptTest.bResult = false;
			
		}
	}

	public static void waitFor(String object, String data, String sDescription,ReportBuilder builder) throws Exception {
		try {
			Log.info("Wait for 10 seconds");
			
			Thread.sleep(10000);
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Pass", sDescription, "Test step passed",path);
			
		} catch (Exception e) {
			Log.error("Not able to Wait --- " + e.getMessage());
			path  = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Fail", sDescription, "Not able to Wait --- " + e.getMessage(),path);
			DriverScriptTest.bResult = false;
			
		}
	}

	public static void closeBrowser(String object, String data, String sDescription,ReportBuilder builder) {
		try {
			Log.info("Closing the browser");
			
			driver.close();
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Pass", sDescription, "Browser closed",path);
		
		} catch (Exception e) {
			Log.error("Not able to Close the Browser --- " + e.getMessage());
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Fail", sDescription,"Not able to Close the Browser --- " + e.getMessage(),path);
			DriverScriptTest.bResult = false;
		}
	}

	public static void waitforobject(String object, String data,String sDescription, ReportBuilder builder) {

		try {
			Log.info("Wait for object");
			
			wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(object))));
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Pass", sDescription, "Test step passed",path);
		
		} catch (Exception e) {
			Log.error("The element:" + object + "is not present");
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Fail", sDescription, "The element:" + object + " is not present",path);
			DriverScriptTest.bResult = false;
			
		}
	}
	
	public static String TakeScreenshot(String ImageName){
		TakesScreenshot oScn = (TakesScreenshot) driver;
        File oScnShot = oScn.getScreenshotAs(OutputType.FILE);
        String path = "C:\\Screenshot\\"+ImageName+".png";
        //String path = System.getProperty("user.dir")+"//Screenshot//"+ImageName+".png";
     File oDest = new File(path);
     try {
          FileUtils.copyFile(oScnShot, oDest);
     } catch (IOException e) {
    	 System.out.println(e.getMessage())
    	 ;}
     return path;
            }
	
	
	public String getCurrentDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static void scrollDown(String object, String data,String sDescription, ReportBuilder builder){
		
		try {
			Log.info("Scroll Down Page");
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,250)", "");
		path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
		builder.DisplayResult("Pass", sDescription, "Test step passed",path);
		} catch (Exception e) {
			Log.error("Scroll Down error");
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Fail", sDescription, "Scroll Down Failed",path);
			e.printStackTrace();
		}
	}
	
	public static void clickByName(String object, String data,String sDescription, ReportBuilder builder) {
		try {
			Log.info("Clicking on Webelement " + object);
			
			driver.findElement(By.name("commit")).click();
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Pass", sDescription, "Clicked",path);
			
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage());
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Fail", sDescription, "Not able to click --- " + e.getMessage(),path);
			DriverScriptTest.bResult = false;
			
		}
	}
	
	public static void clickEnter(String object, String data,String sDescription, ReportBuilder builder){
		try {
			Log.info("Clicking on Enter " );
			
			driver.findElement(By.name(OR.getProperty(object))).sendKeys(Keys.ENTER);
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Pass", sDescription, "Enter",path);
			
		} catch (Exception e) {
			Log.error("Not able to press enter key --- " + e.getMessage());
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Fail", sDescription, "Not able to press enter key --- " + e.getMessage(),path);
			DriverScriptTest.bResult = false;
			
		}
	}

	public static void dropdownSelection(String object, String data,String sDescription, ReportBuilder builder){
		try {
			Log.info("Selecting "+ data+ " from dropdown" );
			Select dropdown = new Select(driver.findElement(By.xpath(OR.getProperty(object))));
			dropdown.selectByVisibleText(data);
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Pass", sDescription, "Value selected from dropdown",path);
			
		} catch (Exception e) {
			Log.error("Not able to select value from drop down --- " + e.getMessage());
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Fail", sDescription, "Not able to select value from drop down ---" + e.getMessage(),path);
			DriverScriptTest.bResult = false;
			
		}
	}
	
	public static void verifyDisabled(String object, String data,String sDescription, ReportBuilder builder){
		try {
			Log.info("Verify " +object+ " disabled" );
			WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
			if("false" == element.getAttribute("readonly") || "false" == element.getAttribute("disabled")){
			
				builder.DisplayResult("Fail", sDescription, "Element is enabled",path);
			}
			else{
				builder.DisplayResult("Pass", sDescription, "Disabled",path);
			}
			
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			
			
		} catch (Exception e) {
			Log.error("Error" + e.getMessage());
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Fail", sDescription, "Error" + e.getMessage(),path);
			DriverScriptTest.bResult = false;
			
		}
	}
	
	public static void verifyDropdownValue(String object, String data,String sDescription, ReportBuilder builder){
		try {
			Log.info("Selecting "+ data+ " from dropdown" );
			Select dropdown = new Select(driver.findElement(By.xpath(OR.getProperty(object))));
			String selectedValue = dropdown.getFirstSelectedOption().getText();
			if(data.equals(selectedValue)){
				builder.DisplayResult("Pass", sDescription, data+ " Value selected from dropdown",path);
			}
			else{
				builder.DisplayResult("Fail", sDescription, "Different value selected in dropdown",path);
			}
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			
			
		} catch (Exception e) {
			Log.error("Error --- " + e.getMessage());
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Fail", sDescription, "Error ---" + e.getMessage(),path);
			DriverScriptTest.bResult = false;
			
		}
	}

	public static void verifyEnabled(String object, String data,String sDescription, ReportBuilder builder){
		try {
			Log.info("Verify " +object+ " enabled" );
			WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
			if("true" == element.getAttribute("readonly") || "true" == element.getAttribute("disabled")){
			
				builder.DisplayResult("Fail", sDescription, "Element is disabled",path);
			}
			else{
				builder.DisplayResult("Pass", sDescription, "Enabled",path);
			}
			
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			
			
		} catch (Exception e) {
			Log.error("Error" + e.getMessage());
			path = TakeScreenshot(DriverScriptTest.sTestCaseID+"_StepId_"+DriverScriptTest.sTestStepId);
			builder.DisplayResult("Fail", sDescription, "Error" + e.getMessage(),path);
			DriverScriptTest.bResult = false;
			
		}
	}
}
