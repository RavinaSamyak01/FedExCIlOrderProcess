package fdxCIL_Stages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_OrderProcess.FedExCILOrderCreation;

public class SendDelAlert extends BaseInit {
	@Test
	public void delAlert() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30);// wait time

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"lblStages\"][contains(text(),'Send Del Alert')]")));

			// --Get StageName
			FedExCILOrderCreation OC = new FedExCILOrderCreation();
			OC.getStageName();

			// --Contacted
			Select Contacttype = new Select(isElementPresent("TLRDContacted_id"));
			Contacttype.selectByVisibleText("Email");
			logs.info("Selected Email option as Contacted");
			Thread.sleep(1000);

			// --Contatcted Value
			isElementPresent("TLRDContValue_id").clear();
			isElementPresent("TLRDContValue_id").sendKeys("Ravina.prajapati@samyak.com");

			// --Spoke with
			isElementPresent("TLRDSpokeW_id").clear();
			isElementPresent("TLRDSpokeW_id").sendKeys("Ravina");
			Thread.sleep(1000);

			// --Alert&Confirm button
			WebElement AlertConfm = isElementPresent("TLRDAlConfrmBtn_id");
			wait.until(ExpectedConditions.elementToBeClickable(AlertConfm));
			Thread.sleep(2000);
			AlertConfm.click();
			logs.info("Click on Alert and Confirm button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception e) {
			logs.error(e);
			getScreenshot(driver, "SENDDELALERT" + svc);
			System.out.println("SEND DEL ALERT Not Exist in Flow!!");
			logs.info("SEND DEL ALERT Not Exist in Flow!!");

		}
	}
}
