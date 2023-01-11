package fdxCIL_Stages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import fdxCIL_BasePackage.BaseInit;
import fdxCIL_OrderProcess.FedExCILOrderCreation;

public class Pickup extends BaseInit {

	@Test
	public void confirmPickup() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30);// wait time
		Actions act = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Get the ServiceID
		String svc = isElementPresent("TLServID_id").getText();
		System.out.println(svc);
		logs.info("ServiceID=" + svc);

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"lblStages\"][contains(text(),'Pickup')]")));

			// --Get StageName
			FedExCILOrderCreation OC = new FedExCILOrderCreation();
			OC.getStageName();

			/*
			 * // --get Ready Time if (svc.equalsIgnoreCase("LOC")) { rdytime =
			 * getData("Sheet1", 1, 34); } else if (svc.equalsIgnoreCase("SD")) { rdytime =
			 * getData("Sheet1", 2, 34); } else if (svc.equalsIgnoreCase("P3P")) { rdytime =
			 * getData("Sheet1", 3, 34); } else if (svc.equalsIgnoreCase("PA")) { rdytime =
			 * getData("Sheet1", 4, 34); } else if (svc.equalsIgnoreCase("DRV")) { rdytime =
			 * getData("Sheet1", 5, 34); } else if (svc.equalsIgnoreCase("AIR")) { rdytime =
			 * getData("Sheet1", 6, 34); } else if (svc.equalsIgnoreCase("SDC")) { rdytime =
			 * getData("Sheet1", 7, 34); } else if (svc.equalsIgnoreCase("FRA")) { rdytime =
			 * getData("Sheet1", 8, 34); } else if (svc.equalsIgnoreCase("FRG")) { rdytime =
			 * getData("Sheet1", 9, 34); }
			 */

			if (svc.equals("LOC") || svc.equals("P3P") || svc.equals("DRV") || svc.equals("SDC") || svc.equals("FRG")) {

				// --Get the timeZone
				String tzone = isElementPresent("TLLPUTimeZone_id").getText();
				String rectime = getTimeAsTZone(tzone);

				// --Enter Actual Pickup Time
				isElementPresent("TLPActPUpTime_id").clear();
				isElementPresent("TLPActPUpTime_id").sendKeys(rectime);
				isElementPresent("TLPActPUpTime_id").sendKeys(Keys.TAB);
				logs.info("Enter Actual pickup time");

				// --Click on Confirm PU button
				WebElement ConfPU = isElementPresent("TLPConfPU_id");
				wait.until(ExpectedConditions.elementToBeClickable(ConfPU));
				Thread.sleep(2000);
				ConfPU.click();
				logs.info("Click on Confirm PU button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//*[@id=\"idValidationforMain\"]//ul[@id=\"errorid\"]")));
					String Valmsg = isElementPresent("OCValOnePack_xpath").getText();
					logs.info("Validation message is displayed=" + Valmsg);
					if (Valmsg.contains("Parameter(s) are modified. Please recalculate customer charges.")) {
						// Recalculate the charges
						// --Go to Edit Job tab
						WebElement EditOrTab = isElementPresent("EOEditOrderTab_id");
						act.moveToElement(EditOrTab).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(EditOrTab));
						act.moveToElement(EditOrTab).click().perform();
						logs.info("Click on Edit Order Tab");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// -Recalculate button
						WebElement ReCalc = isElementPresent("EORecal_id");
						act.moveToElement(ReCalc).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(ReCalc));
						act.moveToElement(ReCalc).click().perform();
						logs.info("Click on Recalculate button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Click on Save Changes button
						WebElement SaveChanges = isElementPresent("TLSaveChanges_id");
						act.moveToElement(SaveChanges).build().perform();
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
						wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
						act.moveToElement(SaveChanges).click().perform();
						logs.info("Click on Save Changes button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Go to job Status Tab
						WebElement JobOverTab = isElementPresent("TLJobStatusTab_id");
						act.moveToElement(JobOverTab).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(JobOverTab));
						act.moveToElement(JobOverTab).click().perform();
						logs.info("Click on Job Overview Tab");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Get the timeZone
						tzone = isElementPresent("TLLPUTimeZone_id").getText();
						rectime = getTimeAsTZone(tzone);

						// --Enter Actual Pickup Time
						isElementPresent("TLPActPUpTime_id").clear();
						isElementPresent("TLPActPUpTime_id").sendKeys(rectime);
						isElementPresent("TLPActPUpTime_id").sendKeys(Keys.TAB);
						logs.info("Enter Actual pickup time");

						// --Click on Confirm PU button
						ConfPU = isElementPresent("TLPConfPU_id");
						wait.until(ExpectedConditions.elementToBeClickable(ConfPU));
						Thread.sleep(2000);
						ConfPU.click();
						logs.info("Click on Confirm PU button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					}

				} catch (Exception PModify) {
					logs.info("Validation message is not displayed for Recalculate the charges");

				}

			}

			if (svc.equals("SD") || svc.equals("PA") || svc.equals("AIR") || svc.equals("FRA")) {

				// --Get the timeZone
				String tzone = isElementPresent("TLLPUTimeZone_id").getText();
				String rectime = getTimeAsTZone(tzone);

				// --Enter Actual Pickup Time
				isElementPresent("TLPActPUpTime_id").clear();
				isElementPresent("TLPActPUpTime_id").sendKeys(rectime);
				isElementPresent("TLPActPUpTime_id").sendKeys(Keys.TAB);
				logs.info("Enter Actual pickup time");

				// --Click on Confirm PU button
				WebElement ConfPU = isElementPresent("TLPUConfPU2_id");
				wait.until(ExpectedConditions.elementToBeClickable(ConfPU));
				act.moveToElement(ConfPU).build().perform();
				js.executeScript("arguments[0].click();", ConfPU);
				logs.info("Click on Confirm PickUp  button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				try {

					wait.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//*[@id=\"idValidationforMain\"]//ul[@id=\"errorid\"]")));
					String Valmsg = isElementPresent("OCValOnePack_xpath").getText();
					logs.info("Validation message is displayed=" + Valmsg);
					if (Valmsg.contains("Parameter(s) are modified. Please recalculate customer charges.")) {
						// Recalculate the charges
						// --Go to Edit Job tab
						WebElement EditOrTab = isElementPresent("EOEditOrderTab_id");
						act.moveToElement(EditOrTab).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(EditOrTab));
						act.moveToElement(EditOrTab).click().perform();
						logs.info("Click on Edit Order Tab");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// -Recalculate button
						WebElement ReCalc = isElementPresent("EORecal_id");
						act.moveToElement(ReCalc).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(ReCalc));
						act.moveToElement(ReCalc).click().perform();
						logs.info("Click on Recalculate button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Click on Save Changes button
						WebElement SaveChanges = isElementPresent("TLSaveChanges_id");
						act.moveToElement(SaveChanges).build().perform();
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
						wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
						act.moveToElement(SaveChanges).click().perform();
						logs.info("Click on Save Changes button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Go to job Status Tab
						WebElement JobOverTab = isElementPresent("TLJobStatusTab_id");
						act.moveToElement(JobOverTab).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(JobOverTab));
						act.moveToElement(JobOverTab).click().perform();
						logs.info("Click on Job Overview Tab");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Get the timeZone
						tzone = isElementPresent("TLLPUTimeZone_id").getText();
						rectime = getTimeAsTZone(tzone);

						// --Enter Actual Pickup Time
						isElementPresent("TLPActPUpTime_id").clear();
						isElementPresent("TLPActPUpTime_id").sendKeys(rectime);
						isElementPresent("TLPActPUpTime_id").sendKeys(Keys.TAB);
						logs.info("Enter Actual pickup time");

						// --Click on Confirm PU button
						ConfPU = isElementPresent("TLPUConfPU2_id");
						wait.until(ExpectedConditions.elementToBeClickable(ConfPU));
						Thread.sleep(2000);
						ConfPU.click();
						logs.info("Click on Confirm PU button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					}

				} catch (Exception PModify) {
					logs.info("Validation message is not displayed for Recalculate the charges");

				}

			}

		} catch (Exception e) {
			logs.error(e);
			getScreenshot(driver, "PICKUP" + svc);
			System.out.println("PICKUP Not Exist in Flow!!");
			logs.info("PICKUP Not Exist in Flow!!");

		}

	}

}
