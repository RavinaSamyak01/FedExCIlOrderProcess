package fdxCIL_Stages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_OrderProcess.FedExCILOrderCreation;


public class OnHandAtDestination extends BaseInit {

	@Test
	public void onHandDst() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30);// wait time

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);
		try {

			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"lblStages\"][contains(text(),'Onhand @ Destination')]")));

			String stg = driver.findElement(By.id("lblStages")).getText();

			// --Get StageName
			FedExCILOrderCreation OC = new FedExCILOrderCreation();
			OC.getStageName();

			if (stg.contains("ONHAND")) {
				Thread.sleep(6000);
				String tzone = driver.findElement(By.id("spnOnHand")).getText();
				String onhandtime = getTimeAsTZone(tzone);

				WebElement Time = isElementPresent("ONHADTime_id");
				Time.clear();
				Time.sendKeys(onhandtime);
				Time.sendKeys(Keys.TAB);
				logs.info("Enter On Hand Time");

				WebElement SpokeWith = isElementPresent("ONHADSpokeWith_id");
				SpokeWith.clear();
				SpokeWith.sendKeys("RV Oza");
				SpokeWith.sendKeys(Keys.TAB);
				logs.info("Enter On Hand Spoke With");

				WebElement ConfirmOnHandBtn = isElementPresent("ONHADConfOnHand_id");
				wait.until(ExpectedConditions.elementToBeClickable(ConfirmOnHandBtn));
				ConfirmOnHandBtn.click();
				logs.info("Click on Confirm OnHand button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			}
		} catch (Exception e) {
			logs.error(e);
			getScreenshot(driver, "Onhand@Dest" + svc);
			System.out.println("Onhand @ Destination Not Exist in Flow!!");
			logs.info("Onhand @ Destination Not Exist in Flow!!");
		}

	}

}
