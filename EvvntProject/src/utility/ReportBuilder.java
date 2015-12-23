package utility;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import net.mindengine.galen.reports.HtmlReportBuilder;

public class ReportBuilder {

	
	    ExtentReports extent;
        ExtentTest test; 
        Date date = new Date();
	
        public ReportBuilder(){
        	String path = System.getProperty("user.dir") + "\\Report\\EvvntReport.html";
        	extent = new ExtentReports(path, true);
        	  extent.config()
  	        .documentTitle("Automation Report")
  	        .reportName("Execution Report_")
  	        .reportHeadline(date.toString());
        }
        
	
	
//	public static void deleteReport(File folderName) {
//
//		File[] contents = folderName.listFiles();
//		if (contents != null) {
//			for (File f : contents) {
//				deleteReport(f);
//			}
//		}
//		folderName.delete();
//
//	}
	
	 public void StartTestCase(String testCaseName,String description,String category)
     {
       test = extent.startTest(testCaseName, description).assignCategory(category);
      
     }

     public void DisplayResult(String result, String step, String details, String path)
     {  
         if(result.equals("Pass")){
        	 test.log(LogStatus.PASS, step, details + test.addScreenCapture(path) );
         }
         else{
         if(result.equals("Fail"))
        	 test.log(LogStatus.FAIL, step, details + test.addScreenCapture(path) );
         }
     
     }
      
     public void endTest()
     {
         extent.endTest(test);
     }

     public void CloseReport()
     {
         extent.flush();
     }
}
