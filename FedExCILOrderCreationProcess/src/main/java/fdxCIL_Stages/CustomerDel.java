package fdxCIL_Stages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import fdxCIL_BasePackage.BaseInit;

public class CustomerDel extends BaseInit{

	public static void CustomerDelivery() throws Exception{
		
		//String stg = driver.findElement(By.id("lblStages")).getText();
		try {
			Thread.sleep(10000);
			String stg = driver.findElement(By.id("lblStages")).getText();
			if (stg.contains("CUSTOMER DELIVERY IN PROGRESS")) {
				Thread.sleep(9000);
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(driver, 50);// wait time
		
		Thread.sleep(5000);
		WebElement ZoneID = driver.findElement(By.id("lblActualDeliveryTimeZone"));
		String ZOneID = ZoneID.getText();
		System.out.println("ZoneID of is==" + ZOneID);

		if (ZOneID.equalsIgnoreCase("EDT")) {
			ZOneID = "America/New_York";
		} else if (ZOneID.equalsIgnoreCase("CDT")) {
			ZOneID = "CST";
		} else if (ZOneID.equalsIgnoreCase("PDT")) {
			ZOneID = "PST";
		}else if (ZOneID.equalsIgnoreCase("MDT")){
			   ZOneID = "MST";
		}
		
		WebElement Time = driver.findElement(By.id("txtActualDeliveryTime"));
		Time.clear();
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));

		Time.sendKeys(dateFormat.format(date));
		Time.sendKeys(Keys.TAB);
		
		WebElement tab = driver.findElement(By.id("lblActualDeliveryTimeZone"));
		jse.executeScript("arguments[0].click();",tab);
		Thread.sleep(3000);
		driver.findElement(By.id("txtDeliverySignature")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("txtDeliverySignature")).sendKeys("tathya");
		Thread.sleep(3000);
		
		WebElement Confirm = driver.findElement(By.id("GreenTickDelivery"));
		jse.executeScript("arguments[0].click();",Confirm);
		Thread.sleep(3000);
		}
		 }catch(Exception e) {
				System.out.println("Customer Delivery in Progress stage Not Exist in Flow!!");
		 }
	}
	}
			
		
	

