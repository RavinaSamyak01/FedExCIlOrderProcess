package fdxCIL_Stages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_OrderProcess.FedExCILOrderCreation;


public class OutForDelivery extends BaseInit {

	@Test
	public void outForDel() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30);// wait time
		JavascriptExecutor jse = (JavascriptExecutor) driver;// scroll,click

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		String svc = driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"lblStages\"][contains(text(),'Out For Delivery')]")));

			// --Get StageName
			FedExCILOrderCreation OC = new FedExCILOrderCreation();
			OC.getStageName();

			WebElement HAOrigin = isElementPresent("TLHAOBtn_id");
			jse.executeScript("arguments[0].click();", HAOrigin);
			logs.info("Click on Confirm Out For Del button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception e) {
			logs.error(e);
			getScreenshot(driver, "OutForDelivery" + svc);
			System.out.println("Out For Delivery Not Exist in Flow!!");
			logs.info("Out For Delivery Not Exist in Flow!!");

		}

	}
}
