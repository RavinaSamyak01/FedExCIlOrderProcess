package fdxCIL_Stages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_BasePackage.BaseInit;

public class Board1 extends BaseInit {

	@Test
	public void onBoard1() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(driver, 30);//
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		String svc = driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);
		try {

			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"lblStages\"][contains(text(),'ON BOARD')]")));

			// --Get StageName
			OrderCreation OC = new OrderCreation();
			OC.getStageName();

			// --Click on Update button
			WebElement update = isElementPresent("TLAckBTn2_id");
			wait.until(ExpectedConditions.elementToBeClickable(update));
			jse.executeScript("arguments[0].click();", update);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception e) {
			logs.error(e);
			getScreenshot(driver, "OnBoard1_" + svc);
			System.out.println("Board Not Exist in Flow!!");
			logs.info("Board Not Exist in Flow!!");

		}

	}
}
