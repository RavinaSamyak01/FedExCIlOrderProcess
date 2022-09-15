package fdxCIL_Stages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_BasePackage.ExcelDataProvider;

public class PULL extends BaseInit{

	public static void FedExPull() throws InterruptedException {
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(driver, 50);// wait time
		Actions actions = new Actions(driver);
		for(int i=3; i<4;i++)
		{
		ExcelDataProvider excelDataProvider = new ExcelDataProvider();
		
		Thread.sleep(4000);
		try {
			Thread.sleep(8000);
			String task = driver.findElement(By.xpath("/html/body/div[2]/section/div[2]/div[1]/div/div[2]/div[2]/div[1]/div[5]/label")).getText();
			if (task.contains("Basic Search")) {
				Thread.sleep(9000);
				
		String JobId = excelDataProvider.getData("Sheet1", i, 1);	
		driver.findElement(By.id("txtContains")).sendKeys(JobId);
		driver.findElement(By.id("txtContains")).sendKeys(Keys.TAB);
		Thread.sleep(10000);
		
		WebElement Search = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
		jse.executeScript("arguments[0].click();",Search);
		Thread.sleep(10000);
		
			}else {}
			}
			catch(Exception e){
				System.out.println("Directly move to the ComfimPuAlert");
				}
			}
		try {
			Thread.sleep(10000);
			String stg = driver.findElement(By.id("lblStages")).getText();
				if(stg.contains("CONFIRM PULL"))
					{
					Thread.sleep(6000);
					WebElement ZoneID = driver.findElement(By.id("Spn3pTime"));
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

					WebElement Time = driver.findElement(By.id("txtPartPullTime"));
					Time.clear();
					Date date = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
					dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));

					Time.sendKeys(dateFormat.format(date));
					Time.sendKeys(Keys.TAB);
					
//					String tzone = driver.findElement(By.id("lblEditArrivalTimeSZone")).getText();
//					String arrtime = getTime(tzone);
//					driver.findElement(By.id("txtArrivalTime")).sendKeys(arrtime);
//					driver.findElement(By.id("txtArrivalTime")).sendKeys(Keys.TAB);
					Thread.sleep(6000);
					
					WebElement Recal = driver.findElement(By.id("idrecalbtn"));
					actions.moveToElement(Recal).click().build().perform();
					Thread.sleep(4000);
					WebElement btn = driver.findElement(By.id("btnGreenTickPull"));
					actions.moveToElement(btn).click().build().perform();
					//logger.info("Submit the Wait for Arrival stage ");
					}
		}catch (Exception e) {
			System.out.println("Wait for Arrival processed!!");
			}
	}
}
