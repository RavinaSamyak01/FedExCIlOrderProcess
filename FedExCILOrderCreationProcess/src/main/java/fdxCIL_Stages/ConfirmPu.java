package fdxCIL_Stages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import fdxCIL_BasePackage.BaseInit;

public class ConfirmPu extends BaseInit{

	public static void FedExConfirmPuAlert() throws Exception{
		JavascriptExecutor jse = (JavascriptExecutor) driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(driver, 50);// wait time
		String svc = driver.findElement(By.id("lblServiceID")).getText(); 			
	            if(svc.equals("CPU")) 
	            {	
				if (driver.findElement(By.id("cmbContacted")).isDisplayed()){
					WebElement email = wait.until(ExpectedConditions.elementToBeClickable(By.id("cmbContacted")));  
				    jse.executeScript("arguments[0].click();",email);
				    
				    	Select list1 = new Select(email);
				    	  list1.selectByValue("number:377");
		              System.out.println("email selected");
				}else {	

				}		
				
				WebElement email = wait.until(ExpectedConditions.elementToBeClickable(By.id("lblContactedValue1")));
				email.clear();
				email.sendKeys("tathya.gandhi@samyak.com");			
				Thread.sleep(4000);
				
				WebElement spoke = wait.until(ExpectedConditions.elementToBeClickable(By.id("txtSpokeWith")));
				spoke.clear();
				spoke.sendKeys("Tathya");
				Thread.sleep(4000);
				
				WebElement ConfirmPu = wait.until(ExpectedConditions.elementToBeClickable(By.id("GreenTickAlertPickup")));
				jse.executeScript("arguments[0].click();",ConfirmPu);
				Thread.sleep(5000);
				System.out.println("submit the Comfirm pu stage at CPU");
				
	            }
	            if(svc.equals("H3P"))
	            {
	            	if (driver.findElement(By.id("cmbContacted")).isDisplayed()){
						WebElement email = wait.until(ExpectedConditions.elementToBeClickable(By.id("cmbContacted")));  
					    jse.executeScript("arguments[0].click();",email);
					    
					    	Select list1 = new Select(email);
					    	  list1.selectByValue("number:377");
			              System.out.println("email selected");
					}else {	

					}		
					
					WebElement email = wait.until(ExpectedConditions.elementToBeClickable(By.id("lblContactedValue2")));
					email.clear();
					email.sendKeys("tathya.gandhi@samyak.com");			
					Thread.sleep(4000);
					
					WebElement spoke = wait.until(ExpectedConditions.elementToBeClickable(By.id("txtSpokeWith")));
					spoke.clear();
					spoke.sendKeys("Tathya");
					Thread.sleep(4000);
					
					WebElement ConfirmPu = wait.until(ExpectedConditions.elementToBeClickable(By.id("GreenTickAlertPickup")));
					jse.executeScript("arguments[0].click();",ConfirmPu);
					Thread.sleep(5000);
					System.out.println("submit the Comfirm pu stage at CPU");
	            }
	}
}
				
		 
		 
		 
		 
		 
		 
