package fdxCIL_Stages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import fdxCIL_BasePackage.BaseInit;

public class CSR extends BaseInit {

	public static void FedExCSRAcknowledge() throws Exception {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebDriverWait wait = new WebDriverWait(driver, 50);// wait time
		String svc = driver.findElement(By.id("lblServiceID")).getText();

		if (svc.equals("SD")) {

			WebElement SelectFlights = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSelectFlights")));
			jse.executeScript("arguments[0].click();", SelectFlights);
			Thread.sleep(5000);

			WebElement hlkSel = wait.until(ExpectedConditions.elementToBeClickable(By.id("hlkSel_0")));
			jse.executeScript("arguments[0].click();", hlkSel);
			Thread.sleep(5000);
			WebElement assign = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,'Assign')]")));
			jse.executeScript("arguments[0].click();", assign);
			Thread.sleep(10000);

			WebElement Tick = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGreenTick")));
			jse.executeScript("arguments[0].click();", Tick);
			System.out.println("Submit the CSR stage");
			Thread.sleep(6000);
		}
		if (svc.equals("LOC")) {
			// LOC
			WebElement ackbtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("GreenTick")));
			jse.executeScript("arguments[0].click();", ackbtn);
			System.out.println("submit the CSR stage at LOC");
			Thread.sleep(4000);
		}

	}
}
