package com.admin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
//import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class reports {
	//private static final String DataName = null;
	public ExtentHtmlReporter htmlReport;
	 public  ExtentTest logger;
	 public static  ExtentReports extent;
	 public static WebDriver driver;
	 //public static void onstart(ITestContext testContext) throws IOException
	// {
		 //extent=new ExtentReports();
	 @BeforeTest
public void setExtent()
{
	 	
	 	String dateName=new  SimpleDateFormat("yyyyMMddHHmmss").format(new Date());//timestamp
	 	//String Testreport="MyReport-"+dateName+".html";
	 	//String Myreport="Test-Report-\"+datatime+\".html";
	 	//C:\Users\91725\Documents\eclipse\new automationproject\inetbanking\test-output
	 	htmlReport = new ExtentHtmlReporter(System.getProperty("user.dir")+ "/test-output/MyReport-"+dateName+".html");
	//htmlReport = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/Myreport.html");
	 	//htmlReporter .loadXMLConfig(System.getProperty("user.dir")+ "/extent_config.xml");
	 	htmlReport .loadXMLConfig(System.getProperty("user.dir")+ "/extent_config.xml");
	 	
	 	extent=new ExtentReports();

	 	
	 	extent.attachReporter(htmlReport );
	 	extent.setSystemInfo("hostname", "localhost");
	 	extent.setSystemInfo("environment","QA");
	 	extent.setSystemInfo("user", "sri");
	 	htmlReport.config().setDocumentTitle("inetbanking test project");
	 	htmlReport.config().setReportName("function test reporter");

	 htmlReport.config().setTheme(Theme.DARK);
	 	
	 	
	 }
	 @AfterTest
	 public void teardown()
	 
	 {
		 extent.flush();
	
	 }
	 @BeforeMethod
	 public void setup()
	 {
		 System.setProperty("webdriver.chrome.driver","C:\\Users\\91725\\Documents\\eclipse\\new automationproject\\inetbanking\\Drivers\\chromedriver.exe");
			driver=new ChromeDriver();
			driver.get("https://demo.nopcommerce.com/");
			driver.manage().timeouts().getImplicitWaitTimeout();
			driver.manage().window().maximize();
	 }
	 @Test
	 public void test()
	 {logger=extent.createTest("nopCommerce demo store");
		 String tittle=driver.getTitle();
			System.out.println(tittle); 
			Assert.assertEquals(tittle,"nopCommerce demo store");
	 }
	
	 @AfterMethod
	 public void teardown(ITestResult result) throws IOException
	 {if(result.getStatus()==ITestResult.SUCCESS)
	 {
		 logger = extent.createTest(result.getName());//create new empty report
		 logger.log(Status.PASS,MarkupHelper.createLabel(result.getName(),ExtentColor.GREEN));//
	 
	 }
	 else if(result.getStatus()==ITestResult.FAILURE)
		 
	 {
		 logger = extent.createTest(result.getName());//create new empty report
		 	logger.log(Status.FAIL,MarkupHelper.createLabel(result.getName(),ExtentColor.RED));//send fail information
		 	
		 	String ScreenShotpath=System.getProperty("user.dir")+"\\screenshots\\"+result.getName()+".png";
		 	File f= new File(ScreenShotpath);
		 	if(f.exists())
		 	{
		 		try
		 		{ 
		 		logger.fail("screenshot is below"+ logger.addScreenCaptureFromPath(ScreenShotpath));
		 		}
		 		catch(Exception e)
		 		{
		 			e.printStackTrace();
		 			
		 			
		 		}
		 		
		 	}
		// String ScreenShotpath=reports.getScreenShot(driver,result.getName());
		 	
		// logger.addScreenCaptureFromPath(ScreenShotpath);

		 }else if(result.getStatus()==ITestResult.SKIP)
		 {
			 	

			 logger = extent.createTest(result.getName());//create new empty report
			 logger.log(Status.SKIP,MarkupHelper.createLabel(result .getName(),ExtentColor.BLUE));//send pass information
			 }
	 driver.quit();
	 }

	// public static String getScreenShot(WebDriver driver,String tname)throws IOException
	 public void getScreenShot(WebDriver driver,String tname)throws IOException
	 {
			//String dateName=new  SimpleDateFormat("yyyyMMddHHmmss").format(new Date());//timestamp
TakesScreenshot ts=(TakesScreenshot)driver;
	File source=ts.getScreenshotAs(OutputType.FILE);
	//String des=System.getProperty("user.dir") + "/test-output/Myreport.html")
	//String des=System.getProperty("user.dir") + "/screenshots/" + tname + dateName + ".png";
	String des=System.getProperty("user.dir") + "/screenshots/" + tname  + ".png";
	 File target=new File(des);
	// File target=new File(des);
	 FileUtils.copyFile(source,target);
		System.out.println("screenshot taken");
		 //return des;
	 }
 

}

