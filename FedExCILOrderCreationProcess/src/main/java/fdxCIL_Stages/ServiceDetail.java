package fdxCIL_Stages;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_OrderProcess.CPU;
import fdxCIL_OrderProcess.H3P;
import fdxCIL_OrderProcess.LOC;
import fdxCIL_OrderProcess.SD;

public class ServiceDetail extends BaseInit {
	@Test
	public static void SvcDetail() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(driver, 30);// wait time


		for (int i = 1; i < 5; i++) {
			// Enter Account#
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			String JobId = getData("Sheet1", i, 1);
			driver.findElement(By.id("txtContains")).sendKeys(JobId);
			driver.findElement(By.id("txtContains")).sendKeys(Keys.TAB);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			WebElement Search = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
			jse.executeScript("arguments[0].click();", Search);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			if (i == 1) // CPU service
			{
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				CPU.FedExCpu();
			}
			if (i == 2) // H3P service
			{
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				H3P.FedExH3P();
			}

			if (i == 3)// SD service
			{
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				SD.FedExSD();
			}
			if (i == 4)// LOC service
			{
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				LOC.FedExLOC();
			}

		}
	}

}
