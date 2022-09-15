package fdxCIL_Stages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_BasePackage.ExcelDataProvider;

public class ConfirmPull extends BaseInit {

	public static void FedExConfirmPull() throws Exception{
		JavascriptExecutor jse = (JavascriptExecutor) driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(driver, 50);// wait time
		for(int i=3; i<4;i++)
		{
		ExcelDataProvider excelDataProvider = new ExcelDataProvider();
		
		Thread.sleep(5000);
		try {
			Thread.sleep(4000);
			String task = driver.findElement(By.xpath("/html/body/div[2]/section/div[2]/div[1]/div/div[2]/div[2]/div[1]/div[5]/label")).getText();
			if (task.contains("Basic Search")) {
				Thread.sleep(4000);
				
		String JobId = excelDataProvider.getData("Sheet1", i, 1);	
		driver.findElement(By.id("txtContains")).sendKeys(JobId);
		driver.findElement(By.id("txtContains")).sendKeys(Keys.TAB);
		Thread.sleep(5000);
		
		WebElement Search = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
		jse.executeScript("arguments[0].click();",Search);
		Thread.sleep(5000);
		
			}else {}
			}
			catch(Exception e){
				System.out.println("Directly move to the ComfimPuAlert");
				}
			}
//		if(driver.findElement(By.id("Spn3pTime")).isDisplayed()) {
//		WebElement ZoneID = driver.findElement(By.id("Spn3pTime"));
//		String ZOneID = ZoneID.getText();
//		System.out.println("ZoneID of is==" + ZOneID);
//
//		if (ZOneID.equalsIgnoreCase("EDT")) {
//			ZOneID = "America/New_York";
//		} else if (ZOneID.equalsIgnoreCase("CDT")) {
//			ZOneID = "CST";
//		} else if (ZOneID.equalsIgnoreCase("PDT")) {
//			ZOneID = "PST";
//		}else if (ZOneID.equalsIgnoreCase("MDT")){
//			   ZOneID = "MST";
//		}
//		
//		WebElement Time = driver.findElement(By.id("txtPartPullTime"));
//		Time.clear();
//		Date date = new Date();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
//		dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
//
//		Time.sendKeys(dateFormat.format(date));
//		Time.sendKeys(Keys.TAB);
//		Thread.sleep(4000);
//		}
//		if(driver.findElement(By.id("GreenTickAlertPickuppull")).isDisplayed()) {
//		WebElement ConfirmPull = wait.until(ExpectedConditions.elementToBeClickable(By.id("GreenTickAlertPickuppull")));  
//	    jse.executeScript("arguments[0].click();",ConfirmPull);
//	    Thread.sleep(5000);
//		}else if (driver.findElement(By.id("GreenTick")).isDisplayed()){
//			WebElement ConfirmPullCPU = wait.until(ExpectedConditions.elementToBeClickable(By.id("GreenTick")));
//			jse.executeScript("arguments[0].click();",ConfirmPullCPU);
//			Thread.sleep(5000);
//		}

		 String svc = driver.findElement(By.id("lblServiceID")).getText();
		 if(svc.equals("SD")) {		
					WebElement ConfirmPull = wait.until(ExpectedConditions.elementToBeClickable(By.id("GreenTickAlertPickuppull")));  
				    jse.executeScript("arguments[0].click();",ConfirmPull);
				    System.out.println("Submit the Confirm Pull SD stage");
				    Thread.sleep(5000);
		 }	    
				    
		 
		 if(svc.equals("CPU")|| svc.equals("H3P")) {
	WebElement ConfirmPullCPU = wait.until(ExpectedConditions.elementToBeClickable(By.id("GreenTick")));
	jse.executeScript("arguments[0].click();",ConfirmPullCPU);
	System.out.println("Submit the Confirm Pull stage");
	Thread.sleep(4000);
}
	}
}
	
