package fedexCILStaging;

import java.awt.Robot;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class ServiceDetail extends BaseInit {
	@Test
	public static void SvcDetail() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(driver, 50);// wait time

		Robot r = new Robot();

		for (int i = 1; i < 2; i++) {
			ExcelDataProvider excelDataProvider = new ExcelDataProvider(); // Call ExcelDataProvider class for Read/
																			// Write data

			// Enter Account#
			Thread.sleep(5000);
			String JobId = excelDataProvider.getData("Sheet1", i, 1);
			driver.findElement(By.id("txtContains")).sendKeys(JobId);
			driver.findElement(By.id("txtContains")).sendKeys(Keys.TAB);
			Thread.sleep(10000);

			WebElement Search = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
			jse.executeScript("arguments[0].click();", Search);
			Thread.sleep(10000);

			if (i == 1) // CPU service
			{
				Thread.sleep(8000);
				CPU.FedExCpu();
			}
			if (i == 2) // H3P service
			{
				Thread.sleep(8000);
				H3P.FedExH3P();
			}

			if (i == 3)// SD service
			{
				Thread.sleep(8000);
				SD.FedExSD();
			}
			if (i == 4)// LOC service
			{
				Thread.sleep(8000);
				LOC.FedExLOC();
			}

		}
	}

}
