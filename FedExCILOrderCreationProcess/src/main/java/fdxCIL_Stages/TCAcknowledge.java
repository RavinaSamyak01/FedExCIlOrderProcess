package fdxCIL_Stages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_OrderProcess.FedExCILOrderCreation;

public class TCAcknowledge extends BaseInit {

	@Test
	public void tcAcknowledge() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30);// wait time

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		// --Get the ServiceID
		String svc = isElementPresent("TLServID_id").getText();
		System.out.println(svc);
		logs.info("ServiceID=" + svc);
		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"lblStages\"][contains(text(),'Acknowledge')]")));

			// --Get StageName
			FedExCILOrderCreation OC = new FedExCILOrderCreation();
			OC.getStageName();

			// --Click on TC Ack button
			if (svc.equals("LOC") || svc.equals("P3P") || svc.equals("DRV") || svc.equals("SDC") || svc.equals("FRG")) {
				WebElement TCAckBtn = isElementPresent("TLAcknBTN_id");
				wait.until(ExpectedConditions.elementToBeClickable(TCAckBtn));
				TCAckBtn.click();
				logs.info("Clicked on TC Acknowledge button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}

			if (svc.equals("SD") || svc.equals("PA") || svc.equals("AIR") || svc.equals("FRA")) {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				WebElement TCAckBtn = isElementPresent("TLAckBTn2_id");
				wait.until(ExpectedConditions.elementToBeClickable(TCAckBtn));
				TCAckBtn.click();
				logs.info("Clicked on TC Acknowledge button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}
		} catch (Exception e) {
			logs.error(e);
			getScreenshot(driver, "TCACKNOWLEDGE" + svc);
			System.out.println("TC ACKNOWLEDGE Not Exist in Flow!!");
			logs.info("TC ACKNOWLEDGE Not Exist in Flow!!");

		}

	}
}
