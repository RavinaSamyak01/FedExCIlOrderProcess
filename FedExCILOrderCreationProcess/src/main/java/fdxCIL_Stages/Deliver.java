package fdxCIL_Stages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_OrderProcess.FedExCILOrderCreation;

public class Deliver extends BaseInit {

	@Test
	public void confirmDelivery() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(driver, 30);// wait time
		//Actions act = new Actions(driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = null;
		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"lblStages\"][contains(text(),'Deliver')]")));

			// --Get the ServiceID
			svc = isElementPresent("TLServID_id").getText();
			System.out.println(svc);
			logs.info("ServiceID=" + svc);

			// --Get StageName
			FedExCILOrderCreation OC = new FedExCILOrderCreation();
			OC.getStageName();

			if (svc.equals("LOC") || svc.equals("DRV") || svc.equals("SDC") || svc.equals("FRG")) {
				// --Enter Actual DL time
				isElementPresent("TLDActDLTime_id").clear();
				isElementPresent("TLDActDLTime_id").sendKeys(rdytime);
				logs.info("Enter Actual DL Time");

				// --Enter SIgnature
				isElementPresent("TLDSignature_id").clear();
				isElementPresent("TLDSignature_id").sendKeys("RVOza");
				logs.info("Enter Signature");

				// --Click on Confirm DL
				isElementPresent("TLDConfDL_id").click();
				logs.info("Clicked on Confirm DEL button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --CHeck parameter modify validation
				OC.reCalc(svc);

				// --Pop Up
				boolean dlpop = driver.getPageSource().contains("NetLink Global Logistics");

				if (dlpop == true) {
					isElementPresent("TLDPUOK_id").click();
					logs.info("Clicked on OK button of pop up");

				}

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			}

			if (svc.equals("SD") || svc.equals("PA") || svc.equals("FRA")) {
				// --Get the timeZone
				String tzone = isElementPresent("TLDACDTimeZone_id").getText();
				String rectime = getTimeAsTZone(tzone);

				// --Enter Actual Del Time
				isElementPresent("TLDActDLTime_id").clear();
				isElementPresent("TLDActDLTime_id").sendKeys(rectime);
				isElementPresent("TLDActDLTime_id").sendKeys(Keys.TAB);

				// --Enter Signature
				isElementPresent("TLDSignature_id").clear();
				isElementPresent("TLDSignature_id").sendKeys("RVOza");
				logs.info("Enter Signature");

				// --Click on Confirm DL
				WebElement ConDL = isElementPresent("TLDAOConfDrop_id");
				wait.until(ExpectedConditions.elementToBeClickable(ConDL));
				jse.executeScript("arguments[0].click();", ConDL);
				logs.info("Clicked on Confirm DEL button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --CHeck parameter modify validation
				OC.reCalc(svc);

				// --Pop Up
				boolean dlpop = driver.getPageSource().contains("NetLink Global Logistics");

				if (dlpop == true) {
					isElementPresent("TLDPUOK_id").click();
					logs.info("Clicked on OK button of pop up");

				}

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}

			if (svc.equals("AIR")) {
				// --Get the timeZone
				String tzone = isElementPresent("TLDAIRTZone_id").getText();
				String rectime = getTimeAsTZone(tzone);

				// --Enter Actual Del Time
				isElementPresent("TLDAIRActualDTime_id").clear();
				isElementPresent("TLDAIRActualDTime_id").sendKeys(rectime);
				isElementPresent("TLDAIRActualDTime_id").sendKeys(Keys.TAB);

				// --Enter Signature
				isElementPresent("TLDAIRSign_id").clear();
				isElementPresent("TLDAIRSign_id").sendKeys("RVOza");
				logs.info("Enter Signature");

				// --Click on Confirm DL
				WebElement ConDL = isElementPresent("TLDAIRDLStage_id");
				wait.until(ExpectedConditions.elementToBeClickable(ConDL));
				jse.executeScript("arguments[0].click();", ConDL);
				logs.info("Clicked on Confirm DEL button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --CHeck parameter modify validation
				OC.reCalc(svc);

				// --Pop Up
				boolean dlpop = driver.getPageSource().contains("NetLink Global Logistics");

				if (dlpop == true) {
					isElementPresent("TLDPUOK_id").click();
					logs.info("Clicked on OK button of pop up");

				}

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}
		} catch (Exception e) {
			logs.error(e);
			getScreenshot(driver, "DELIVER" + svc);
			System.out.println("DELIVER Not Exist in Flow!!");
			logs.info("DELIVER Not Exist in Flow!!");

		}

	}

}
