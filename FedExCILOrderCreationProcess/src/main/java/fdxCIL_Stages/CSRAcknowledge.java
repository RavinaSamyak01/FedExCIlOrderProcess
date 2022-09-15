package fdxCIL_Stages;

import org.openqa.selenium.By;
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

		String svc = driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);

		try {

			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"lblStages\"][contains(text(),'CSR Acknowledge')]")));

			// --Get StageName
			FedExCILOrderCreation OC = new FedExCILOrderCreation();
			OC.getStageName();

			if (svc.equals("LOC") || svc.equals("P3P")) {
				driver.findElement(By.id("GreenTick")).click();
			}

			if (svc.equals("SD") || svc.equals("PA")) {
				driver.findElement(By.id("btnGreenTick")).click();
			}
		} catch (Exception e) {
			logs.error(e);
			getScreenshot(driver, "CSRACKNOWLEDGE" + svc);
			System.out.println("CSR ACKNOWLEDGE Not Exist in Flow!!");
			logs.info("CSR ACKNOWLEDGE Not Exist in Flow!!");

		}

	}
}
