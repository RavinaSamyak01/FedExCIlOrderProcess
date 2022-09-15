package fdxCIL_Stages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import fdxCIL_BasePackage.BaseInit;

public class ConfirmDel extends BaseInit{
	
	public static void FedExcomfirmDel() throws Exception {

		
		JavascriptExecutor jse = (JavascriptExecutor) driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(driver, 50);// wait time
		
		WebElement ConfirmDelAlert = driver.findElement(By.id("GreenTickAlertPickup"));
		jse.executeScript("arguments[0].click();",ConfirmDelAlert);
		Thread.sleep(4000);
		
	}
		
}
