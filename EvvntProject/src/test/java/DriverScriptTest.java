package test.java;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import config.Constants;
import utility.ExcelUtils;
import utility.Log;
import utility.ReportBuilder;

public class DriverScriptTest {

	public static Properties OR;
	public static utility.ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static Method method[];
	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sRunMode;
	public static String sTestDescription;
	public static String sData;
	public static boolean bResult;
	public static String sDescription;
	public static String sTestStepId;
	public static ReportBuilder builder;

	@BeforeMethod
	public void  beforeMethod() throws NoSuchMethodException, SecurityException {
		actionKeywords = new utility.ActionKeywords();
		method = actionKeywords.getClass().getMethods();
		builder = new ReportBuilder();

	}

	@Test
	public void Test() throws Exception {

		ExcelUtils.setExcelFile(System.getProperty("user.dir") + "\\src\\data_engine\\DataEngine.xlsx");
		DOMConfigurator.configure("log4j.xml");
		String Path_OR = System.getProperty("user.dir") + "\\src\\config\\OR.properties";
		FileInputStream fs = new FileInputStream(Path_OR);
		OR = new Properties(System.getProperties());
		OR.load(fs);

		DriverScriptTest startEngine = new DriverScriptTest();
		
		startEngine.execute_TestCase();
		//ReportBuilder.createReport();

	}

	private void execute_TestCase() throws Exception {

		int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
		for (int iTestcase = 1; iTestcase < iTotalTestCases; iTestcase++) {
			
			bResult = true;
			sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases);
			sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode, Constants.Sheet_TestCases);
			sTestDescription = ExcelUtils.getCellData(iTestcase, Constants.Col_TestDescID, Constants.Sheet_TestCases);
			if (sRunMode.equals("Yes")) {
				Log.startTestCase(sTestCaseID);
				builder.StartTestCase(sTestCaseID, sTestDescription, "Smoke");
				iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
				iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
				bResult = true;
				
				for (; iTestStep < iTestLastStep; iTestStep++) {
					sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,
							Constants.Sheet_TestSteps);
					sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject,
							Constants.Sheet_TestSteps);
					sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);
					sDescription =  ExcelUtils.getCellData(iTestStep, Constants.Col_TestStepDescription, Constants.Sheet_TestSteps);
					sTestStepId = ExcelUtils.getCellData(iTestStep, Constants.Col_TestDescID, Constants.Sheet_TestSteps);
					execute_Actions();
					if (bResult == false) {
						ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestcase, Constants.Col_Result,
								Constants.Sheet_TestCases);
						Log.endTestCase(sTestCaseID);
						builder.endTest();
						break;
					}
				}
				if (bResult == true) {
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestcase, Constants.Col_Result,
							Constants.Sheet_TestCases);
					Log.endTestCase(sTestCaseID);
					builder.endTest();

				}
			}
		}
		builder.CloseReport();
	}

	private static void execute_Actions() throws Exception {

		for (int i = 0; i < method.length; i++) {

			
			if (method[i].getName().equals(sActionKeyword)) {
				method[i].invoke(actionKeywords, sPageObject, sData, sDescription, builder);
				if (bResult == true) {
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult,
							Constants.Sheet_TestSteps);
					break;
				} else {
					ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult,
							Constants.Sheet_TestSteps);
					//utility.ActionKeywords.closeBrowser("", "", builder);
					break;
				}
			}
		}
	}

}