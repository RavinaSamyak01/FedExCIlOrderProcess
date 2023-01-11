package fdxCIL_Stages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_OrderProcess.FedExCILOrderCreation;

public class CSRAcknowledge extends BaseInit {

	@Test
	public void csrAcknowledge() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30);//

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Get the ServiceID
		String svc = isElementPresent("TLServID_id").getText();
		System.out.println(svc);
		logs.info("ServiceID=" + svc);

		try {

			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"lblStages\"][contains(text(),'CSR Acknowledge')]")));

			// --Get StageName
			FedExCILOrderCreation OC = new FedExCILOrderCreation();
			OC.getStageName();

			if (svc.equals("LOC") || svc.equals("P3P")) {
				WebElement TCACK = isElementPresent("TLAcknBTN_id");
				wait.until(ExpectedConditions.elementToBeClickable(TCACK));
				Thread.sleep(2000);
				TCACK.click();
				logs.info("Click on Acknowledge button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			}

			if (svc.equals("SD") || svc.equals("PA")) {
				WebElement TCACK = isElementPresent("TLAckBTn2_id");
				wait.until(ExpectedConditions.elementToBeClickable(TCACK));
				Thread.sleep(2000);
				TCACK.click();
				logs.info("Click on Acknowledge button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			}
		} catch (Exception e) {
			logs.error(e);
			getScreenshot(driver, "CSRACKNOWLEDGE" + svc);
			System.out.println("CSR ACKNOWLEDGE Not Exist in Flow!!");
			logs.info("CSR ACKNOWLEDGE Not Exist in Flow!!");

		}

	}
}
