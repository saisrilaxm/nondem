package com.admin;

import java.io.IOException;
//import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class adminloginpage {
WebDriver driver;
@BeforeClass
public void setup()
{
	System.setProperty("webdriver.chrome.driver","C:\\Users\\91725\\Documents\\eclipse\\new automationproject\\inetbanking\\Drivers\\chromedriver.exe");
	driver=new ChromeDriver();

	driver.manage().timeouts().getImplicitWaitTimeout();
	driver.manage().window().maximize();
}
@Test(dataProvider="loginData")
public void logintest(String mail,String pwd)
{
	driver.get("https://admin-demo.nopcommerce.com/ogin");
	WebElement txtemail=driver.findElement(By.id("Email"));
	txtemail.clear();
	txtemail.sendKeys(mail);
	WebElement txtpassword=driver.findElement(By.id("Password"));
	txtpassword.clear();
	txtpassword.sendKeys(pwd);
	driver.findElement(By.xpath("/html/body/div[6]/div/div/div/div/div[2]/div[1]/div/form/div[3]/button")).click();
	String tittle=driver.getTitle();
	System.out.println(tittle); 
	
	
	
}
@DataProvider(name="loginData")
public String[][] getData() throws IOException   
{
	/*String loginData[][]= {
			{"admin@yourstore.com","admin"},
				{"admin@yourstore.com","admin1"},
				
				{	"admin@yourstore.com","admin2"}
				};
	return loginData;*/
	String path=System.getProperty("user.dir")+ "/src/test/java/com/admin/Book1.xlsx";
	xlutilits xlutil=new xlutilits(path);
int totalrows=xlutil.getRowCount("Sheet1");
int totalcols=xlutil.getCellCount("Sheet1", 1);
 String loginData[][]=new String[totalrows][totalcols];
for(int i=1;i<=totalrows;i++)//1
{
	for(int j=0;j<totalcols;j++)//0  
	{
		loginData[i-1][j]=xlutil.getCellData( "Sheet1",i,j);
	}
}
	return loginData;
	
	
}
	
@AfterClass
void teardown()
{driver.close();
}
			
	
}

