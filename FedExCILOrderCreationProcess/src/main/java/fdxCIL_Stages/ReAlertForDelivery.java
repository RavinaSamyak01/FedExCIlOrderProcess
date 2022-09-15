package fdxCIL_Stages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_OrderProcess.FedExCILOrderCreation;


public class ReAlertForDelivery extends BaseInit {
	@Test
	public void reAlertfordel() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30);// wait time
		JavascriptExecutor jse = (JavascriptExecutor) driver;// scroll,click
		Actions act = new Actions(driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		String svc = driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);

		try {

			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"lblStages\"][contains(text(),'Re-alert for Delivery')]")));

			// --Get StageName
			FedExCILOrderCreation OC = new FedExCILOrderCreation();
			OC.getStageName();

			// --Alert&Confirm
			WebElement Sendpualert = isElementPresent("TLRDAlConfrm_id");
			act.moveToElement(Sendpualert).build().perform();
			jse.executeScript("arguments[0].click();", Sendpualert);
			logs.info("Clicked on Alert&Confirm button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception e) {
			logs.error(e);
			getScreenshot(driver, "Re-alertforDel" + svc);
			System.out.println("Re-alert for Delivery Not Exist in Flow!!");
			logs.info("Re-alert for Delivery Not Exist in Flow!!");

		}

	}

}
